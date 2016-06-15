package brainfuck.command;

abstract public class ScalableCommand implements Command{
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    protected int capacity = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScalableCommand that = (ScalableCommand) o;

        return capacity == that.capacity;

    }

    @Override
    public int hashCode() {
        return capacity;
    }
}