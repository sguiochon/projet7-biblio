package sam.biblio.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.biblio.web.config.ApplicationConfig;
import sam.biblio.web.dto.NavDTO;


/**
 * Classe utilitaire permettant de construire une instance de NAvDTO contenant l'ensemble des informations
 * nécessaires à la représentation des éléments de navigation paginée. Cet objet est ensuite traité par la vue.
 */
@Component
public class TableNavigationHelper {

    protected ApplicationConfig applicationConfig;

    @Autowired
    public TableNavigationHelper(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    protected NavDTO buildNavInfo(int currentPage, long totalPages) {
        if (totalPages == 1)
            return null;

        NavDTO nav = new NavDTO();
        long pageLimitMin = 0;
        long pageLimitMax = totalPages - 1;

        long pageMin = currentPage - (applicationConfig.nbItemNavigation - 1) / 2;
        long pageMax = currentPage + (applicationConfig.nbItemNavigation - 1) / 2;

        for (long i = pageMin; i < currentPage; i++) {
            if (i < pageLimitMin)
                pageMax++;
        }
        for (long i = pageMax; i > currentPage; i--) {
            if (i > pageLimitMax)
                pageMin--;
        }
        pageMin = Math.max(pageMin, pageLimitMin);
        pageMax = Math.min(pageMax, pageLimitMax);

        for (long i = pageMin; i <= pageMax; i++) {
            if (currentPage == i)
                nav.addItem(i, true);
            else
                nav.addItem(i, false);
        }
        if (currentPage != pageLimitMin)
            nav.addPrevious(currentPage - 1);
        if (currentPage != pageLimitMax)
            nav.addNext(currentPage + 1);

        return nav;
    }
}
