package brainfuck;

import brainfuck.visitor.JavaAssembler;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class JavaAssemblerTest {

    JavaAssembler javaAssembler = new JavaAssembler();

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

    private void compareLists(List<String> expected, List<String> actual) {
        assertNotNull("Actual list is null", actual);
        assertEquals("Actual list has different size", expected.size(), actual.size());
        assertEquals("Actual list is different", expected, actual);
    }
}
