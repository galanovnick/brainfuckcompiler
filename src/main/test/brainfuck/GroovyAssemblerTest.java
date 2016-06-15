package brainfuck;

import brainfuck.visitor.GroovyAssembler;
import freemarker.template.TemplateException;
import groovy.lang.GroovyShell;
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

public class GroovyAssemblerTest {

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outContent);

    final GroovyAssembler groovyAssembler = new GroovyAssembler();


    final File groovyTemplateFile = new File("src/main/resources/GroovyTemplate.groovy");

    @Test
    public void testIncrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer < memory.length - 1) pointer+=1");

        List<String> actual = groovyAssembler.translate(">");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementPointerCommandTranslation() {
        List<String> expected = Collections.singletonList("if (pointer > 0) pointer-=1");

        List<String> actual = groovyAssembler.translate("<");

        compareLists(expected, actual);
    }

    @Test
    public void testIncrementValueCommandTranslation() {
        List<String> expected = Collections.singletonList("memory[pointer]+=1");

        List<String> actual = groovyAssembler.translate("+");

        compareLists(expected, actual);
    }

    @Test
    public void testDecrementValueCommandTranslation() {
        List<String> expected = Collections.singletonList("memory[pointer]-=1");

        List<String> actual = groovyAssembler.translate("-");

        compareLists(expected, actual);
    }

    @Test
    public void testPrintCommandTranslation() {
        List<String> expected = Collections.singletonList(
                "1.times { print ((char) memory[pointer]) }");

        List<String> actual = groovyAssembler.translate(".");

        compareLists(expected, actual);
    }

    @Test
    public void testLoopCommandTranslation() {
        List<String> expected = Arrays.asList("while (memory[pointer] != 0) {\n","}\n");

        List<String> actual = groovyAssembler.translate("[]");

        compareLists(expected, actual);
    }

    @Test
    public void testJavaSimpleProgrammCreation() throws IOException, TemplateException {

        String resultFilePath = "src/main/groovy/brainfuck/results/";
        String resultFileName = "BrainfuckSimpleProgrammExample";
        CodeGenerator.generateCode(groovyTemplateFile, resultFilePath,
                resultFileName, groovyAssembler.translate(">+<<-"), printStream);

        compareFiles(new File("src/main/resources/benchmarkfiles/GroovyProgrammBenchmark.groovy"),
                new File(resultFilePath + resultFileName + ".groovy"));

    }

    @Test
    public void testLoopProgramCreation() throws IOException, TemplateException {

        String resultFilePath = "src/main/groovy/brainfuck/results/";
        String resultFileName = "BrainfuckLoopExample";
        CodeGenerator.generateCode(groovyTemplateFile, resultFilePath,
                resultFileName, groovyAssembler.translate("++[>+<-]"), printStream);

        compareFiles(new File("src/main/resources/benchmarkfiles/GroovyLoopBenchmark.groovy"),
                new File(resultFilePath + resultFileName + ".groovy"));
    }

    @Test
    public void testHelloWorldAssemble() throws IOException, TemplateException {
        String resultFilePath = "src/main/groovy/brainfuck/results/";
        String resultFileName = "BrainfuckHelloWorld";
        CodeGenerator.generateCode(groovyTemplateFile, resultFilePath,
                resultFileName, groovyAssembler.translate("++++++++[>++++[>++>" +
                        "+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-." +
                        "<.+++.------.--------.>>+.>++."), printStream);

        assertEquals("Hello world programm example is incorrect.", "Hello World!", new GroovyShell()
                .evaluate(new File("src/main/groovy/brainfuck" +
                        "/results/BrainfuckHelloWorld.groovy")));
        System.out.print("");
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
