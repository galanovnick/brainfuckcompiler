package brainfuck.command;

import brainfuck.visitor.Visitor;

public class IncrementPointer extends ScalableCommand{
    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }
}
