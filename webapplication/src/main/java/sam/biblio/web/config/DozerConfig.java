package sam.biblio.web.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {

    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        return new DozerBeanMapper();
    }

}



