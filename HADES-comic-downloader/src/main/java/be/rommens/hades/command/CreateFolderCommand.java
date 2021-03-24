package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AbstractCommand;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:56
 */
@Slf4j
public class CreateFolderCommand extends AbstractCommand {

    private final File issueFolder;

    public CreateFolderCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        issueAssemblyContext.setIssueFolder(createIssueFolderPath(issueAssemblyContext.getBaseUrl(), issueAssemblyContext.getDownloadIssueMessage()));
        this.issueFolder = new File(issueAssemblyContext.getIssueFolder());
    }

    @Override
    public CommandResult body() {
        try {
            // if the parent folder doesn't exist, it will create it
            FileUtils.forceMkdir(issueFolder);
            log.info("   [CreateFolderTask] Folder {} created", issueFolder);
            return CommandResult.COMPLETED;
        } catch (Exception e) {
            log.error("   [CreateFolderTask] Folder {} not created", issueFolder);
            return CommandResult.ERROR;
        }
    }

    @Override
    public boolean rollback() {
        log.info("CreateFolderCommand rolled back");
        return true;
    }

    private String createIssueFolderPath(String baseUrl, DownloadIssueMessage downloadIssueMessage) {
        return Paths.get(baseUrl, downloadIssueMessage.getComicFolder(), downloadIssueMessage.getIssueFolder()).toString();
    }
}
