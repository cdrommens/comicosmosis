package be.rommens.scraper.builders;

import be.rommens.scraper.api.models.ScrapedIssue;

import java.util.ArrayList;
import java.util.List;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 08:43
 */
public final class ScrapedIssueBuilder {

    private String comic;
    private String issueNumber;
    private Integer numberOfPages;
    private List<String> pages = new ArrayList<>();

    public ScrapedIssueBuilder comic(String comic) {
        this.comic = comic;
        return this;
    }

    public ScrapedIssueBuilder issueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
        return this;
    }

    public ScrapedIssueBuilder numberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public ScrapedIssueBuilder addPage(String page) {
        this.pages.add(page);
        return this;
    }

    public ScrapedIssueBuilder addPages(List<String> pages) {
        this.pages.addAll(pages);
        return this;
    }

    public ScrapedIssue build() {
        return new ScrapedIssue(comic, issueNumber, numberOfPages, pages);
    }
}
