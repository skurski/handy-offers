package pl.edu.agh.handy.offers.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.handy.offers.converter.UserConverter;
import pl.edu.agh.handy.offers.dto.UserDto;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.exception.UserAlreadyRegistered;
import pl.edu.agh.handy.offers.model.User;
import pl.edu.agh.handy.offers.service.UserService;
import pl.edu.agh.handy.offers.util.LogoutHandler;

import javax.validation.Valid;

import static pl.edu.agh.handy.offers.common.Links.*;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = Url.USER_REGISTER, method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return View.USER_REGISTER;
    }

    @RequestMapping(value = Url.USER_REGISTER, method = RequestMethod.POST)
    public String registerFormSubmission(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return View.USER_REGISTER;
        }

        try {
            userService.create(userDto);
        } catch (UserAlreadyRegistered userAlreadyRegistered) {
            model.addAttribute("userAlreadyRegistered", true);
            return View.USER_REGISTER;
        }
        return "redirect:/";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Url.USER_REMOVE, method = RequestMethod.POST)
    public String removeUser(HttpServletRequest request, UserDto userDto) {
        try {
            userService.delete(userDto);
            LogoutHandler.logout(request);

        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        return View.USER_REMOVE;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Url.USER_ACCOUNT, method = RequestMethod.GET)
    public String editAccount(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userDto", userService.findById(user.getId()));
        return View.USER_ACCOUNT;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Url.USER_ACCOUNT_EDIT, method = RequestMethod.POST)
    public String editAccount(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return View.USER_ACCOUNT;
        }

        User user = null;
        try {
            user = userService.update(userDto);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        model.addAttribute("success", true);
        model.addAttribute("user", userConverter.modelToDto(user));
        return View.USER_ACCOUNT;
    }
}
