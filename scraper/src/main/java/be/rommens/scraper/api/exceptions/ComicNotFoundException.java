package be.rommens.scraper.api.exceptions;

/**
 * User : cederik
 * Date : 02/04/2020
 * Time : 20:49
 */
public class ComicNotFoundException extends RuntimeException {

    public ComicNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
