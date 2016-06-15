package brainfuck.visitor;

import brainfuck.Analyzer;
import brainfuck.command.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class Assembler implements Visitor{

    List<String> translatedCommands = new ArrayList<>();

    public List<String> translate(String commands) {

        List<Command> commandsList =
                new Optimizer().optimize(new Analyzer().analyze(commands));

        commandsList.forEach(cmd -> cmd.acceptVisitor(this));

        return translatedCommands;
    }

}