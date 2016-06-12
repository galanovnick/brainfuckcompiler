package brainfuck.command;

abstract public class BrainfuckCommand implements Command{
    protected int capacity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrainfuckCommand that = (BrainfuckCommand) o;

        return capacity == that.capacity;

    }

    @Override
    public int hashCode() {
        return capacity;
    }
}
