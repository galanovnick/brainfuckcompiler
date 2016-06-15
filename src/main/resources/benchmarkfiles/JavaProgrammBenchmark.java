package brainfuck.result;

public class BrainfuckSimpleProgrammExample {

    private byte[] memory = new byte[1000];
    private int pointer = 0;

    public void execute() {
        if (pointer < memory.length - 1) pointer+=1;
        memory[pointer]+=1;
        if (pointer > 1) pointer-=2;
        memory[pointer]-=1;
    }

    public static void main(String[] args) {

        new BrainfuckSimpleProgrammExample().execute();

    }
}