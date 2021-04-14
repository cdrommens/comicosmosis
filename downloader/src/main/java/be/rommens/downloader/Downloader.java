package be.rommens.downloader;

import be.rommens.downloader.tasks.CompletedStep;
import be.rommens.downloader.tasks.FirstStep;
import be.rommens.downloader.tasks.SecondStep;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class Downloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Downloader.class);

    private final ApplicationEventPublisher publisher;
    //private final ExecutorService threadPool = Executors.newFixedThreadPool(2);
    private final @Qualifier("downloaderExecutor") TaskExecutor downloaderExecutor;

    private final List<String> comics = List.of("comic1", "comic2", "comic3", "comic4", "comic5","comic6", "comic7", "comic8", "comic9", "comic10");

    public Downloader(ApplicationEventPublisher publisher, TaskExecutor downloaderExecutor) {
        this.publisher = publisher;
        this.downloaderExecutor = downloaderExecutor;
    }

    public void addTask(String comic) {
        CompletableFuture
                .runAsync(new FirstStep(comic, publisher), downloaderExecutor)
                .thenRun(new SecondStep(comic, publisher))
                .thenRun(new CompletedStep(comic, publisher))
                .exceptionally(ex -> {
                    LOGGER.error(ex.getMessage());
                    return null;
                });
    }

}
