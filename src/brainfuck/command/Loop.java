package brainfuck.command;

import brainfuck.visitor.Visitor;

import java.util.List;

public class Loop implements Command {
    private final List<Command> commands;

    public Loop(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Loop loop = (Loop) o;

        return commands != null ? commands.equals(loop.commands) : loop.commands == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (commands != null ? commands.hashCode() : 0);
        return result;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }
}
