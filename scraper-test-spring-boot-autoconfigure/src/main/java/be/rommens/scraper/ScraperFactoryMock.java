package be.rommens.scraper;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.core.Scraper;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 14/05/2020
 * Time : 10:03
 */
@RequiredArgsConstructor
public class ScraperFactoryMock implements ScraperFactory {

    private final Scraper scraper;

    @Override
    public Scraper createScraper(Provider provider) {
        return scraper;
    }
}
