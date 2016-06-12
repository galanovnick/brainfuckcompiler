package brainfuck.command;

import brainfuck.visitor.Visitor;

public class DecrementValue extends ScalableCommand{

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }
}
