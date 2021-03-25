package be.rommens.scraper.api;

import be.rommens.scraper.core.Scraper;
import be.rommens.scraper.providers.readcomics.ReadComicsScraper;

/**
 * User : cederik
 * Date : 31/03/2020
 * Time : 15:42
 */
public enum Provider {

    READCOMICS("readcomics", ReadComicsScraper.class);

    private final String propertyName;
    private final Class<? extends Scraper> scraper;

    Provider(String propertyName, Class<? extends Scraper> scraper) {
        this.propertyName = propertyName;
        this.scraper = scraper;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<? extends Scraper> getScraper() {
        return scraper;
    }
}
