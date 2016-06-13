package brainfuck;
public class ${classname} {
private byte[] memory = new byte[1000];
private int pointer = 0;
public void execute() {
<#list commands as command>
${command}
</#list>
}
public static void main(String[] args) {
new BrainfuckJavaExecutor().execute();
}
}