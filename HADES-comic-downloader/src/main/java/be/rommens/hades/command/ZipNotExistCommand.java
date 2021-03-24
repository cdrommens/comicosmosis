package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 07/05/2020
 * Time : 14:02
 */
@Slf4j
public class ZipNotExistCommand extends AbstractZipCommand {

    public ZipNotExistCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
    }

    @Override
    public CommandResult body() {
        if (Files.exists(Paths.get(getCbzFilePath()))) {
            return CommandResult.ERROR;
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public boolean rollback() {
        log.info("ZipNotExistCommand rolled back");
        return false;
    }
}
