package be.rommens.downloader;

import be.rommens.downloader.events.TaskEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 */
@Service
public class EmitterEventService {

    final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    final DownloaderProgressService downloaderProgressService;

    public EmitterEventService(DownloaderProgressService downloaderProgressService) {
        this.downloaderProgressService = downloaderProgressService;
    }


    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter();
        downloaderProgressService.getFullHistory().forEach(event -> {
            try {
                emitter.send(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    public void sendMessage(TaskEvent message) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .data(message));
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });
        emitters.removeAll(deadEmitters);
        downloaderProgressService.addHistoryEvent(message);
    }

    public void complete() {
        emitters.forEach(ResponseBodyEmitter::complete);
        emitters.clear();
    }

}
