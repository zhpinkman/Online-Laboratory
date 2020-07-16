package com.example.demo.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Enumeration;

@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        return String.format("<html><center><body><h2>Custom Error Page | Don't Panic! :)</h2><div>Status code: <b>%s</b></div>"
                        + "<div>Exception Message: <b>%s</b></div><body></html>",
                statusCode, message==null? "N/A": message);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}