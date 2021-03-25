package be.rommens.scraper.dataset;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:40
 */
public class Issue {

    private String issueNumber;
    private String url;
    private String date;
    private List<String> pages;

    public Issue() {
    }

    public Issue(String issueNumber, String url, String date, List<String> pages) {
        this.issueNumber = issueNumber;
        this.url = url;
        this.date = date;
        this.pages = pages;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public LocalDate getLocalDate() {
        if (StringUtils.isNotEmpty(this.date)) {
            String[] split = this.date.split("-");
            return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        return null;
    }

    public static IssueBuilder builder() {
        return new IssueBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Issue)) {
            return false;
        }
        Issue issue = (Issue) o;
        return Objects.equals(getIssueNumber(), issue.getIssueNumber()) && Objects.equals(getUrl(), issue.getUrl())
                && Objects.equals(getDate(), issue.getDate()) && Objects.equals(getPages(), issue.getPages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIssueNumber(), getUrl(), getDate(), getPages());
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueNumber='" + issueNumber + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", pages=" + pages +
                '}';
    }

    public static class IssueBuilder {
        private String issueNumber;
        private String url;
        private String date;
        private List<String> pages;

        public IssueBuilder issueNumber(String issueNumber) {
            this.issueNumber = issueNumber;
            return this;
        }

        public IssueBuilder url(String url) {
            this.url = url;
            return this;
        }

        public IssueBuilder date(String date) {
            this.date = date;
            return this;
        }

        public IssueBuilder pages(List<String> pages) {
            this.pages = pages;
            return this;
        }

        public Issue build() {
            return new Issue(issueNumber, url, date, pages);
        }
    }
}
