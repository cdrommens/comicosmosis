package be.rommens.scraper.core;

import be.rommens.scraper.api.models.ScrapedComic;
import be.rommens.scraper.api.models.ScrapedIssue;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:54
 */
public interface Scraper {

    ScrapedComic scrapeComic(String technicalComicName);

    ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException;

    byte[] downloadPage(String url) throws FileNotFoundException;
}
