package be.rommens.scraper.autoconfigure;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.api.service.ScraperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 05/04/2020
 * Time : 09:43
 */
@SpringBootTest(classes = ScraperAutoConfiguration.class)
class ScraperAutoConfigurationTest {

    @Autowired
    private ScraperFactory scraperFactory;

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
        assertThat(scraperFactory).isNotNull();
        assertThat(scraperFactory.createScraper(Provider.READCOMICS)).isNotNull();
        assertThat(scraperFactory.createScraper(Provider.EXAMPLE)).isNotNull();

    }
}
