package brainfuck;

import brainfuck.visitor.JavaScriptAssembler;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class JavaScriptAssemblerTest {

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outContent);

    final JavaScriptAssembler javaScriptAssembler= new JavaScriptAssembler();


    final File jsTemplateFile = new File("src/main/resources/JavaScriptTemplate.html");

    @Test
    public void testIncrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer < memory.length - 1) pointer+=1;");

        List<String> actual = javaScriptAssembler.translate(">");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer > 0) pointer-=1;");

        List<String> actual = javaScriptAssembler.translate("<");

        compareLists(expected, actual);
    }

    @Test
    public void testIncrementValueCommandTranslation() {
        List<String> expected = Collections.singletonList("memory[pointer]+=1;");

        List<String> actual = javaScriptAssembler.translate("+");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementValueCommandTranslation() {
        List<String> expected = Collections.singletonList("memory[pointer]-=1;");

        List<String> actual = javaScriptAssembler.translate("-");

        compareLists(expected, actual);
    }

    @Test
    public void testPrintCommandTranslation() {
        List<String> expected = Collections.singletonList(
                "for (var i = 0; i < 1; i++)" +
                        " document.getElementById(\"out\").innerText += String.fromCharCode(memory[pointer]);");

        List<String> actual = javaScriptAssembler.translate(".");

        compareLists(expected, actual);
    }

    @Test
    public void testLoopCommandTranslation() {
        List<String> expected = Arrays.asList("while (memory[pointer] != 0) {" +
                System.getProperty("line.separator"),
                "}" + System.getProperty("line.separator"));

        List<String> actual = javaScriptAssembler.translate("[]");

        compareLists(expected, actual);
    }

    @Test
    public void testJavaSimpleProgrammCreation() throws IOException, TemplateException {

        String resultFilePath = "src/main/javascript/brainfuck/results/";
        String resultFileName = "BrainfuckSimpleProgrammExample";
        CodeGenerator.generateCode(jsTemplateFile, resultFilePath,
                resultFileName, javaScriptAssembler.translate(">+<<-"), printStream);

        compareFiles(new File("src/main/resources/benchmarkfiles/JavaScriptProgrammBenchmark.html"),
                new File(resultFilePath + resultFileName + ".html"));

    }

    @Test
    public void testLoopProgramCreation() throws IOException, TemplateException {

        String resultFilePath = "src/main/javascript/brainfuck/results/";
        String resultFileName = "BrainfuckLoopExample";
        CodeGenerator.generateCode(jsTemplateFile, resultFilePath,
                resultFileName, javaScriptAssembler.translate("++[>+<-]"), printStream);

        compareFiles(new File("src/main/resources/benchmarkfiles/JavaScriptLoopBenchmark.html"),
                new File(resultFilePath + resultFileName + ".html"));
    }

    @Test
    public void testHelloWorldAssemble() throws IOException, TemplateException {
        String resultFilePath = "src/main/javascript/brainfuck/results/";
        String resultFileName = "BrainfuckHelloWorld";
        CodeGenerator.generateCode(jsTemplateFile, resultFilePath,
                resultFileName, javaScriptAssembler.translate("++++++++[>++++[>++>" +
                        "+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-." +
                        "<.+++.------.--------.>>+.>++."), null);
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
