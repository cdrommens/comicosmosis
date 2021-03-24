package be.rommens.scraper.api.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * User : cederik
 * Date : 02/04/2020
 * Time : 20:16
 */
public class ScrapedIssueDetails {

    private final String issue;
    private final String url;
    private final LocalDate date;

    public ScrapedIssueDetails(String issue, String url, LocalDate date) {
        this.issue = issue;
        this.url = url;
        this.date = date;
    }

    public String getIssue() {
        return issue;
    }

    public String getUrl() {
        return url;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ScrapedIssueDetails{" +
                "issue='" + issue + '\'' +
                ", url='" + url + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScrapedIssueDetails)) {
            return false;
        }
        ScrapedIssueDetails that = (ScrapedIssueDetails) o;
        return Objects.equals(getIssue(), that.getIssue()) && Objects.equals(getUrl(), that.getUrl()) && Objects
                .equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIssue(), getUrl(), getDate());
    }
}
