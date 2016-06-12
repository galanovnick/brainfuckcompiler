package brainfuck.visitor;

import brainfuck.command.*;

import java.util.*;

/**
 * Created by nick on 12.06.16.
 */
public class Optimizer implements Visitor{
    List<Command> optimizedCommands = new ArrayList<>();

    public List<Command> optimizeCommands(List<Command> commands) {
        if (commands == null || commands.size() == 0)
            throw new IllegalArgumentException("Input commands list cannot be empty.");
        commands.forEach(command -> command.acceptVisitor(this));

        return optimizedCommands;
    }
    @Override
    public void visit(IncrementPointer command) {
        optimizeScalableCommand(command);
    }

    @Override
    public void visit(DecrementPointer command) {
        optimizeScalableCommand(command);
    }

    @Override
    public void visit(IncrementValue command) {
        optimizeScalableCommand(command);
    }

    @Override
    public void visit(DecrementValue command) {
        optimizeScalableCommand(command);
    }

    @Override
    public void visit(PrintValue command) {
        optimizeScalableCommand(command);
    }

    @Override
    public void visit(Loop command) {

    }

    private void optimizeScalableCommand(Command command) {

    }
}
