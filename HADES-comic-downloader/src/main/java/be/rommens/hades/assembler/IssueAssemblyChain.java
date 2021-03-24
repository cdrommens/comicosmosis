package be.rommens.hades.assembler;

import be.rommens.hades.core.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:59
 */
@Slf4j
public class IssueAssemblyChain {

    private final IssueAssemblyContext context;
    private final Optional<Command> precondition;
    private final Optional<Command> preconditionOnFailed;
    private final List<Optional<Command>> commandStack = new LinkedList<>();
    private final Optional<Command> onError;

    public IssueAssemblyChain(IssueAssemblyContext context,
                              Class<? extends Command> precondition,
                              Class<? extends Command> preconditionOnFailed,
                              List<Class<? extends Command>> queue,
                              Class<? extends Command> onError) {
        this.context = context;
        this.precondition = makeInstance(precondition);
        this.preconditionOnFailed = makeInstance(preconditionOnFailed);
        queue.forEach(clazz -> this.commandStack.add(makeInstance(clazz)));
        this.onError = makeInstance(onError);
    }

    public Boolean execute() {
        boolean resultPrecondition = executePrecondition();
        if (!resultPrecondition) {
            return true;
        }
        for (Optional<Command> command : commandStack) {
            if (command.isPresent()) {
                boolean resultOfCommand = command.get().execute();
                if (!resultOfCommand) {
                    int index = commandStack.indexOf(command);
                    for (int i = index; i >= 0; i--) {
                        command.get().rollback();
                        log.info("command {} rolled back", command.get().getClass().getSimpleName());
                    }
                    onError.ifPresent(Command::execute);
                    return true;
                }
            }
        }
        return true;
    }

    private boolean executePrecondition() {
        if (precondition.isPresent()) {
            boolean result = precondition.get().execute();
            if (result) {
                return true;
            } else {
                preconditionOnFailed.ifPresent(Command::execute);
                return false;
            }
        }
        return true;
    }

    private Optional<Command> makeInstance(Class<? extends Command> commandClass) {
        if (commandClass == null) {
            return Optional.empty();
        }
        if (context == null) {
            Constructor<? extends Command> constructor = ClassUtils.getConstructorIfAvailable(commandClass);
            if (constructor == null) {
                throw new IllegalStateException("No constructor found for object " + commandClass.getCanonicalName());
            }
            try {
                return Optional.of(constructor.newInstance());
            } catch (Exception e) {
                throw new IllegalStateException("Can't create object " + commandClass.getCanonicalName(), e);
            }
        }
        Constructor<? extends Command> constructor = ClassUtils.getConstructorIfAvailable(commandClass, IssueAssemblyContext.class);
        if (constructor == null) {
            throw new IllegalStateException("No constructor found for object " + commandClass.getCanonicalName());
        }
        try {
            return Optional.of(constructor.newInstance(context));
        } catch (Exception e) {
            throw new IllegalStateException("Can't create object with context constructor " + commandClass.getCanonicalName(), e);
        }
    }
}
