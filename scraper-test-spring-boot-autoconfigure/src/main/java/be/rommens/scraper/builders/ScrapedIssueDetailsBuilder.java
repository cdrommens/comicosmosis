package be.rommens.scraper.builders;

import be.rommens.scraper.api.models.ScrapedIssueDetails;
import java.time.LocalDate;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 08:47
 */
public final class ScrapedIssueDetailsBuilder {

    private String issue;
    private String url;
    private LocalDate date;

    public ScrapedIssueDetailsBuilder issue(String issue) {
        this.issue = issue;
        return this;
    }

    public ScrapedIssueDetailsBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ScrapedIssueDetailsBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public ScrapedIssueDetails build() {
        return new ScrapedIssueDetails(issue, url, date);
    }
}
