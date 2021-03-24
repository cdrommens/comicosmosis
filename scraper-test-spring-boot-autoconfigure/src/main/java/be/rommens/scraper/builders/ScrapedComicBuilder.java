package be.rommens.scraper.builders;

import be.rommens.scraper.api.models.ScrapedComic;
import be.rommens.scraper.api.models.ScrapedIssueDetails;

import java.util.HashSet;
import java.util.Set;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 08:31
 */
public final class ScrapedComicBuilder {

    private String title;
    private String publisher;
    private String author;
    private String dateOfRelease;
    private String status;          //TODO : after inventory, convert to enum
    private String cover;
    private String summary;
    private Set<ScrapedIssueDetails> issues = new HashSet<>();

    public ScrapedComicBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ScrapedComicBuilder publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public ScrapedComicBuilder author(String author) {
        this.author = author;
        return this;
    }

    public ScrapedComicBuilder dateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
        return this;
    }

    public ScrapedComicBuilder status(String status) {
        this.status = status;
        return this;
    }

    public ScrapedComicBuilder cover(String cover) {
        this.cover = cover;
        return this;
    }

    public ScrapedComicBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public ScrapedComicBuilder addIssue(ScrapedIssueDetailsBuilder issueDetailsBuilder) {
        this.issues.add(issueDetailsBuilder.build());
        return this;
    }

    public ScrapedComic build() {
        ScrapedComic scrapedComic = new ScrapedComic();
        scrapedComic.setTitle(title);
        scrapedComic.setPublisher(publisher);
        scrapedComic.setAuthor(author);
        scrapedComic.setDateOfRelease(dateOfRelease);
        scrapedComic.setStatus(status);
        scrapedComic.setCover(cover);
        scrapedComic.setSummary(summary);
        issues.forEach(scrapedComic::addIssue);
        return scrapedComic;
    }

}
