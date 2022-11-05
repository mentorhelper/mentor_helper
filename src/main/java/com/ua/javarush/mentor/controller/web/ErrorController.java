package com.ua.javarush.mentor.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.ua.javarush.mentor.constants.WebConstants.ERROR_PAGE;
import static com.ua.javarush.mentor.constants.WebConstants.ERROR_URL;


@Controller
public class ErrorController {

    public static final String ERRORS_ACCESS_DENIED_PAGE = "errors/access_denied";
    public static final String ACCESS_DENIED_URL = "/access_denied";

    @GetMapping(ACCESS_DENIED_URL)
    public ModelAndView accessDenied() {
        return new ModelAndView(ERRORS_ACCESS_DENIED_PAGE);
    }

    @RequestMapping(ERROR_URL)
    public String handleError() {
        return ERROR_PAGE;
    }

}
