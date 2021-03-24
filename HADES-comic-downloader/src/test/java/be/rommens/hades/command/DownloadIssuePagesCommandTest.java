package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.scraper.api.models.ScrapedIssue;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.autoconfigure.AutoConfigureScraperMock;
import be.rommens.scraper.builders.ScrapedIssueBuilder;
import be.rommens.scraper.core.Scraper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static be.rommens.hades.model.IssueAssemblyContextTestObjectFactory.createTestContext;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 27/04/2020
 * Time : 08:42
 */
@AutoConfigureScraperMock(value = "/datasets/scrape-issue-command-test-input.yml")
class DownloadIssuePagesCommandTest {

    @TempDir
    Path tempDir;

    @Autowired
    private ScraperFactory scraperFactory;

    private Scraper scraper;

    @BeforeEach
    void setUp() {
        this.scraper = scraperFactory.createScraper(null);
    }

    @Test
    void whenIssueExists_thenReturnCompletedAndDownloadAllPages() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);

        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("page1.txt")
            .addPage("page2.txt")
            .build();

        IssueAssemblyContext context = createTestContext(tempDir.toString(), scraper);
        context.setScrapedIssue(scrapedIssue);
        DownloadIssuePagesCommand command = new DownloadIssuePagesCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        assertThat(result).isEqualTo(CommandResult.COMPLETED);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1", "page1.txt"))).isTrue();
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1", "page2.txt"))).isTrue();
    }

    @Test
    void whenIssueNotExists_thenReturnErrorAndFilesNotDownloaded() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);

        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("page1.txt")
            .addPage("page2.txt")
            .addPage("unknownpage.txt")
            .build();

        IssueAssemblyContext context = createTestContext(tempDir.toString(), scraper);
        context.setScrapedIssue(scrapedIssue);
        DownloadIssuePagesCommand command = new DownloadIssuePagesCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        assertThat(result).isEqualTo(CommandResult.ERROR);
    }

    @Test
    void testRollback() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        DownloadIssuePagesCommand command = new DownloadIssuePagesCommand(createTestContext(tempDir.toString(), scraper));
        //when
        boolean result = command.rollback();
        //then
        assertThat(result).isTrue();
        assertThat(Files.exists(newDir.toPath())).isFalse();
    }
}
