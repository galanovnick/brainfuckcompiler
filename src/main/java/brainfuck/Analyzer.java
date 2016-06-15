package brainfuck;

import brainfuck.command.*;

import java.util.*;

public class Analyzer {

    public List<Command> analyze(String commands) {
        final Deque<List<Command>> commandsStack = new ArrayDeque<>();
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
                    if (commandsStack.size() == 1)
                        throw new IllegalStateException("[ expected");
                    final List<Command> loopCommands = commandsStack.pop();
                    commandsStack.peek().add(new Loop(loopCommands));
                    break;
            }
        }
        if (commandsStack.size() > 1) {
            throw new IllegalStateException("] expected");
        }
        return commandsStack.peek();
    }
}
