package be.rommens.downloader.events;

/**
 *
 */
public class TaskEvent {

    private final String message;

    public TaskEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
