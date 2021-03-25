package be.rommens.scraper.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.core.Scraper;
import be.rommens.scraper.core.ScrapingConfig;
import be.rommens.scraper.providers.readcomics.ReadComicsScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * User : cederik
 * Date : 07/04/2020
 * Time : 15:11
 */
@SpringBootTest(classes = {ScraperFactoryImpl.class, ScraperFactoryTest.TestConfig.class})
class ScraperFactoryTest {

    @Autowired
    private ScraperFactory scraperFactory;

    @TestConfiguration
    static class TestConfig {
        @Autowired
        private Environment environment;

        @Bean
        ScrapingConfig config() {
            return new ScrapingConfig();
        }

        @Bean
        Scraper readComicsScraper() {
            return new ReadComicsScraper(config());
        }
    }

    @Test
    void testGetReadComicsScraper() {
        assertThat(scraperFactory.createScraper(Provider.READCOMICS)).isInstanceOf(ReadComicsScraper.class);
    }

    @Test
    void testWillThrowErrorWithNoProvider() {
        assertThatIllegalStateException().isThrownBy(() -> scraperFactory.createScraper(null));
    }

}
