package be.rommens.hades.core;

import be.rommens.hades.assembler.IssueAssemblyContext;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:51
 */
@RequiredArgsConstructor
public abstract class AbstractCommand implements Command {

    private final IssueAssemblyContext issueAssemblyContext;

    protected abstract CommandResult body();

    protected IssueAssemblyContext getIssueAssemblyContext() {
        return issueAssemblyContext;
    }

    @Override
    public boolean execute() {
        CommandResult result = body();
        return CommandResult.COMPLETED == result;
    }
}
