package be.rommens.downloader.config;

import java.time.Duration;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean("mainExecutor")
    public TaskExecutor mainExecutor() {
        return new TaskExecutorBuilder().build();
    }

    @Bean("downloaderExecutor")
    public TaskExecutor downloaderExecutor() {
        return new TaskExecutorBuilder()
                .corePoolSize(2)
                .maxPoolSize(2)
                .queueCapacity(1000)
                .threadNamePrefix("downloader-executor-")
                .awaitTermination(true)
                .awaitTerminationPeriod(Duration.ofMinutes(5))
                .allowCoreThreadTimeOut(false)
                .build();
    }


}
