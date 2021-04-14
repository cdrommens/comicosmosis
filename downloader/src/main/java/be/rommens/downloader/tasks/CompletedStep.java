package be.rommens.downloader.tasks;

import be.rommens.downloader.events.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 */
public class CompletedStep implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompletedStep.class);

    private final String context;
    private final ApplicationEventPublisher publisher;

    public CompletedStep(String context, ApplicationEventPublisher publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    public String execute() {
        run();
        return context;
    }


    @Override
    public void run() {
        LOGGER.info("completed folder {}", context);
        publisher.publishEvent(new TaskEvent("completed folder "+ context));
    }

}
