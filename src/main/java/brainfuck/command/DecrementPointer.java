package brainfuck.command;

import brainfuck.visitor.Visitor;

public class DecrementPointer extends ScalableCommand{

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }
}
