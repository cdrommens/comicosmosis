package be.rommens.scraper.api.service;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.core.Scraper;

/**
 * User : cederik
 * Date : 14/05/2020
 * Time : 10:02
 */
public interface ScraperFactory {
    Scraper createScraper(Provider provider);
}
