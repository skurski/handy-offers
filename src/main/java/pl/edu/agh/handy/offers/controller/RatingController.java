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
import pl.edu.agh.handy.offers.service.OfferService;
import pl.edu.agh.handy.offers.service.RatingService;
import pl.edu.agh.handy.offers.service.UserService;

@Controller
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    private static final String CATEGORIES = "categories";

    @Autowired
    private UserService userService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private RatingService ratingService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_OPINION_FORM, method = RequestMethod.POST)
    public String opinionForm(String offerId, Model model) {
        model.addAttribute("offerId", offerId);
        return Links.View.OFFER_OPINION;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = Links.Url.OFFER_OPINION_ADD, method = RequestMethod.POST)
    public String opinionAdd(String opinion, String offerId, String stars, Model model) {
        ratingService.addOpinion(offerId, opinion, stars);
        model.addAttribute("done", true);
        return Links.View.OFFER_OPINION;
    }
}
