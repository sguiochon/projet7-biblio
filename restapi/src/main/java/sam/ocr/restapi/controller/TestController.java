package sam.ocr.restapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String getTest(){
        return "ok";
    }

    @RequestMapping("/pays")
    public List<String> getCountries() {
        String[] varCountries = {"Afghanistan", "Albanie", "Algérie", "Allemagne", "Greenland", "Grenada"};
        ArrayList<String> countries = new ArrayList<String>(Arrays.asList(varCountries));
        return countries;
    }

}
