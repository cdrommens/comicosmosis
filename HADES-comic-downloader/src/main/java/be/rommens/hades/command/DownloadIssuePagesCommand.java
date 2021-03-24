package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.AbstractCommand;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:10
 */
@Slf4j
public class DownloadIssuePagesCommand extends AbstractCommand {

    private final String issueFolder;

    private int numberOfDownloadedPages = 0;

    public DownloadIssuePagesCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.issueFolder = issueAssemblyContext.getIssueFolder();
    }

    @Override
    public CommandResult body() {
        try {
            for (String page : getIssueAssemblyContext().getScrapedIssue().getPages()) {
                //TODO : https://stackoverflow.com/questions/37410249/wiremock-to-serve-images-stored-on-local-disk
                downloadFile(page);
            }
        } catch (IOException e) {
            log.error("   [DownloadIssuePagesCommand] Something went wrong ", e);
            return CommandResult.ERROR;
        }
        if (areAllPagesDownloaded()) {
            log.error("   [DownloadIssuePagesCommand] Not all pages downloaded");
            return CommandResult.ERROR;
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public boolean rollback() {
        try {
            FileUtils.deleteDirectory(new File(issueFolder));
        } catch (IOException e) {
            log.error("DownloadIssuePagesCommand not rolled back", e);
        }
        log.info("DownloadIssuePagesCommand rolled back");
        return true;
    }

    private boolean areAllPagesDownloaded() {
        return numberOfDownloadedPages != getIssueAssemblyContext().getScrapedIssue().getNumberOfPages();
    }

    //TODO : move to scraper (with header and some delay)
    private void downloadFile(String page) throws IOException {
        byte[] contentOfPage = getIssueAssemblyContext().getScraper().downloadPage(page);
        FileUtils.writeByteArrayToFile(getDestinationFile(page), contentOfPage);
        //TODO : check filesize
        numberOfDownloadedPages++;
        log.info("   [DownloadIssuePagesCommand] Downloaded {} / {} pages", numberOfDownloadedPages, getIssueAssemblyContext().getScrapedIssue().getNumberOfPages());
    }

    private File getDestinationFile(String page) {
        Path destinationFileName = Paths.get(page);
        return Paths.get(issueFolder, destinationFileName.getFileName().toString()).toFile();
    }
}
