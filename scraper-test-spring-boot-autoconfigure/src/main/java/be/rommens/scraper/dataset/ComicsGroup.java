package be.rommens.scraper.dataset;

import java.util.List;
import java.util.Objects;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:55
 */
public class ComicsGroup {

    private List<Comic> comics;

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComicsGroup)) {
            return false;
        }
        ComicsGroup that = (ComicsGroup) o;
        return Objects.equals(getComics(), that.getComics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComics());
    }

    @Override
    public String toString() {
        return "ComicsGroup{" +
                "comics=" + comics +
                '}';
    }
}
