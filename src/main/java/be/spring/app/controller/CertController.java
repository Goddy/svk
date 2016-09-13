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
    @RequestMapping(value = "/.well-known/acme-challenge/zjCTblqt2QPG7NGYQYAP0mFZg5XHOfO-mTVzhGQuk44", method =
            RequestMethod.GET)
    @ResponseBody
    public String foo() {
        return "zjCTblqt2QPG7NGYQYAP0mFZg5XHOfO-mTVzhGQuk44.9ZpoaCjeT61aOhL9vOOMOL78nBX6uZe054866Jq_-mc";
    }
}
