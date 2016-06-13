package brainfuck.visitor;

import brainfuck.command.*;

public interface Visitor {

    void visit(IncrementPointer command);
    void visit(DecrementPointer command);
    void visit(IncrementValue command);
    void visit(DecrementValue command);
    void visit(PrintValue command);
    void visit(Loop command);
}
