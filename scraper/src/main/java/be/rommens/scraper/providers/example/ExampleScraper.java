package be.rommens.scraper.providers.example;

import be.rommens.scraper.api.models.ScrapedComic;
import be.rommens.scraper.api.models.ScrapedIssue;
import be.rommens.scraper.core.AbstractScraper;
import be.rommens.scraper.core.ScrapingConfig;
import be.rommens.scraper.core.ScrapingConfigParams;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:57
 */
public class ExampleScraper extends AbstractScraper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleScraper.class);

    public ExampleScraper(ScrapingConfig scrapingConfig) {
        super(scrapingConfig);
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + "/comic/" + technicalComicName;
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + "/comic" + technicalComicName + "/issue/" + issue;
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) {
        ScrapedComic scrapedComic = new ScrapedComic();
        scrapedComic.setTitle(scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL) );
        return scrapedComic;
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        return null;
    }

    @Override
    public byte[] downloadPage(String url) throws FileNotFoundException {
        return new byte[0];
    }
}
