package brainfuck.result;

public class BrainfuckLoopExample {

    private byte[] memory = new byte[1000];
    private int pointer = 0;

    public void execute() {
        memory[pointer]+=2;
        while (memory[pointer] != 0) {

        if (pointer < memory.length - 1) pointer+=1;
        memory[pointer]+=1;
        if (pointer > 0) pointer-=1;
        memory[pointer]-=1;
        }

    }

    public static void main(String[] args) {

        new BrainfuckLoopExample().execute();

    }
}