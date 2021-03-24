package be.rommens.scraper.dataset;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:40
 */
@ToString@EqualsAndHashCode
@Builder
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
}
