package be.rommens.downloader;

import be.rommens.downloader.events.CompletedEvent;
import be.rommens.downloader.events.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 */
@Async("mainExecutor")
@Component
public class TaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskListener.class);

    private final EmitterEventService emitterEventService;

    public TaskListener(EmitterEventService emitterEventService) {
        this.emitterEventService = emitterEventService;
    }

    @EventListener
    public void handleTaskEvent(TaskEvent event) {
        LOGGER.info("EVENT RECEIVED {}" , event.getMessage());
        emitterEventService.sendMessage(event);
    }

    @EventListener
    public void handleTaskEvent(CompletedEvent event) {
        LOGGER.info("EVENT COMPLETED RECEIVED {}" , event.getMessage());
        emitterEventService.complete();
    }
}
