package brainfuck;

import brainfuck.command.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by nick on 12.06.16.
 */
public class AnalyzerTest {

    private Analyzer analyzer = new Analyzer();

    @Test
    public void testIncrementPointerCommandAnalyze() {
        List<Command> expected = Collections.singletonList(new IncrementPointer());
        List<Command> actual = analyzer.analyze(">");
        compareLists(expected, actual);
    }

    @Test
    public void testDecrementPointerCommandAnalyze() {
        List<Command> expected = Collections.singletonList(new DecrementPointer());
        List<Command> actual = analyzer.analyze("<");
        compareLists(expected, actual);
    }

    @Test
    public void testIncrementValueCommandAnalyze() {
        List<Command> expected = Collections.singletonList(new IncrementValue());
        List<Command> actual = analyzer.analyze("+");
        compareLists(expected, actual);
    }

    @Test
    public void testDecrementValueCommandAnalyze() {
        List<Command> expected = Collections.singletonList(new DecrementValue());
        List<Command> actual = analyzer.analyze("-");
        compareLists(expected, actual);
    }

    @Test
    public void testPrintCommandAnalyze() {
        List<Command> expected = Collections.singletonList(new PrintValue());
        List<Command> actual = analyzer.analyze(".");
        compareLists(expected, actual);
    }

    @Test
    public void testLoopCommandAnalyze() {
        List<Command> expected = Collections.singletonList(new Loop(
                Arrays.asList(new Loop(Collections.EMPTY_LIST),
                        new Loop(Collections.EMPTY_LIST))
        ));
        List<Command> actual = analyzer.analyze("[[][]]");
        compareLists(expected, actual);
    }

    private void compareLists(List<Command> expected, List<Command> actual) {
        assertNotNull("Actual list is null", actual);
        assertEquals("Actual list size is different", expected.size(), actual.size());
        assertEquals("Actual list is different", expected, actual);
    }
}
