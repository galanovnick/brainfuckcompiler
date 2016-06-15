package brainfuck.visitor;

import brainfuck.command.*;
import groovyx.gpars.extra166y.Ops;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Optimizer implements Visitor {

    private List<Command> optimizedCommands = new ArrayList<>();

    public List<Command> optimize(List<Command> commands) {
        for (Command command : commands) {
            command.acceptVisitor(this);
        }

        return optimizedCommands;
    }

    private void visitValueAwareCommand(ScalableCommand command) {
        if (!optimizedCommands.isEmpty()) {

            final Command lastCommand = optimizedCommands.get(
                    optimizedCommands.size() - 1);

            if (lastCommand.getClass().equals(command.getClass())) {

                ScalableCommand valueAwareCommand =
                        (ScalableCommand) lastCommand;

                valueAwareCommand.setCapacity(
                        valueAwareCommand.getCapacity() + 1);
                return;
            }
        }

        optimizedCommands.add(command);
    }

    @Override
    public void visit(IncrementPointer command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(DecrementPointer command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(PrintValue command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(IncrementValue command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(DecrementValue command) {
        optimizedCommands.add(command);
    }

    @Override
    public void visit(Loop command) {
        optimizedCommands.add(command);

        final List<Command> justToKeep = optimizedCommands;

        optimizedCommands = new ArrayList<>();

        for (Command innerCommand: command.getCommands()) {
            innerCommand.acceptVisitor(this);
        }

        command.setCommands(optimizedCommands);

        optimizedCommands = justToKeep;

    }
}
