package be.rommens.hades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 15:32
 */
@SpringBootApplication
@EnableBinding(Sink.class)
public class HadesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HadesApplication.class, args);
    }
}
