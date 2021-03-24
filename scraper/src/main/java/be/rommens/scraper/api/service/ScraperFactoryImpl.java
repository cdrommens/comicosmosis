package be.rommens.scraper.api.service;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.core.Scraper;
import be.rommens.scraper.providers.example.ExampleScraper;
import be.rommens.scraper.providers.readcomics.ReadComicsScraper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:03
 */
@Service
public class ScraperFactoryImpl implements ScraperFactory {

    private final ApplicationContext applicationContext;

    public ScraperFactoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Scraper createScraper(Provider provider) {
        if (provider == null) {
            throw new IllegalStateException("Provider must not be null");
        }
        switch (provider) {
            case READCOMICS:
                return applicationContext.getBean(ReadComicsScraper.class);
            case EXAMPLE:
                return applicationContext.getBean(ExampleScraper.class);
            default:
                throw new IllegalStateException("No Scraper found for provider " + provider.name());
        }
    }
}
