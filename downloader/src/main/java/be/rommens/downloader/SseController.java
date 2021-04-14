package be.rommens.downloader;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 https://howtodoinjava.com/spring-boot2/rest/spring-async-controller-sseemitter/
 https://golb.hplar.ch/2017/03/Server-Sent-Events-with-Spring.html
 http://arnaud-nauwynck.github.io/2017/05/15/ServerSentEvent-spring-4-reactive5-to-AngularJS.html
 */
@RestController
public class SseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SseController.class);

    private final Downloader downloader;
    private final EmitterEventService emitterEventService;

    public SseController(Downloader downloader, EmitterEventService emitterEventService) {
        this.downloader = downloader;
        this.emitterEventService = emitterEventService;
    }

    //TODO : use a quartz job to trigger
    @GetMapping("/future-async")
    public String startFutureAsync() {
        List.of("comic1", "comic2", "comic3", "comic4", "comic5", "comic6", "comic7", "comic8", "comic9", "comic10")
                .forEach(downloader::addTask);
        return "ok";
    }

    @GetMapping("/follow")
    public SseEmitter followSse() {
        LOGGER.info("subscribed");
        return emitterEventService.subscribe();
    }



}
