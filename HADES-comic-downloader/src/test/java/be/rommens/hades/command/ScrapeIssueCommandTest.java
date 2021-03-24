package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import be.rommens.scraper.api.Provider;
import be.rommens.scraper.api.models.ScrapedIssue;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.autoconfigure.AutoConfigureScraperMock;
import be.rommens.scraper.builders.ScrapedIssueBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:35
 */
@AutoConfigureScraperMock(value = "/datasets/scrape-issue-command-test-input.yml")
class ScrapeIssueCommandTest {

    @TempDir
    Path tempDir;

    @Autowired
    private ScraperFactory scraperFactory;

    @Test
    void whenIssueExists_thenReturnCompletedAndScrapedIssue() {
        //given
        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("page1")
            .addPage("page2")
            .build();
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(null, scraperFactory.createScraper(Provider.READCOMICS));
        ScrapeIssueCommand command = new ScrapeIssueCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(result).isEqualTo(CommandResult.COMPLETED);
        assertThat(context.getScrapedIssue()).usingRecursiveComparison().isEqualTo(scrapedIssue);
    }

    @Test
    void whenIssueNotExists_thenReturnErrorAndNoScrapedIssue() {
        //given
        //Scraper scraper = ScraperTestFactory.willThrowComicNotFound("comickey");
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(null, null);
        ScrapeIssueCommand command = new ScrapeIssueCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(result).isEqualTo(CommandResult.ERROR);
        assertThat(context.getScrapedIssue()).isNull();
    }

    @Test
    void testRollback() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        ScrapeIssueCommand command = new ScrapeIssueCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        //when
        boolean result = command.rollback();
        //then
        assertThat(result).isTrue();
        assertThat(Files.exists(newDir.toPath())).isFalse();
    }
}
