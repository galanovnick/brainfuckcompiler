package brainfuck.command;

abstract public class ScalableCommand implements Command{
    protected int capacity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScalableCommand that = (ScalableCommand) o;

        return capacity == that.capacity;

    }

    @Override
    public int hashCode() {
        return capacity;
    }
}