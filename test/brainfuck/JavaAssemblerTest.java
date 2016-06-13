package brainfuck;

import brainfuck.visitor.JavaAssembler;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class JavaAssemblerTest {

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outContent);

    final JavaAssembler javaAssembler = new JavaAssembler(printStream);

    @Test
    public void testIncrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer < memory.length - 1)\npointer+=1;");

        List<String> actual = javaAssembler.translate(">");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer > 0)\npointer-=1;");

        List<String> actual = javaAssembler.translate("<");

        compareLists(expected, actual);
    }

    @Test
    public void testIncrementValueCommandTranslation() {
        List<String> expected = Collections.singletonList("memory[pointer]+=1;");

        List<String> actual = javaAssembler.translate("+");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementValueCommandTranslation() {
        List<String> expected = Collections.singletonList("memory[pointer]-=1;");

        List<String> actual = javaAssembler.translate("-");

        compareLists(expected, actual);
    }

    @Test
    public void testPrintCommandTranslation() {
        List<String> expected = Collections.singletonList(
                "for (int i = 0; i < 1; i++)\n" +
                "System.out.print((char) memory[pointer]);");

        List<String> actual = javaAssembler.translate(".");

        compareLists(expected, actual);
    }

    @Test
    public void testLoopCommandTranslation() {
        List<String> expected = Arrays.asList("while (memory[pointer] != 0) {\n","}\n");

        List<String> actual = javaAssembler.translate("[]");

        compareLists(expected, actual);
    }

    @Test
    public void testJavaSimpleProgrammCreation() throws IOException, TemplateException {
        String expected = "package brainfuck;\n" +
                "public class BrainfuckJavaExecutor {\n" +
                "private byte[] memory = new byte[1000];\n" +
                "private int pointer = 0;\n" +
                "public void execute() {\n" +
                "if (pointer < memory.length - 1)\n" +
                "pointer+=1;\n" +
                "memory[pointer]+=1;\n" +
                "if (pointer > 1)\n" +
                "pointer-=2;\n" +
                "memory[pointer]-=1;\n" +
                "}\n" +
                "public static void main(String[] args) {\n" +
                "new BrainfuckJavaExecutor().execute();\n" +
                "}\n" +
                "}";

        javaAssembler.createJavaClass(javaAssembler.translate(">+<<-"));
        assertEquals(expected, outContent.toString());

    }

    @Test
    public void testLoopProgramCreation() throws IOException, TemplateException {

        String expected = "package brainfuck;\n" +
                "public class BrainfuckJavaExecutor {\n" +
                "private byte[] memory = new byte[1000];\n" +
                "private int pointer = 0;\n" +
                "public void execute() {\n" +
                "memory[pointer]+=2;\n" +
                "while (memory[pointer] != 0) {\n" +
                "\n" +
                "if (pointer < memory.length - 1)\n" +
                "pointer+=1;\n" +
                "memory[pointer]+=1;\n" +
                "if (pointer > 0)\n" +
                "pointer-=1;\n" +
                "memory[pointer]-=1;\n" +
                "}\n" +
                "\n" +
                "}\n" +
                "public static void main(String[] args) {\n" +
                "new BrainfuckJavaExecutor().execute();\n" +
                "}\n" +
                "}";

        javaAssembler.createJavaClass(javaAssembler.translate("++[>+<-]"));
        assertEquals(expected, outContent.toString());
    }

    private void compareLists(List<String> expected, List<String> actual) {
        assertNotNull("Actual list is null", actual);
        assertEquals("Actual list has different size", expected.size(), actual.size());
        assertEquals("Actual list is different", expected, actual);
    }
}
