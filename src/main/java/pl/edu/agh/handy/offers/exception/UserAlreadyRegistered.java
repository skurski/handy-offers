package pl.edu.agh.handy.offers.exception;


public class UserAlreadyRegistered extends Exception {

    public UserAlreadyRegistered(String message) {
        super(message);
    }
}