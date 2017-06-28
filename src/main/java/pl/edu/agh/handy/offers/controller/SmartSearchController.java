package pl.edu.agh.handy.offers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.handy.offers.common.Links;
import pl.edu.agh.handy.offers.dto.OfferDto;
import pl.edu.agh.handy.offers.dto.SmartSearchDto;
import pl.edu.agh.handy.offers.model.Category;
import pl.edu.agh.handy.offers.model.Coordinate;
import pl.edu.agh.handy.offers.model.SmartSearch;
import pl.edu.agh.handy.offers.service.CategoryService;
import pl.edu.agh.handy.offers.service.OfferService;
import pl.edu.agh.handy.offers.service.SmartSearchService;
import pl.edu.agh.handy.offers.util.DateUtil;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for smart search.
 */
@Controller
public class SmartSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartSearchController.class);
    private static final String OFFERS = "offers";
    private static final String CATEGORIES = "categories";
    private static final DecimalFormat DISTANCE_FORMAT = new DecimalFormat("#.##");

    @Autowired
    private SmartSearchService smartSearchService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = Links.Url.SMART_SEARCH, method = RequestMethod.POST)
    public String doSearch(Model model, SmartSearchDto smartSearchDto) {
        model.addAttribute(CATEGORIES, categoryService.findAll());
        SmartSearch filter = checkFilters(smartSearchDto);
        List<OfferDto> offerDtos = null;

        if (filter == SmartSearch.NO_SEARCH) {
            offerDtos = offerService.findAll();
        }
        if (filter == SmartSearch.KEYWORDS || filter == SmartSearch.KEYWORDS_RADIUS) { //radius without location makes no sense
            offerDtos = offerService.findByKeywords(smartSearchDto.getKeywords());
        }
        if (filter == SmartSearch.LOCATION) {
            List<Coordinate> coordinates = smartSearchService.findCoordinateInRadius(smartSearchDto.getLatitude(),
                    smartSearchDto.getLongitude(), smartSearchDto.getRadiusValue());
            offerDtos = filterOffersByCoordinatesAndSetDistance(coordinates);
        }
        if (filter == SmartSearch.CATEGORY) {
            Category category = categoryService.findOne(smartSearchDto.getCategoryId());
            offerDtos = offerService.findDtoByCategory(category);
        }
        if (filter == SmartSearch.START_DATE) {
            offerDtos = offerService.findByStartDate(
                    DateUtil.convertToLocalDateTime(smartSearchDto.getFromDate()));
        }
        if (filter == SmartSearch.END_DATE) {
            offerDtos = offerService.findByEndDate(
                    DateUtil.convertToLocalDateTime(smartSearchDto.getToDate()));
        }
        if (filter == SmartSearch.KEYWORDS_LOCATION) { // radius is zero so no results
            offerDtos = Collections.emptyList();
        }
        if (filter == SmartSearch.KEYWORDS_LOCATION_RADIUS) {
            List<Coordinate> coordinates = smartSearchService.findCoordinateInRadiusWithKeywords(smartSearchDto.getLatitude(),
                    smartSearchDto.getLongitude(), smartSearchDto.getRadiusValue(), smartSearchDto.getKeywords());
            offerDtos = filterOffersByCoordinatesAndSetDistance(coordinates);
        }
        if (filter == SmartSearch.KEYWORDS_CATEGORY) {
            Category category = categoryService.findOne(smartSearchDto.getCategoryId());
            offerDtos = offerService.findByKeywordsAndCategory(smartSearchDto.getKeywords(), category);
        }
        if (filter == SmartSearch.KEYWORDS_START_DATE) {
            offerDtos = offerService.findByKeywordsAndStartDate(smartSearchDto.getKeywords(),
                    DateUtil.convertToLocalDateTime(smartSearchDto.getFromDate()));
        }
        if (filter == SmartSearch.KEYWORDS_END_DATE) {
            offerDtos = offerService.findByKeywordsAndEndDate(smartSearchDto.getKeywords(),
                    DateUtil.convertToLocalDateTime(smartSearchDto.getToDate()));
        }
        if (filter == SmartSearch.KEYWORDS_START_DATE_END_DATE) {
            offerDtos = offerService.findByKeywordsAndStartDateAndEndDate(smartSearchDto.getKeywords(),
                    DateUtil.convertToLocalDateTime(smartSearchDto.getFromDate()),
                    DateUtil.convertToLocalDateTime(smartSearchDto.getToDate()));
        }
        if (filter == SmartSearch.KEYWORDS_LOCATION_CATEGORY_RADIUS) {
            List<Coordinate> coordinates = smartSearchService.findCoordinateInRadiusWithCategoryAndKeywords(
                    smartSearchDto.getLatitude(), smartSearchDto.getLongitude(), smartSearchDto.getRadiusValue(),
                    smartSearchDto.getCategoryId(), smartSearchDto.getKeywords());
            offerDtos = filterOffersByCoordinatesAndSetDistance(coordinates);
        }
        if (filter == SmartSearch.KEYWORDS_LOCATION_START_DATE_RADIUS ||
                filter == SmartSearch.KEYWORDS_LOCATION_END_DATE_RADIUS ||
                 filter == SmartSearch.KEYWORDS_LOCATION_START_DATE_END_DATE_RADIUS) {
            List<Coordinate> coordinates = smartSearchService.findCoordinateInRadiusWithKeywords(
                    smartSearchDto.getLatitude(), smartSearchDto.getLongitude(), smartSearchDto.getRadiusValue(),
                    smartSearchDto.getKeywords());
            offerDtos = filterOffersByCoordinatesAndDatesAndSetDistance(coordinates, smartSearchDto);
        }
        if (filter == SmartSearch.KEYWORDS_LOCATION_START_DATE_CATEGORY_RADIUS ||
                filter == SmartSearch.KEYWORDS_LOCATION_END_DATE_CATEGORY_RADIUS ||
                filter == SmartSearch.KEYWORDS_LOCATION_START_DATE_END_DATE_CATEGORY_RADIUS) {
            List<Coordinate> coordinates = smartSearchService.findCoordinateInRadiusWithCategoryAndKeywords(
                    smartSearchDto.getLatitude(), smartSearchDto.getLongitude(), smartSearchDto.getRadiusValue(),
                    smartSearchDto.getCategoryId(), smartSearchDto.getKeywords());
            offerDtos = filterOffersByCoordinatesAndDatesAndSetDistance(coordinates, smartSearchDto);
        }

        model.addAttribute(OFFERS, offerDtos);
        return Links.View.INDEX;
    }

    private SmartSearch checkFilters(SmartSearchDto smartSearchDto) {
        List<SmartSearch> filters = new ArrayList<>();
        if (!StringUtils.isEmpty(smartSearchDto.getKeywords())) {
            filters.add(SmartSearch.KEYWORDS);
        }
        if (!StringUtils.isEmpty(smartSearchDto.getLatitude()) && !StringUtils.isEmpty(smartSearchDto.getLongitude())) {
            filters.add(SmartSearch.LOCATION);
        }
        if (!StringUtils.isEmpty(smartSearchDto.getFromDate())) {
            filters.add(SmartSearch.START_DATE);
        }
        if (!StringUtils.isEmpty(smartSearchDto.getToDate())) {
            filters.add(SmartSearch.END_DATE);
        }
        if (smartSearchDto.getCategoryId() != 0) {
            filters.add(SmartSearch.CATEGORY);
        }
        if (smartSearchDto.getRadiusValue() != 0) {
            filters.add(SmartSearch.RADIUS);
        }

        if (filters.isEmpty()) {
            return SmartSearch.NO_SEARCH;
        }

        return prepareComplexFilters(filters);
    }

    private SmartSearch prepareComplexFilters(List<SmartSearch> simpleFilters) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (SmartSearch filter : simpleFilters) {
            sb.append(prefix);
            sb.append(SmartSearch.getName(filter));
            prefix = "_";
        }

        try {
            return SmartSearch.valueOf(sb.toString());
        } catch (IllegalArgumentException e) {
            return SmartSearch.NO_SEARCH;
        }
    }

    private List<OfferDto> filterOffersByCoordinatesAndSetDistance(List<Coordinate> coordinates) {
        List<Long> offerIds = mapCoordinatesToOfferIds(coordinates);

        List<OfferDto> offers = coordinates.isEmpty() ? Collections.emptyList() :
                offerService.findByIdIn(offerIds);

        setDistanceForOffer(coordinates, offers);

        return offers;
    }

    private List<Long> mapCoordinatesToOfferIds(List<Coordinate> coordinates) {
        return coordinates
                    .stream()
                    .map(c -> c.getOfferId())
                    .collect(Collectors.toList());
    }

    private void setDistanceForOffer(List<Coordinate> coordinates, List<OfferDto> offers) {
        for (int i = 0; i < offers.size(); i++) {
            offers.get(i).setDistance(DISTANCE_FORMAT.format(coordinates.get(i).getDistance()));
        }
    }

    /**
     * Check if offer is between dates.
     *
     * If start date is empty - it's set to current date by default.
     * If end date is empty - it's set to current date + 7 days from now.
     *
     * @param coordinates
     * @param smartSearchDto
     * @return list of offer dto
     */
    private List<OfferDto> filterOffersByCoordinatesAndDatesAndSetDistance(List<Coordinate> coordinates,
                                                                           SmartSearchDto smartSearchDto) {
        List<Long> offerIds = mapCoordinatesToOfferIds(coordinates);

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        if (StringUtils.isEmpty(smartSearchDto.getFromDate())) {
            fromDate = LocalDateTime.now();
        } else {
            fromDate = DateUtil.convertToLocalDateTime(smartSearchDto.getFromDate());
        }

        if (StringUtils.isEmpty(smartSearchDto.getToDate())) {
            toDate = LocalDateTime.now().plus(7, ChronoUnit.DAYS);
        } else {
            toDate = DateUtil.convertToLocalDateTime(smartSearchDto.getToDate());
        }

        List<OfferDto> offers = coordinates.isEmpty() ? Collections.emptyList() :
                offerService.findByIdInAndBetweenDates(offerIds, fromDate, toDate);

        setDistanceForOffer(coordinates, offers);

        return offers;
    }
}
