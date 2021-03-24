package be.rommens.scraper.api.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User : cederik
 * Date : 02/04/2020
 * Time : 20:04
 */

public class ScrapedComic {

    private String title;
    private String publisher;
    private String author;
    private String dateOfRelease;
    private String status;          //TODO : after inventory, convert to enum
    private String cover;
    private String summary;
    private Set<ScrapedIssueDetails> issues = new HashSet<>();

    public void addIssue(ScrapedIssueDetails issue) {
        issues.add(issue);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Set<ScrapedIssueDetails> getIssues() {
        return issues;
    }

    @Override
    public String toString() {
        return "ScrapedComic{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", author='" + author + '\'' +
                ", dateOfRelease='" + dateOfRelease + '\'' +
                ", status='" + status + '\'' +
                ", cover='" + cover + '\'' +
                ", summary='" + summary + '\'' +
                ", issues=" + issues +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScrapedComic)) {
            return false;
        }
        ScrapedComic that = (ScrapedComic) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getPublisher(), that.getPublisher()) && Objects
                .equals(getAuthor(), that.getAuthor()) && Objects.equals(getDateOfRelease(), that.getDateOfRelease()) && Objects
                .equals(getStatus(), that.getStatus()) && Objects.equals(getCover(), that.getCover()) && Objects
                .equals(getSummary(), that.getSummary()) && Objects.equals(getIssues(), that.getIssues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPublisher(), getAuthor(), getDateOfRelease(), getStatus(), getCover(), getSummary(), getIssues());
    }
}
