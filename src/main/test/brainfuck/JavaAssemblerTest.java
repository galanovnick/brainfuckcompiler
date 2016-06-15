package brainfuck;

import brainfuck.visitor.JavaAssembler;
import freemarker.template.TemplateException;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.CompositeFileComparator;
import org.apache.commons.io.comparator.SizeFileComparator;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class JavaAssemblerTest {

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outContent);

    final JavaAssembler javaAssembler = new JavaAssembler();


    final File javaTempalteFile = new File("src/main/resources/JavaTemplate.java");

    @Test
    public void testIncrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer < memory.length - 1) pointer+=1;");

        List<String> actual = javaAssembler.translate(">");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer > 0) pointer-=1;");

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
                "for (int i = 0; i < 1; i++) " +
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

        String resultFilePath = "src/main/java/brainfuck/result/";
        String resultFileName = "BrainfuckSimpleProgrammExample";
        CodeGenerator.generateCode(javaTempalteFile, resultFilePath,
                resultFileName, javaAssembler.translate(">+<<-"), printStream);

        compareFiles(new File("src/main/resources/benchmarkfiles/JavaProgrammBenchmark.java"),
                new File(resultFilePath + resultFileName + ".java"));

    }

    @Test
    public void testLoopProgramCreation() throws IOException, TemplateException {

        String resultFilePath = "src/main/java/brainfuck/result/";
        String resultFileName = "BrainfuckLoopExample";
        CodeGenerator.generateCode(javaTempalteFile, resultFilePath,
                resultFileName, javaAssembler.translate("++[>+<-]"), printStream);

        compareFiles(new File("src/main/resources/benchmarkfiles/JavaLoopBenchmark.java"),
                new File(resultFilePath + resultFileName + ".java"));
    }

    private void compareLists(List<String> expected, List<String> actual) {
        assertNotNull("Actual list is null", actual);
        assertEquals("Actual list has different size", expected.size(), actual.size());
        assertEquals("Actual list is different", expected, actual);
    }

    private void compareFiles(File expected, File actual) throws IOException {
        assertEquals("Actual file is different.", true,
                FileUtils.contentEquals(expected, actual));
    }
}
