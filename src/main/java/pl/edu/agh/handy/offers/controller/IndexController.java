package pl.edu.agh.handy.offers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.handy.offers.service.CategoryService;
import pl.edu.agh.handy.offers.service.OfferService;

import static pl.edu.agh.handy.offers.common.Links.*;

/**
 * Main page controller.
 */
@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    private static final String OFFERS = "offers";
    private static final String CATEGORIES = "categories";

    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = Url.ROOT, method = RequestMethod.GET)
    public String index(Model model) {
        LOGGER.debug("Load application main page.");

        model.addAttribute(OFFERS, offerService.findAll());
        model.addAttribute(CATEGORIES, categoryService.findAll());

        return View.INDEX;
    }
}
