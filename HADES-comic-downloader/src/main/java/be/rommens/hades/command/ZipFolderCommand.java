package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:25
 */
@Slf4j
public class ZipFolderCommand extends AbstractZipCommand {

    private final File issueFolder;

    public ZipFolderCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.issueFolder = new File(issueAssemblyContext.getIssueFolder());
    }

    @Override
    public CommandResult body() {
        if (ArrayUtils.isEmpty(issueFolder.listFiles())) {
            log.error(issueFolder + " is empty! No files to zip");
            return CommandResult.ERROR;
        }
        try {
            ZipFile zipFile = new ZipFile(getCbzFilePath());
            ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
            zipFile.setRunInThread(true);
            zipFile.addFolder(issueFolder);
            while (!progressMonitor.getState().equals(ProgressMonitor.State.READY)) {
                log.info("   [CreateZip] Percentage done: " + progressMonitor.getPercentDone());
                log.info("   [CreateZip] Current file: " + progressMonitor.getFileName());
                log.info("   [CreateZip] Current task: " + progressMonitor.getCurrentTask());

                Thread.sleep(100);
            }
            if (zipFile.isValidZipFile()) {
                log.info("   [CreateZip] {} is created", getCbzFilePath());
                return CommandResult.COMPLETED;
            } else {
                return CommandResult.ERROR;
            }
        } catch (ZipException e) {
            log.error("   [CreateZip] Something went wrong ", e);
            return CommandResult.ERROR;
        } catch (InterruptedException e) {
            log.error("   [CreateZip] Interrupted! ", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
            return CommandResult.ERROR;
        }
    }

    @Override
    public boolean rollback() {
        try {
            FileUtils.deleteDirectory(issueFolder);
        } catch (IOException e) {
            log.error("ZipFolderCommand not rolled back", e);
        }
        log.info("ZipFolderCommand rolled back");
        return true;
    }
}
