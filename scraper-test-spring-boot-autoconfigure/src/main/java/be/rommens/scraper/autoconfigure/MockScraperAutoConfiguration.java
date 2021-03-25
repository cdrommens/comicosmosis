package be.rommens.scraper.autoconfigure;

import be.rommens.scraper.ScraperFactoryMock;
import be.rommens.scraper.ScraperMock;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.core.Scraper;
import be.rommens.scraper.dataset.Comic;
import be.rommens.scraper.dataset.DataSetParser;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:46
 */
@Configuration
public class MockScraperAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockScraperAutoConfiguration.class);
    private final Environment environment;

    public MockScraperAutoConfiguration(Environment environment) {
        this.environment = environment;
    }

    private Scraper createMockScraper(List<Comic> givenResults) {
        return new ScraperMock(null, givenResults);
    }

    @Bean
    public ScraperFactory createScraperFactoryMock() {
        String dataset = this.environment.getProperty("hera.test.scrapermock.value");
        LOGGER.info("using dataset for mocking the scraper = {}", dataset);
        List<Comic> parsedComics = processDataSet(dataset);
        return new ScraperFactoryMock(createMockScraper(parsedComics));
    }

    private List<Comic> processDataSet(String dataset) {
        DataSetParser parser = new DataSetParser(dataset);
        return parser.parseDataSet();
    }

}
