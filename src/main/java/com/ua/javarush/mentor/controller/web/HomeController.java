package com.ua.javarush.mentor.controller.web;

import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.ua.javarush.mentor.constants.WebConstants.HOME_PAGE;
import static com.ua.javarush.mentor.constants.WebConstants.HOME_URL;
import static com.ua.javarush.mentor.controller.web.AuthorizationController.USER_NAME;

@Controller
@Slf4j
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(HOME_URL)
    public ModelAndView home() throws GeneralException {
        ModelAndView model = new ModelAndView();
        User user = getUser();

        model.addObject(USER_NAME, user.getFirstName() + " " + user.getLastName());
        model.setViewName(HOME_PAGE);
        return model;
    }

    private User getUser() throws GeneralException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }
}
