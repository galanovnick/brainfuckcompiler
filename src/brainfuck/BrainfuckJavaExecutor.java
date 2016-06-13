package brainfuck;

public class BrainfuckJavaExecutor {
    private byte[] memory = new byte[1000];
    private int pointer = 0;

    public void execute() {
          memory[pointer]+=1;
    }

    public static void main(String[] args) {
        new BrainfuckJavaExecutor().execute();
    }
}