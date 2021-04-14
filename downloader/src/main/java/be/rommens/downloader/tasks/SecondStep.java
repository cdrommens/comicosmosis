package be.rommens.downloader.tasks;

import be.rommens.downloader.events.TaskEvent;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 */
public class SecondStep implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondStep.class);

    private final String context;
    private final ApplicationEventPublisher publisher;

    public SecondStep(String context, ApplicationEventPublisher publisher) {
        this.context = context;
        this.publisher = publisher;
    }

    public String execute() {
        run();
        return context;
    }

    @Override
    public void run() {
        int random = new Random().nextInt(2);
        LOGGER.info("random {} voor {}", random, context);
        if (random == 1) {
            // CompletionException als je wil stoppen!
            //throw new CompletionException(new IllegalStateException("er ging iets mis " + context));
            throw new IllegalStateException("er ging iets mis " + context);
        }
        LOGGER.info("zip folder {}", context);
        publisher.publishEvent(new TaskEvent("zip folder " + context));
        randomDelay();
        LOGGER.info("zip created for {}", context);
        publisher.publishEvent(new TaskEvent("zip created for "+ context));
    }

    private void randomDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
