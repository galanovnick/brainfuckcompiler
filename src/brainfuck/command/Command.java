package brainfuck.command;

import brainfuck.visitor.Visitor;

public interface Command {

    void acceptVisitor(Visitor visitor);
}
