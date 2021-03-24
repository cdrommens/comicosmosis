package be.rommens.scraper.api.service;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.core.Scraper;
import be.rommens.scraper.core.ScrapingConfig;
import be.rommens.scraper.providers.example.ExampleScraper;
import be.rommens.scraper.providers.readcomics.ReadComicsScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

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

        @Bean
        Scraper exampleScraper() {
            return new ExampleScraper(config());
        }
    }

    @Test
    void testGetReadComicsScraper() {
        assertThat(scraperFactory.createScraper(Provider.READCOMICS)).isInstanceOf(ReadComicsScraper.class);
    }

    @Test
    void testGetExampleScraper() {
        assertThat(scraperFactory.createScraper(Provider.EXAMPLE)).isInstanceOf(ExampleScraper.class);
    }

    @Test
    void testWillThrowErrorWithNoProvider() {
        assertThatIllegalStateException().isThrownBy(() -> scraperFactory.createScraper(null));
    }

}
