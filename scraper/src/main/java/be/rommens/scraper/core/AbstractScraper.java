package be.rommens.scraper.core;

import be.rommens.scraper.api.exceptions.ComicNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:38
 */

public abstract class AbstractScraper implements Scraper {

    protected final ScrapingConfig scrapingConfig;

    public AbstractScraper(ScrapingConfig scrapingConfig) {
        this.scrapingConfig = scrapingConfig;
    }

    protected Document getSource(String url) throws IOException {
        return Jsoup.connect(url)
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .header("Accept-Language", "en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7,la;q=0.6")
            .header("Connection", "keep-alive")
            .userAgent(RandomUserAgent.getRandomUserAgent())
            .header("Pragma", "no-cache")
            .header("Host", "google.com")
            .get();
    }

    protected URLConnection getUrlConnection(String url) throws IOException {
        URL myURL = new URL(url);
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
        myURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        myURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7,la;q=0.6");
        myURLConnection.setRequestProperty("Connection", "keep-alive");
        myURLConnection.setRequestProperty("User-Agent", RandomUserAgent.getRandomUserAgent());
        myURLConnection.setRequestProperty("Pragma", "no-cache");
        myURLConnection.setRequestProperty("Host", "google.com");
        return myURLConnection;
    }

    protected ComicNotFoundException throwComicNotFound(String technicalComicName, Exception ex) {
        return new ComicNotFoundException("URL for " + technicalComicName + " is not found", ex);
    }

    protected ComicNotFoundException throwIssueNotFound(String technicalComicName, String issue, Exception ex) {
        return new ComicNotFoundException("URL for " + technicalComicName + " with issue " + issue + " is not found", ex);
    }

    protected FileNotFoundException throwPageNotFound(String url) {
        return new FileNotFoundException("Resource on " + url + " is not found");
    }

    protected abstract String buildUrlForComic(String technicalComicName);

    protected abstract String buildUrlForIssue(String technicalComicName, String issue);

}
