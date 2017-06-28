package pl.edu.agh.handy.offers.exception;

/**
 * Only empty categories can be deleted from admin UI.
 */
public class CategoryNotEmpty extends Exception {

    public CategoryNotEmpty(String message) {
        super(message);
    }
}
