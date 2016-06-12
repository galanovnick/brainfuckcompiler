package brainfuck;

import brainfuck.visitor.JavaExecutor;
import brainfuck.visitor.Visitor;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nick on 13.06.16.
 */
public class JavaExecutorTest {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outContent);
    final JavaExecutor javaExecutor = new JavaExecutor(printStream);

    @Test
    public void testJavaExecutor() {
        javaExecutor.execute("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<" +
                "]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.");
        assertEquals("Hello World!\n", outContent.toString());
    }
}
