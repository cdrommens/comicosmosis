package be.rommens.scraper.autoconfigure;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 21:04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(classes = MockScraperAutoConfiguration.class)
@PropertyMapping("hera.test.scrapermock")
@ExtendWith(SpringExtension.class)
public @interface AutoConfigureScraperMock {

    String value() default "";
}
