package com.ua.javarush.mentor.controller.web;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.CountryService;
import com.ua.javarush.mentor.services.RoleService;
import com.ua.javarush.mentor.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.ua.javarush.mentor.constants.WebConstants.*;
import static com.ua.javarush.mentor.exceptions.UiErrorCode.USER_EMAIL_ALREADY_EXISTS;

@Slf4j
@Controller
public class AuthorizationController {

    public static final String USER = "user";
    public static final String MESSAGE = "msg";
    public static final String REGISTERED_SUCCESSFULLY = "User has been registered successfully! Please check your email for confirmation";
    public static final String USER_NAME = "userName";
    private static final String COUNTRIES = "countries";
    public static final long NO_ROLE_ID = 4L;

    private final UserService userService;
    private final CountryService countryService;
    private final RoleService roleService;

    @Autowired
    public AuthorizationController(UserService userService,
                                   CountryService countryService,
                                   RoleService roleService) {
        this.userService = userService;
        this.countryService = countryService;
        this.roleService = roleService;
    }

    @GetMapping(value = {EMPTY_URL, LOGIN_URL})
    public ModelAndView login() {
        return new ModelAndView(LOGIN_PAGE, USER, new UserCommand());
    }

    @GetMapping(SIGN_UP_URL)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView(SIGN_UP_PAGE);
        modelAndView.addObject(USER, new UserCommand());
        modelAndView.addObject(COUNTRIES, countryService.getAllCountries());
        return modelAndView;
    }

    @PostMapping(SIGN_UP_URL)
    public ModelAndView signupPost(@Valid UserCommand userCommand, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView(SIGN_UP_PAGE);
        isEmailExists(userCommand, bindingResult);
        try {
            insertDefaultRole(userCommand);
            userService.createUser(userCommand);
            model.addObject(MESSAGE, REGISTERED_SUCCESSFULLY);
            model.addObject(USER, new UserCommand());
        } catch (GeneralException e) {
            log.error(e.getMessage());
            model.addObject(MESSAGE, e.getUiErrors());
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addObject(MESSAGE, e.getMessage());
        }
        return model;
    }

    private void isEmailExists(UserCommand userCommand, BindingResult bindingResult) {
        if (userService.isEmailExists(userCommand.getEmail())) {
            bindingResult.rejectValue("email", "error.user", USER_EMAIL_ALREADY_EXISTS);
        }
    }

    private void insertDefaultRole(UserCommand userCommand) throws GeneralException {
        userCommand.setRoleId(roleService.getRoleById(NO_ROLE_ID).getId());
    }
}
