package be.rommens.scraper.api.service;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.core.Scraper;
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
        return applicationContext.getBean(provider.getScraper());
    }
}
