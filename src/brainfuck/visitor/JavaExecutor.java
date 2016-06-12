package brainfuck.visitor;

import brainfuck.Analyzer;
import brainfuck.command.*;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by nick on 12.06.16.
 */
public class JavaExecutor implements Visitor{
    byte[] memory = new byte[1000];
    int pointer = 0;

    public JavaExecutor(PrintStream printStream) {
        this.printStream = printStream;
    }

    final PrintStream printStream;

    public void execute(String commands) {
        memory = new byte[1000];
        pointer = 0;
        List<Command> commandsList = new Analyzer().analyze(commands);
        commandsList.forEach(command -> command.acceptVisitor(this));
    }

    @Override
    public void visit(IncrementPointer command) {
        if (pointer < memory.length - 1)
            pointer++;
    }

    @Override
    public void visit(DecrementPointer command) {
        if (pointer > 0)
            pointer--;
    }

    @Override
    public void visit(IncrementValue command) {
        memory[pointer]++;
    }

    @Override
    public void visit(DecrementValue command) {
        memory[pointer]--;
    }

    @Override
    public void visit(PrintValue command) {
        printStream.print((char) memory[pointer]);
    }

    @Override
    public void visit(Loop command) {
        while (memory[pointer] > 0) {
            command.getCommands().forEach(cmd -> cmd.acceptVisitor(this));
        }
    }
}
