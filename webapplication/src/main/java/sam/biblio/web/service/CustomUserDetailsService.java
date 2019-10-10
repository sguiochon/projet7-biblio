package sam.biblio.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import sam.biblio.model.security.Privilege;
import sam.biblio.model.security.Role;
import sam.biblio.model.security.User;
import sam.biblio.web.webclient.UserWebClient;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Qualifier("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserWebClient userWebClient;

    //@Autowired
    //private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    public CustomUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Resource<User> user;
        try {
            System.out.println(">>>>> tentative de connexion au site");
            user = userWebClient.findByEmail(email);
            System.out.println(">>>>> email: " + user.getContent().getEmail() + ", password: " + user.getContent().getPassword() + " ,isEnabled: " + user.getContent().isEnabled() + ", roles: " + user.getContent().getRoles());
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println(e.toString());
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getContent().getEmail(), user.getContent().getPassword(), user.getContent().isEnabled(), true, true, true, getAuthorities(user.getContent().getRoles()));
    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private final List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            System.out.println(">>>>> Authorities: " + role.getPrivileges());
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
