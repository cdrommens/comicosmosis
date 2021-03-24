package be.rommens.scraper.autoconfigure;

import be.rommens.scraper.ScraperMock;
import be.rommens.scraper.api.Provider;
import be.rommens.scraper.api.exceptions.ComicNotFoundException;
import be.rommens.scraper.api.models.ScrapedComic;
import be.rommens.scraper.api.models.ScrapedIssue;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.builders.ScrapedComicBuilder;
import be.rommens.scraper.builders.ScrapedIssueBuilder;
import be.rommens.scraper.builders.ScrapedIssueDetailsBuilder;
import be.rommens.scraper.core.Scraper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * User : cederik
 * Date : 05/04/2020
 * Time : 09:43
 */
@SpringBootTest(classes = MockScraperAutoConfiguration.class)
@AutoConfigureScraperMock(value = "/datasets/scraper-input.yml")
class ScraperAutoConfigurationTest {

    @Autowired
    private ScraperFactory scraperFactory;

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() throws IOException {
        assertThat(scraperFactory).isNotNull();
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);
        assertThat(scraper).isInstanceOf(ScraperMock.class);
        assertThat(scraper.scrapeComic("batman-2016")).isNotNull();
    }

    @Test
    void whenScrapeKnownComic_thenReturnScrapedComic() throws IOException {
        //given
        ScrapedComic scrapedComic = new ScrapedComicBuilder()
            .title("batman-2016")
            .cover("cover1.jpg")
            .publisher("DC Comics")
            .dateOfRelease("2016")
            .status("ONGOING")
            .author("Tom King")
            .summary("summary")
            .addIssue(new ScrapedIssueDetailsBuilder()
                .issue("1")
                .date(LocalDate.of(2016, 1, 1))
                .url("url1"))
            .addIssue(new ScrapedIssueDetailsBuilder()
                .issue("2")
                .date(LocalDate.of(2018, 1, 1))
                .url("url2"))
            .build();
        //when
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);
        ScrapedComic result = scraper.scrapeComic("batman-2016");

        //then
        assertThat(result).isEqualToComparingFieldByField(scrapedComic);
    }

    @Test
    void whenScrapeunknownComic_thenReturnException() throws IOException {
        //when
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);

        //then
        assertThatThrownBy(() -> scraper.scrapeComic("unknown"))
            .isInstanceOf(ComicNotFoundException.class);
    }

    @Test
    void whenScrapeKnownIssue_thenReturnScrapedIssue() throws IOException {
        //given
        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("batman-2016")
            .issueNumber("1")
            .numberOfPages(3)
            .addPage("page1.txt")
            .addPage("page2.jpg")
            .addPage("page3.jpg")
            .build();
        //when
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);
        ScrapedIssue result = scraper.scrapeIssue("batman-2016", "1");

        //then
        assertThat(result).isEqualToComparingFieldByField(scrapedIssue);
    }

    @Test
    void whenScrapeUnknownIssue_thenReturnException() throws IOException {
        //when
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);

        //then
        assertThatThrownBy(() -> scraper.scrapeIssue("unknown", "1"))
            .isInstanceOf(ComicNotFoundException.class);
    }

    @Test
    void whenDownloadPage_thenReturnBytes() throws IOException {
        //when
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);
        byte[] result = scraper.downloadPage("page1.txt");

        //then
        assertThat(result).isNotNull();
        assertThat(new String(result)).isEqualTo("page1");
    }

    @Test
    void whenDownloadUnknownPage_thenReturnException() throws IOException {
        //when
        Scraper scraper = scraperFactory.createScraper(Provider.READCOMICS);

        //then
        assertThatThrownBy(() -> scraper.downloadPage("unknown.txt"))
            .isInstanceOf(FileNotFoundException.class);
    }
}
