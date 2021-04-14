package be.rommens.downloader.tasks;

import be.rommens.downloader.events.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 */
public class FirstStep implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstStep.class);

    private final String context;
    private final ApplicationEventPublisher publisher;

    public FirstStep(String context, ApplicationEventPublisher publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    public String execute() {
        run();
        return context;
    }


    @Override
    public void run() {
        LOGGER.info("create folder {}", context);
        publisher.publishEvent(new TaskEvent("create folder "+ context));
        LOGGER.info("get list of all pages for {}", context);
        publisher.publishEvent(new TaskEvent("get list of all pages for "+ context));
        for (int i = 0; i < 5; i++) {
            LOGGER.info("get page {} of {}", i, context);
            publisher.publishEvent(new TaskEvent("get page " + i + " of " + context));
            randomDelay();
        }
        LOGGER.info("All downloaded for {}", context);
        publisher.publishEvent(new TaskEvent("All downloaded for "+ context));
    }

    private void randomDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
