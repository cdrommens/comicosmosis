package be.rommens.hades.core;

import be.rommens.hades.assembler.IssueAssemblyChain;
import be.rommens.hades.assembler.IssueAssemblyContext;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:42
 */
public interface IssueAssemblyDsl {
    StartChain withContext(IssueAssemblyContext context);
    Precondition withPrecondition(Class<? extends Command> commandClass);
    InterChain startChain(Class<? extends Command> commandClass);


    interface Context extends StartChain {
        StartChain withContext(IssueAssemblyContext context);
    }

    interface Precondition extends PreconditionOrElse {
        PreconditionOrElse onFailed(Class<? extends Command> commandClass);
    }

    interface PreconditionOrElse {
        Context orElse();
    }

    interface StartChain extends End {
        InterChain startChain(Class<? extends Command> commandClass);
    }

    interface InterChain extends End{
        InterChain next(Class<? extends Command> commandClass);
        End onError(Class<? extends Command> commandClass);
    }

    interface End {
        IssueAssemblyChain build();
    }
}
