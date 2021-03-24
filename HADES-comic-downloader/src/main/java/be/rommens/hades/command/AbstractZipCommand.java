package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AbstractCommand;

import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 16:20
 */
public abstract class AbstractZipCommand extends AbstractCommand {

    private static final String EXTENSION = "cbz";
    private final String cbzFilePath;

    public AbstractZipCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.cbzFilePath = createCbzFilePath(issueAssemblyContext.getBaseUrl(), issueAssemblyContext.getDownloadIssueMessage());
    }

    protected String getCbzFilePath() {
        return cbzFilePath;
    }

    private String createCbzFilePath(String baseUrl, DownloadIssueMessage downloadIssueMessage) {
        return Paths.get(baseUrl, downloadIssueMessage.getComicFolder(), downloadIssueMessage.getIssueFolder() + "." + EXTENSION).toString();
    }
}
