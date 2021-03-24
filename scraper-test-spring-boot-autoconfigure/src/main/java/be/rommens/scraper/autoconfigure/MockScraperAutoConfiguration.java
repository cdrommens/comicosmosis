package be.rommens.scraper.autoconfigure;

import be.rommens.scraper.ScraperFactoryMock;
import be.rommens.scraper.ScraperMock;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.core.Scraper;
import be.rommens.scraper.dataset.Comic;
import be.rommens.scraper.dataset.DataSetParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:46
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MockScraperAutoConfiguration {

    private final Environment environment;

    private Scraper createMockScraper(List<Comic> givenResults) {
        return new ScraperMock(null, givenResults);
    }

    @Bean
    public ScraperFactory createScraperFactoryMock() {
        String dataset = this.environment.getProperty("hera.test.scrapermock.value");
        log.info("using dataset for mocking the scraper = {}", dataset);
        List<Comic> parsedComics = processDataSet(dataset);
        return new ScraperFactoryMock(createMockScraper(parsedComics));
    }

    private List<Comic> processDataSet(String dataset) {
        DataSetParser parser = new DataSetParser(dataset);
        return parser.parseDataSet();
    }

}
