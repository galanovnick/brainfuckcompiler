package brainfuck.visitor;

import brainfuck.Analyzer;
import brainfuck.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Assembler implements Visitor{

    final static Logger LOG = LoggerFactory.getLogger(Assembler.class);

    protected List<String> translatedCommands = new ArrayList<>();
    protected StringBuilder shift = new StringBuilder("");

    public List<String> translate(String commands) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Translating commands:\""
                    + ((commands.length() > 20) ? commands.substring(0, 20) : commands) + "\".");
        }
        List<Command> commandsList =
                new Optimizer().optimize(new Analyzer().analyze(commands));

        commandsList.forEach(cmd -> cmd.acceptVisitor(this));

        return translatedCommands;
    }

}
