package be.rommens.hades.assembler;

import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.scraper.api.models.ScrapedIssue;
import be.rommens.scraper.core.Scraper;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:52
 */
@RequiredArgsConstructor
public class IssueAssemblyContext {

    private final DownloadIssueMessage downloadIssueMessage;
    private final String baseUrl;
    private final Scraper scraper;

    private String issueFolder;
    private ScrapedIssue scrapedIssue;

    public DownloadIssueMessage getDownloadIssueMessage() {
        return downloadIssueMessage;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getIssueFolder() {
        return issueFolder;
    }

    public void setIssueFolder(String issueFolder) {
        this.issueFolder = issueFolder;
    }

    public ScrapedIssue getScrapedIssue() {
        return scrapedIssue;
    }

    public void setScrapedIssue(ScrapedIssue scrapedIssue) {
        this.scrapedIssue = scrapedIssue;
    }

    public Scraper getScraper() {
        return scraper;
    }
}
