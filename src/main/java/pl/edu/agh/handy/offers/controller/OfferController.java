package pl.edu.agh.handy.offers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.handy.offers.common.Links;
import pl.edu.agh.handy.offers.dto.OfferDto;
import pl.edu.agh.handy.offers.dto.ReservationDto;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.exception.ReservationException;
import pl.edu.agh.handy.offers.model.User;
import pl.edu.agh.handy.offers.service.CategoryService;
import pl.edu.agh.handy.offers.service.OfferService;
import pl.edu.agh.handy.offers.service.ReservationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Offer controller.
 */
@Controller
public class OfferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String CATEGORIES = "categories";

    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReservationService reservationService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_ADD, method = RequestMethod.GET)
    public String addOffer(Model model) {
        model.addAttribute(CATEGORIES, categoryService.findAll());
        model.addAttribute("offerDto", new OfferDto());
        return Links.View.OFFER_ADD;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_ADD, method = RequestMethod.POST)
    public String addOffer(@Valid OfferDto offerDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CATEGORIES, categoryService.findAll());
            return Links.View.OFFER_ADD;
        }
        offerService.addOffer(offerDto);
        return "redirect:" + Links.Url.OFFER_MANAGE;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_DELETE, method = RequestMethod.POST)
    public String deleteOffer(OfferDto offerDto) {
        try {
            offerService.delete(offerDto);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        return "redirect:" + Links.Url.OFFER_MANAGE;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_MANAGE, method = RequestMethod.GET)
    public String manageOffer(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OfferDto> offers = offerService.findByUser(user);
        model.addAttribute("userOffers", offers);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.OFFER_MANAGE;
    }

    @RequestMapping(value = Links.Url.OFFER_DETAILS + "/{id}", method = RequestMethod.GET)
    public String details(@PathVariable String id, Model model) {
        OfferDto offerDto = offerService.findById(Long.valueOf(id));
        model.addAttribute("offer", offerDto);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.OFFER_DETAILS;
    }

    @RequestMapping(value = Links.Url.OFFER_EDIT + "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable String id, Model model) {
        OfferDto offerDto = offerService.findById(Long.valueOf(id));
        model.addAttribute("offerDto", offerDto);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.OFFER_EDIT;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_EDIT, method = RequestMethod.POST)
    public String edit(@Valid OfferDto offerDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CATEGORIES, categoryService.findAll());
            return Links.View.OFFER_EDIT;
        }
        offerService.updateOffer(offerDto);
        return "redirect:" + Links.Url.OFFER_MANAGE;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_REPORT + "/{id}", method = RequestMethod.GET)
    public String report(@PathVariable String id, Model model) {
        OfferDto offerDto = offerService.makeReported(Long.valueOf(id));
        model.addAttribute("offer", offerDto);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.OFFER_DETAILS;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_RESERVE, method = RequestMethod.POST)
    public String reserveOffer(ReservationDto reservationDto, Model model) {
        try {
            offerService.reserveOffer(reservationDto);
        } catch (ReservationException e) {
            LOGGER.info(e.getMessage());

            OfferDto offerDto = offerService.findById(Long.valueOf(reservationDto.getOfferId()));
            model.addAttribute("offer", offerDto);
            model.addAttribute(CATEGORIES, categoryService.findAll());
            model.addAttribute("thresholdAlert", true);
            return Links.View.OFFER_DETAILS;
        }

        return "redirect:" + Links.Url.OFFER_RESERVE_MANAGE;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_RESERVE_CANCEL, method = RequestMethod.POST)
    public String reserveOfferCancel(ReservationDto reservationDto) {
        offerService.cancelReservation(reservationDto);
        return "redirect:" + Links.Url.OFFER_RESERVE_MANAGE;
    }


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_RESERVE_MANAGE, method = RequestMethod.GET)
    public String reserveOfferManage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userReservations", reservationService.findCurrentByUser(user));
        model.addAttribute("feedback", false);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.OFFER_RESERVATIONS;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_FEEDBACK, method = RequestMethod.GET)
    public String feedback(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userReservations", reservationService.findPastByUser(user));
        model.addAttribute("feedback", true);
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return Links.View.OFFER_RESERVATIONS;
    }
}