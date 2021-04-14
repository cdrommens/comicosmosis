package be.rommens.downloader.events;

/**
 *
 */
public class CompletedEvent {

    private final String message;

    public CompletedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
