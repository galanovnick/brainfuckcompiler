package brainfuck.visitor;

import brainfuck.command.*;

public class JavaAssembler extends Assembler {

    @Override
    public void visit(IncrementPointer command) {
        translatedCommands.add(shift + "if (pointer < memory.length - " + command.getCapacity() + ") " +
                "pointer+="+command.getCapacity()+";");
    }

    @Override
    public void visit(DecrementPointer command) {
        translatedCommands.add(shift + "if (pointer > " + (command.getCapacity() - 1) + ") " +
                "pointer-=" + command.getCapacity() + ";");
    }

    @Override
    public void visit(IncrementValue command) {
        translatedCommands.add(shift + "memory[pointer]+=" + command.getCapacity() +";");
    }

    @Override
    public void visit(DecrementValue command) {
        translatedCommands.add(shift + "memory[pointer]-=" + command.getCapacity() + ";");
    }

    @Override
    public void visit(PrintValue command) {
        translatedCommands.add(shift + "for (int i = 0; i < "+ command.getCapacity() + "; i++) " +
                "System.out.print((char) memory[pointer]);");
    }

    @Override
    public void visit(Loop command) {
        translatedCommands.add(shift + "while (memory[pointer] != 0) {"
                + System.getProperty("line.separator"));
        shift.append("    ");
        for (Command cmd : command.getCommands()) {
            cmd.acceptVisitor(this);
        }
        shift.delete(shift.length() - 4, shift.length());
        translatedCommands.add(shift + "}" + System.getProperty("line.separator"));
    }
}
