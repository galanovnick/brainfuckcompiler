package brainfuck.visitor;

import brainfuck.Analyzer;
import brainfuck.command.*;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaAssembler implements Visitor {
    List<String> translatedCommands = new ArrayList<>();

    public List<String> translate(String commands) {

        List<Command> commandsList =
                new Optimizer().optimize(new Analyzer().analyze(commands));

        commandsList.forEach(cmd -> cmd.acceptVisitor(this));

        return translatedCommands;
        /*Configuration cfg = new Configuration();

        Map<String, Object> data = new HashMap<>();

        data.put("classname", "BrainfuckJavaExecutor");

        data.put("commands", translatedCommands);

        Template template = cfg.getTemplate("src/brainfuck/JavaExecutorTemplate.ftl");

        Writer fileWriter = new FileWriter(new File("src/brainfuck/BrainfuckJavaExecutor.java"));

        template.process(data, fileWriter);
        fileWriter.close();*/
    }
    @Override
    public void visit(IncrementPointer command) {
        translatedCommands.add("if (pointer < memory.length - " + command.getCapacity() + ")\n" +
                "pointer+="+command.getCapacity()+";");
    }

    @Override
    public void visit(DecrementPointer command) {
        translatedCommands.add("if (pointer > " + (command.getCapacity() - 1) + ")\n" +
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
        translatedCommands.add("for (int i = 0; i < "+ command.getCapacity() + "; i++)\n" +
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
