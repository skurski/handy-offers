package pl.edu.agh.handy.offers.common;

/**
 * All common constants in application.
 */
public class Links {

    public static class Url {
        public static final String ROOT = "/";
        public static final String OFFER = ROOT + "offer";
        public static final String ADMIN = ROOT + "admin";
        public static final String LOGIN = ROOT + "login";
        public static final String FORBIDDEN = ROOT + "403";

        public static final String USER = ROOT + "user";
        public static final String USER_REGISTER = USER + "/register";
        public static final String USER_REMOVE = USER + "/remove";
        public static final String USER_ACCOUNT = USER + "/account";
        public static final String USER_ACCOUNT_EDIT = USER + "/account/edit";

        public static final String OFFER_ADD = OFFER + "/add";
        public static final String OFFER_MANAGE = OFFER + "/manage";
        public static final String OFFER_DETAILS = OFFER + "/details";
        public static final String OFFER_EDIT = OFFER + "/edit";
        public static final String OFFER_DELETE = OFFER + "/delete";
        public static final String OFFER_REPORT = OFFER + "/report";
        public static final String OFFER_RESERVE = OFFER + "/reserve";
        public static final String OFFER_RESERVE_MANAGE = OFFER + "/reserve/manage";
        public static final String OFFER_RESERVE_CANCEL = OFFER + "/reserve/cancel";
        public static final String OFFER_FEEDBACK = OFFER + "/feedback";
        public static final String OFFER_OPINION_FORM = OFFER + "/opinion/form";
        public static final String OFFER_OPINION_ADD = OFFER + "/opinion/add";

        public static final String ADMIN_USER = ADMIN + "/user/list";
        public static final String ADMIN_CATEGORY = ADMIN + "/category/list";
        public static final String ADMIN_USER_UPDATE = ADMIN + "/user/update";
        public static final String ADMIN_CATEGORY_UPDATE = ADMIN + "/category/update";
        public static final String ADMIN_CATEGORY_ADD = ADMIN + "/category/add";
        public static final String ADMIN_OFFER_REPORTED = ADMIN + "/offer/reported";
        public static final String ADMIN_OFFER_UPDATE = ADMIN + "/offer/update";

        public static final String SMART_SEARCH = ROOT + "smartsearch/search";
    }

    public static class View {
        public static final String INDEX = "index";
        public static final String ADMIN = "admin";
        public static final String LOGIN = "login";
        public static final String FORBIDDEN = "403";

        public static final String USER_REGISTER = "user/register";
        public static final String USER_REMOVE = "user/remove";
        public static final String USER_ACCOUNT = "user/account";

        public static final String OFFER_ADD = "offer/add";
        public static final String OFFER_MANAGE = "offer/manage";
        public static final String OFFER_DETAILS = "offer/details";
        public static final String OFFER_EDIT = "offer/edit";
        public static final String OFFER_RESERVATIONS = "offer/reservation";
        public static final String OFFER_OPINION = "offer/opinion";

        public static final String ADMIN_MAIN = ADMIN + "/main";
        public static final String ADMIN_USER = ADMIN + "/user";
        public static final String ADMIN_CATEGORY = ADMIN + "/category";
        public static final String ADMIN_OFFER_REPORTED = ADMIN + "/offer";
    }

    private Links() {}
}
