package brainfuck.visitor;

import brainfuck.Analyzer;
import brainfuck.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.List;

public class JavaExecutor implements Visitor {
    static final Logger LOG = LoggerFactory.getLogger(JavaExecutor.class);

    byte[] memory = new byte[1000];
    int pointer = 0;

    public JavaExecutor(PrintStream printStream) {
        this.printStream = printStream;
    }

    final PrintStream printStream;

    public void execute(String commands) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Executing commands:\""
                    + ((commands.length() > 20) ? commands.substring(0, 20) : commands) + "\".");
        }
        memory = new byte[1000];
        pointer = 0;
        List<Command> commandsList =
                new Optimizer().optimize(new Analyzer().analyze(commands));
        commandsList.forEach(command -> command.acceptVisitor(this));
    }

    @Override
    public void visit(IncrementPointer command) {
        if (pointer < memory.length - command.getCapacity())
            pointer+=command.getCapacity();
    }

    @Override
    public void visit(DecrementPointer command) {
        if (pointer > command.getCapacity() - 1)
            pointer-=command.getCapacity();
    }

    @Override
    public void visit(IncrementValue command) {
        memory[pointer]+=command.getCapacity();
    }

    @Override
    public void visit(DecrementValue command) {
        memory[pointer]-=command.getCapacity();
    }

    @Override
    public void visit(PrintValue command) {
        for (int i = 0; i < command.getCapacity(); i++)
            printStream.print((char) memory[pointer]);
    }

    @Override
    public void visit(Loop command) {
        while (memory[pointer] > 0) {
            command.getCommands().forEach(cmd -> cmd.acceptVisitor(this));
        }
    }
}
