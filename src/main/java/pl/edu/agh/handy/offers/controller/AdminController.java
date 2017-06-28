package pl.edu.agh.handy.offers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.handy.offers.common.Links;
import pl.edu.agh.handy.offers.dto.CategoryDto;
import pl.edu.agh.handy.offers.dto.UserDto;
import pl.edu.agh.handy.offers.exception.CategoryNotEmpty;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.service.CategoryService;
import pl.edu.agh.handy.offers.service.OfferService;
import pl.edu.agh.handy.offers.service.UserService;

/**
 * Admin controller.
 */
@Controller
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    private static final String OFFERS = "offers";
    private static final String CATEGORIES = "categories";
    private static final String USERS = "users";

    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN, method = RequestMethod.GET)
    public String index(Model model) {
        LOGGER.debug("Load admin main page.");
        model.addAttribute(OFFERS, offerService.findAll());
        model.addAttribute(CATEGORIES, categoryService.findAll());
        model.addAttribute(USERS, userService.findAll());
        return Links.View.ADMIN_MAIN;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_USER, method = RequestMethod.GET)
    public String usersList(Model model) {
        model.addAttribute(USERS, userService.findAll());
        return Links.View.ADMIN_USER;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_CATEGORY, method = RequestMethod.GET)
    public String categoryList(Model model) {
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.ADMIN_CATEGORY;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_OFFER_REPORTED, method = RequestMethod.GET)
    public String reportedOffers(Model model) {
        model.addAttribute(OFFERS, offerService.findByReported(1));
        return Links.View.ADMIN_OFFER_REPORTED;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_USER_UPDATE, method = RequestMethod.POST)
    public String userUpdate(UserDto userDto, Model model) {
        try {
            userService.update(userDto);
            model.addAttribute(USERS, userService.findAll());
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        return Links.View.ADMIN_USER;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_CATEGORY_UPDATE, method = RequestMethod.POST)
    public String categoryUpdate(CategoryDto categoryDto, String delete, Model model) {
        boolean categoryEmpty = false;
        try {
            if (delete != null && delete.equals("true")) {
                categoryService.delete(categoryDto);
            } else {
                categoryService.update(categoryDto);
            }
            model.addAttribute(CATEGORIES, categoryService.findAll());
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        } catch (CategoryNotEmpty categoryNotEmpty) {
            categoryEmpty = true;
        }

        model.addAttribute("categoryEmpty", categoryEmpty);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.ADMIN_CATEGORY;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_CATEGORY_ADD, method = RequestMethod.POST)
    public String categoryAdd(CategoryDto categoryDto, Model model) {
        categoryService.create(categoryDto);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.ADMIN_CATEGORY;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.ADMIN_OFFER_UPDATE, method = RequestMethod.POST)
    public String offerBanned(String offerId, String banned, Model model) {
        if (banned == null) {
            offerService.makeBanned(Long.valueOf(offerId), false);
        } else if (banned.equals("1")) {
            offerService.makeBanned(Long.valueOf(offerId), true);
        }
        model.addAttribute("success", true);
        model.addAttribute(OFFERS, offerService.findByReported(1));
        return Links.View.ADMIN_OFFER_REPORTED;
    }

}
