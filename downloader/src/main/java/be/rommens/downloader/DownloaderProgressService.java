package be.rommens.downloader;

import be.rommens.downloader.events.TaskEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class DownloaderProgressService {

    private final List<TaskEvent> history = new CopyOnWriteArrayList<>();

    public List<TaskEvent> getFullHistory() {
        return history;
    }

    public void addHistoryEvent(TaskEvent event) {
        history.add(event);
    }


}
