package be.rommens.scraper.api.models;

import java.util.List;
import java.util.Objects;

/**
 * User : cederik
 * Date : 03/04/2020
 * Time : 20:30
 */
public class ScrapedIssue {

    private final String comic;
    private final String issueNumber;
    private final Integer numberOfPages;
    private final List<String> pages;

    public ScrapedIssue(String comic, String issueNumber, Integer numberOfPages, List<String> pages) {
        this.comic = comic;
        this.issueNumber = issueNumber;
        this.numberOfPages = numberOfPages;
        this.pages = pages;
    }

    public String getComic() {
        return comic;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public List<String> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "ScrapedIssue{" +
                "comic='" + comic + '\'' +
                ", issueNumber='" + issueNumber + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", pages=" + pages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScrapedIssue)) {
            return false;
        }
        ScrapedIssue that = (ScrapedIssue) o;
        return Objects.equals(getComic(), that.getComic()) && Objects.equals(getIssueNumber(), that.getIssueNumber())
                && Objects.equals(getNumberOfPages(), that.getNumberOfPages()) && Objects.equals(getPages(), that.getPages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComic(), getIssueNumber(), getNumberOfPages(), getPages());
    }
}
