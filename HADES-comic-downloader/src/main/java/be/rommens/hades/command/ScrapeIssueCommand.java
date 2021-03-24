package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AbstractCommand;
import be.rommens.hades.core.CommandResult;
import be.rommens.scraper.core.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 16:05
 */
@Slf4j
public class ScrapeIssueCommand extends AbstractCommand {

    private final Scraper scraper;
    private final DownloadIssueMessage downloadIssueMessage;

    public ScrapeIssueCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.scraper = issueAssemblyContext.getScraper();
        this.downloadIssueMessage = issueAssemblyContext.getDownloadIssueMessage();
    }

    @Override
    public CommandResult body() {
        try {
            getIssueAssemblyContext().setScrapedIssue(scraper.scrapeIssue(downloadIssueMessage.getComicKey(), downloadIssueMessage.getIssueNumber()));
            log.info("   [GetPages] Pages fetched for {} issue {}", downloadIssueMessage.getComicKey(), downloadIssueMessage.getIssueNumber());
            return CommandResult.COMPLETED;
        } catch (Exception e) {
            log.info("   [GetPages] something went wrong", e);
            return CommandResult.ERROR;
        }
    }

    @Override
    public boolean rollback() {
        try {
            FileUtils.deleteDirectory(new File(getIssueAssemblyContext().getIssueFolder()));
        } catch (IOException e) {
            log.error("ScrapeIssueCommand not rolled back", e);
        }
        log.info("ScrapeIssueCommand rolled back");
        return true;
    }
}
