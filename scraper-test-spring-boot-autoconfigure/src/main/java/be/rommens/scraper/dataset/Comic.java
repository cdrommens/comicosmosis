package be.rommens.scraper.dataset;

import java.util.List;
import java.util.Objects;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:39
 */
public class Comic {

    private String key;
    private String title;
    private String publisher;
    private String author;
    private String dateOfRelease;
    private String status;
    private String cover;
    private String summary;
    private List<Issue> issues;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public static ComicBuilder builder() {
        return new ComicBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comic)) {
            return false;
        }
        Comic comic = (Comic) o;
        return Objects.equals(getKey(), comic.getKey()) && Objects.equals(getTitle(), comic.getTitle()) && Objects
                .equals(getPublisher(), comic.getPublisher()) && Objects.equals(getAuthor(), comic.getAuthor()) && Objects
                .equals(getDateOfRelease(), comic.getDateOfRelease()) && Objects.equals(getStatus(), comic.getStatus()) && Objects
                .equals(getCover(), comic.getCover()) && Objects.equals(getSummary(), comic.getSummary()) && Objects
                .equals(getIssues(), comic.getIssues());
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(getKey(), getTitle(), getPublisher(), getAuthor(), getDateOfRelease(), getStatus(), getCover(), getSummary(), getIssues());
    }

    @Override
    public String toString() {
        return "Comic{" +
                "key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", author='" + author + '\'' +
                ", dateOfRelease='" + dateOfRelease + '\'' +
                ", status='" + status + '\'' +
                ", cover='" + cover + '\'' +
                ", summary='" + summary + '\'' +
                ", issues=" + issues +
                '}';
    }

    public static class ComicBuilder {

        private String key;
        private String title;
        private String publisher;
        private String author;
        private String dateOfRelease;
        private String status;
        private String cover;
        private String summary;
        private List<Issue> issues;

        public ComicBuilder key(String key) {
            this.key = key;
            return this;
        }

        public ComicBuilder title(String title) {
            this.title = title;
            return this;
        }
        public ComicBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public ComicBuilder author(String author) {
            this.author = author;
            return this;
        }

        public ComicBuilder dateOfRelease(String dateOfRelease) {
            this.dateOfRelease = dateOfRelease;
            return this;
        }

        public ComicBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ComicBuilder cover(String cover) {
            this.cover = cover;
            return this;
        }

        public ComicBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public ComicBuilder issues(List<Issue> issues) {
            this.issues = issues;
            return this;
        }

        public Comic build() {
            Comic comic = new Comic();
            comic.setKey(key);
            comic.setTitle(title);
            comic.setPublisher(publisher);
            comic.setAuthor(author);
            comic.setDateOfRelease(dateOfRelease);
            comic.setStatus(status);
            comic.setCover(cover);
            comic.setSummary(summary);
            comic.setIssues(issues);
            return comic;
        }
    }
}
