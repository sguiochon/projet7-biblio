package sam.biblio.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.net.URISyntaxException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(URISyntaxException.class)
    public ModelAndView handleMyException(URISyntaxException mex) {
        ModelAndView model = new ModelAndView();
        model.addObject("errMessage", mex.getMessage());
        model.addObject("errReason", mex.getReason());
        model.setViewName("error/api_error");
        return model;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleException(HttpClientErrorException ex){
        return "error/api_error";
    }

}
