package brainfuck;

import brainfuck.command.*;

import java.util.*;

public class Analyzer {

    public List<Command> analyze(String commands) {
        Deque<List<Command>> commandsStack = new ArrayDeque<>();
        commandsStack.push(new ArrayList<>());
        for (char symbol : commands.toCharArray()) {
            switch (symbol) {
                default:
                    throw new IllegalStateException("Cannot resolve symbol '" + symbol + "'");
                case '+':
                    commandsStack.peek().add(new IncrementValue());
                    break;
                case '-':
                    commandsStack.peek().add(new DecrementValue());
                    break;
                case '>':
                    commandsStack.peek().add(new IncrementPointer());
                    break;
                case '<':
                    commandsStack.peek().add(new DecrementPointer());
                    break;
                case '.':
                    commandsStack.peek().add(new PrintValue());
                    break;
                case '[':
                    commandsStack.push(new ArrayList<>());
                    break;
                case ']':
                    commandsStack.peek().add(new Loop(commandsStack.pop()));
                    break;
            }
        }
        return commandsStack.peek();
    }
}
