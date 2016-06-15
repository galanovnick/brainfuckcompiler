package brainfuck.visitor;

import brainfuck.command.*;

public class JavaAssembler extends Assembler {

    @Override
    public void visit(IncrementPointer command) {
        translatedCommands.add("if (pointer < memory.length - " + command.getCapacity() + ") " +
                "pointer+="+command.getCapacity()+";");
    }

    @Override
    public void visit(DecrementPointer command) {
        translatedCommands.add("if (pointer > " + (command.getCapacity() - 1) + ") " +
                "pointer-=" + command.getCapacity() + ";");
    }

    @Override
    public void visit(IncrementValue command) {
        translatedCommands.add("memory[pointer]+=" + command.getCapacity() +";");
    }

    @Override
    public void visit(DecrementValue command) {
        translatedCommands.add("memory[pointer]-=" + command.getCapacity() + ";");
    }

    @Override
    public void visit(PrintValue command) {
        translatedCommands.add("for (int i = 0; i < "+ command.getCapacity() + "; i++) " +
                "System.out.print((char) memory[pointer]);");
    }

    @Override
    public void visit(Loop command) {
        translatedCommands.add("while (memory[pointer] != 0) {\n");
        for (Command cmd : command.getCommands()) {
            cmd.acceptVisitor(this);
        }
        translatedCommands.add("}\n");
    }
}
