package be.spring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by u0090265 on 13/09/16.
 */
@Controller
public class CertController {
    @RequestMapping(value = "/.well-known/acme-challenge/usElbbUwhooe8cuYlQeVGpJ2lNrT6KmQ5xNdvltJZ8g", method =
            RequestMethod.GET)
    @ResponseBody
    public String getCert() {
        return "usElbbUwhooe8cuYlQeVGpJ2lNrT6KmQ5xNdvltJZ8g.9ZpoaCjeT61aOhL9vOOMOL78nBX6uZe054866Jq_-mc";
    }
}
