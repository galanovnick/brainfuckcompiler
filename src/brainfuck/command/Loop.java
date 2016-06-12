package brainfuck.command;

import java.util.List;

public class Loop extends BrainfuckCommand {
    private final List<Command> commands;

    public Loop(List<Command> commands) {
        this.commands = commands;
    }
}
