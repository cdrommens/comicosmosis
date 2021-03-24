package be.rommens.hades.assembler;

import be.rommens.hades.core.Command;
import be.rommens.hades.core.IssueAssemblyDsl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:45
 */
@Slf4j
public final class IssueAssemblyChainBuilder
    implements IssueAssemblyDsl, IssueAssemblyDsl.Context, IssueAssemblyDsl.Precondition, IssueAssemblyDsl.PreconditionOrElse, IssueAssemblyDsl.StartChain, IssueAssemblyDsl.InterChain, IssueAssemblyDsl.End {

    private IssueAssemblyContext context;
    private Class<? extends Command> precondition;
    private Class<? extends Command> preconditionOnFailed;
    private final List<Class<? extends Command>> commandQueue = new ArrayList<>();
    private Class<? extends Command> onError;

    private IssueAssemblyChainBuilder() {
        // force to use builder
    }

    public static IssueAssemblyDsl createInstance() {
        return new IssueAssemblyChainBuilder();
    }

    @Override
    public StartChain withContext(IssueAssemblyContext context) {
        this.context = context;
        return this;
    }

    @Override
    public Precondition withPrecondition(Class<? extends Command> commandClass) {
        this.precondition = commandClass;
        return this;
    }

    @Override
    public PreconditionOrElse onFailed(Class<? extends Command> commandClass) {
        this.preconditionOnFailed = commandClass;
        return this;
    }

    @Override
    public Context orElse() {
        return this;
    }

    @Override
    public InterChain startChain(Class<? extends Command> commandClass) {
        commandQueue.add(commandClass);
        return this;
    }

    @Override
    public InterChain next(Class<? extends Command> commandClass) {
        commandQueue.add(commandClass);
        return this;
    }

    @Override
    public End onError(Class<? extends Command> commandClass) {
        this.onError = commandClass;
        return this;
    }

    @Override
    public IssueAssemblyChain build() {
        return new IssueAssemblyChain(context, precondition, preconditionOnFailed, commandQueue, onError);
    }
}
