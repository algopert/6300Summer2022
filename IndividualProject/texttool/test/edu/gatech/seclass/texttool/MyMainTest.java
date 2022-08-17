package edu.gatech.seclass.txted;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyMainTest {

    // Place all  of your tests in this class, optionally using MainTest.java as an example.

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final Charset charset = StandardCharsets.UTF_8;
    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file = createTmpFile();
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        fileWriter.write(input);
        fileWriter.close();
        return file;
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = Files.readString(Paths.get(filename), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private String errMsg = "Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE" + System.lineSeparator();

    /*
     *   TEST CASES
     */

    // Frame #: <test case 1 in file catpart.txt.tsl>
    @Test
    public void txtedTest1() throws Exception {
        String input = "abcdefgh2468foobar" + System.lineSeparator();
        String expected = "abcdefgh2468foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 2 in file catpart.txt.tsl>
    @Test
    public void txtedTest2() throws Exception {
        String input = "abcdefgh2468foobar" + System.lineSeparator();
        String expected = "abcdefgh2468foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 3 in file catpart.txt.tsl>
    @Test
    public void txtedTest3() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-w", inputFile.getPath()};
        Main.main(args);
        //    assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        //  assertEquals(errMsg, errStream.toString().trim());
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }

    // Frame #: <test case 4 in file catpart.txt.tsl>
    @Test
    public void txtedTest4() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "2", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }

    // Frame #: <test case 5 in file catpart.txt.tsl>
    @Test
    public void txtedTest5() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "-1", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }


    // Frame #: <test case 6 in file catpart.txt.tsl>
    @Test
    public void txtedTest6() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }

    // Frame #: <test case 7 in file catpart.txt.tsl>
    @Test
    public void txtedTest7() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }


    // Frame #: <test case 8 in file catpart.txt.tsl>
    @Test
    public void txtedTest8() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-i", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }

    // Frame #: <test case 9 in file catpart.txt.tsl>
    @Test
    public void txtedTest9() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "01 alphanumeric123FOO!" + System.lineSeparator()
                + "02 abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 10 in file catpart.txt.tsl>
    @Test
    public void txtedTest10() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "alphanumeric123FOO!" + System.lineSeparator()
                + "abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-s", "0", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 11 in file catpart.txt.tsl>
    @Test
    public void txtedTest11() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "01 abcdefgh2468foo!" + System.lineSeparator()
                + "02 alphanumeric123FOO!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }


    // Frame #: <test case 12 in file catpart.txt.tsl>
    @Test
    public void txtedTest12() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "abcdefgh2468foo!" + System.lineSeparator()
                + "alphanumeric123FOO!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-s", "0", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));


    }

    // Frame #: <test case 13 in file catpart.txt.tsl>
    @Test
    public void txtedTest13() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOOBAR!" + System.lineSeparator() +
                        "02 abcdefgh2468foobar!" + System.lineSeparator() +
                        "03 alphanumeric123FOO!" + System.lineSeparator() +
                        "04 abcdefgh2468foo!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }


    // Frame #: <test case 14 in file catpart.txt.tsl>
    @Test
    public void txtedTest14() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOOBAR!" + System.lineSeparator() +
                        "02 abcdefgh2468foobar!" + System.lineSeparator() +
                        "03 alphanumeric123FOO!" + System.lineSeparator() +
                        "04 abcdefgh2468foo!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }


    // Frame #: <test case 15 in file catpart.txt.tsl>
    @Test
    public void txtedTest15() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo!" + System.lineSeparator() +
                        "02 alphanumeric123FOO!" + System.lineSeparator() +
                        "03 abcdefgh2468foobar!" + System.lineSeparator() +
                        "04 alphanumeric123FOOBAR!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
//        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 16 in file catpart.txt.tsl>
    @Test
    public void txtedTest16() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo!" + System.lineSeparator() +
                        "alphanumeric123FOO!" + System.lineSeparator() +
                        "abcdefgh2468foobar!" + System.lineSeparator() +
                        "alphanumeric123FOOBAR!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-s", "0", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 17 in file catpart.txt.tsl>
    @Test
    public void txtedTest17() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOO!" + System.lineSeparator() +
                        "02 alphanumeric123Foo!" + System.lineSeparator() +
                        "03 abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 18 in file catpart.txt.tsl>
    @Test
    public void txtedTest18() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "alphanumeric123FOO!" + System.lineSeparator() +
                        "alphanumeric123Foo!" + System.lineSeparator() +
                        "abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 19 in file catpart.txt.tsl>
    @Test
    public void txtedTest19() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo!" + System.lineSeparator() +
                        "02 alphanumeric123Foo!" + System.lineSeparator() +
                        "03 alphanumeric123FOO!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 20 in file catpart.txt.tsl>
    @Test
    public void txtedTest20() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo!" + System.lineSeparator() +
                        "alphanumeric123Foo!" + System.lineSeparator() +
                        "alphanumeric123FOO!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 21 in file catpart.txt.tsl>
    @Test
    public void txtedTest21() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOOBAR!" + System.lineSeparator() +
                        "02 alphanumeric123FooBAR!" + System.lineSeparator() +
                        "03 alphanumeric123Foobar!" + System.lineSeparator() +
                        "04 abcdefgh2468foobar!" + System.lineSeparator() +
                        "05 alphanumeric123BAR!" + System.lineSeparator() +
                        "06 alphanumeric123bar!" + System.lineSeparator() +
                        "07 alphanumeric123FOO!" + System.lineSeparator() +
                        "08 alphanumeric123Foo!" + System.lineSeparator() +
                        "09 abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-n", "2", "-e", "Bar", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 22 in file catpart.txt.tsl>
    @Test
    public void txtedTest22() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "alphanumeric123FOOBAR!" + System.lineSeparator() +
                        "alphanumeric123FooBAR!" + System.lineSeparator() +
                        "alphanumeric123Foobar!" + System.lineSeparator() +
                        "abcdefgh2468foobar!" + System.lineSeparator() +
                        "alphanumeric123BAR!" + System.lineSeparator() +
                        "alphanumeric123bar!" + System.lineSeparator() +
                        "alphanumeric123FOO!" + System.lineSeparator() +
                        "alphanumeric123Foo!" + System.lineSeparator() +
                        "abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 23 in file catpart.txt.tsl>
    @Test
    public void txtedTest23() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo!" + System.lineSeparator() +
                        "02 alphanumeric123Foo!" + System.lineSeparator() +
                        "03 alphanumeric123FOO!" + System.lineSeparator() +
                        "04 alphanumeric123bar!" + System.lineSeparator() +
                        "05 alphanumeric123BAR!" + System.lineSeparator() +
                        "06 abcdefgh2468foobar!" + System.lineSeparator() +
                        "07 alphanumeric123Foobar!" + System.lineSeparator() +
                        "08 alphanumeric123FooBAR!" + System.lineSeparator() +
                        "09 alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-e", "Bar", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 24 in file catpart.txt.tsl>
    @Test
    public void txtedTest24() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo!" + System.lineSeparator() +
                        "alphanumeric123Foo!" + System.lineSeparator() +
                        "alphanumeric123FOO!" + System.lineSeparator() +
                        "alphanumeric123bar!" + System.lineSeparator() +
                        "alphanumeric123BAR!" + System.lineSeparator() +
                        "abcdefgh2468foobar!" + System.lineSeparator() +
                        "alphanumeric123Foobar!" + System.lineSeparator() +
                        "alphanumeric123FooBAR!" + System.lineSeparator() +
                        "alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 25 in file catpart.txt.tsl>
    @Test
    public void txtedTest25() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "01 alphanumeric123FOO" + System.lineSeparator()
                + "02 abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-s", "0", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 26 in file catpart.txt.tsl>
    @Test
    public void txtedTest26() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "alphanumeric123FOO" + System.lineSeparator()
                + "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 27 in file catpart.txt.tsl>
    @Test
    public void txtedTest27() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo" + System.lineSeparator() +
                        "02 alphanumeric123FOO" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-n", "2", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 28 in file catpart.txt.tsl>
    @Test
    public void txtedTest28() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }




    // Frame #: <test case 29 in file catpart.txt.tsl>
    @Test
    public void txtedTest29() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo!" + System.lineSeparator() +
                        "alphanumeric123FOO!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-s","0", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }




    // Frame #: <test case 30 in file catpart.txt.tsl>
    @Test
    public void txtedTest30() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-r", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 31 in file catpart.txt.tsl>
    @Test
    public void txtedTest31() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "01 abcdefgh2468foo" + System.lineSeparator() +
                        "02 alphanumeric123FOO" + System.lineSeparator() +
                        "03 abcdefgh2468foobar" + System.lineSeparator() +
                        "04 alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-n", "2", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 32 in file catpart.txt.tsl>
    @Test
    public void txtedTest32() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 33 in file catpart.txt.tsl>
    @Test
    public void txtedTest33() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOO" + System.lineSeparator() +
                        "02 alphanumeric123Foo" + System.lineSeparator() +
                        "03 abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 34 in file catpart.txt.tsl>
    @Test
    public void txtedTest34() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-e", "Bar", "-i", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 35 in file catpart.txt.tsl>
    @Test
    public void txtedTest35() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "001 abcdefgh2468foo" + System.lineSeparator() +
                        "002 alphanumeric123Foo" + System.lineSeparator() +
                        "003 alphanumeric123FOO" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 36 in file catpart.txt.tsl>
    @Test
    public void txtedTest36() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-i", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 37 in file catpart.txt.tsl>
    @Test
    public void txtedTest37() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "001 alphanumeric123FOOBAR" + System.lineSeparator() +
                        "002 alphanumeric123FooBAR" + System.lineSeparator() +
                        "003 alphanumeric123Foobar" + System.lineSeparator() +
                        "004 abcdefgh2468foobar" + System.lineSeparator() +
                        "005 alphanumeric123BAR" + System.lineSeparator() +
                        "006 alphanumeric123bar" + System.lineSeparator() +
                        "007 alphanumeric123FOO" + System.lineSeparator() +
                        "008 alphanumeric123Foo" + System.lineSeparator() +
                        "009 abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-r", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 38 in file catpart.txt.tsl>
    @Test
    public void txtedTest38() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 39 in file catpart.txt.tsl>
    @Test
    public void txtedTest39() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo" + System.lineSeparator() +
                        "02 alphanumeric123Foo" + System.lineSeparator() +
                        "03 alphanumeric123FOO" + System.lineSeparator() +
                        "04 alphanumeric123bar" + System.lineSeparator() +
                        "05 alphanumeric123BAR" + System.lineSeparator() +
                        "06 abcdefgh2468foobar" + System.lineSeparator() +
                        "07 alphanumeric123Foobar" + System.lineSeparator() +
                        "08 alphanumeric123FooBAR" + System.lineSeparator() +
                        "09 alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 40 in file catpart.txt.tsl>
    @Test
    public void txtedTest40() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "Bar", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 41 in file catpart.txt.tsl>
    @Test
    public void txtedTest41() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOOBAR!" + System.lineSeparator()+
                        "02 alphanumeric123FOOBar!" + System.lineSeparator() +
                        "03 abcdefgh2468fooBar!" + System.lineSeparator() +
                        "04 abcdefgh2468foobar!" + System.lineSeparator() +
                        "05 alphanumeric123Bar!" + System.lineSeparator() +
                        "06 alphanumeric123FOO!" + System.lineSeparator() +
                        "07 abcdefgh2468foo!" + System.lineSeparator() ;


        File inputFile = createInputFile(input);
        String[] args = {"-r", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 42 in file catpart.txt.tsl>
    @Test
    public void txtedTest42() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "alphanumeric123FOOBAR!" + System.lineSeparator() +
                        "alphanumeric123FOOBar!" + System.lineSeparator() +
                        "abcdefgh2468fooBar!" + System.lineSeparator() +
                        "abcdefgh2468foobar!" + System.lineSeparator() +
                        "alphanumeric123Bar!" + System.lineSeparator() +
                        "alphanumeric123FOO!" + System.lineSeparator() +
                        "abcdefgh2468foo!" + System.lineSeparator() ;


        File inputFile = createInputFile(input);
        String[] args = {"-r", "-s", "0", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 43 in file catpart.txt.tsl>
    @Test
    public void txtedTest43() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "001 abcdefgh2468foo!" + System.lineSeparator()+
                        "002 alphanumeric123FOO!" + System.lineSeparator() +
                        "003 alphanumeric123Bar!" + System.lineSeparator() +
                        "004 abcdefgh2468foobar!" + System.lineSeparator() +
                        "005 abcdefgh2468fooBar!" + System.lineSeparator() +
                        "006 alphanumeric123FOOBar!" + System.lineSeparator() +
                        "007 alphanumeric123FOOBAR!" + System.lineSeparator() ;

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-x","!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
        //assertTrue("Unexpected stderr output", errStream.toString().isEmpty());

    }

    // Frame #: <test case 44 in file catpart.txt.tsl>
    @Test
    public void txtedTest44() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo!" + System.lineSeparator()+
                        "alphanumeric123FOO!" + System.lineSeparator() +
                        "alphanumeric123Bar!" + System.lineSeparator() +
                        "abcdefgh2468foobar!" + System.lineSeparator() +
                        "abcdefgh2468fooBar!" + System.lineSeparator() +
                        "alphanumeric123FOOBar!" + System.lineSeparator() +
                        "alphanumeric123FOOBAR!" + System.lineSeparator() ;

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 45 in file catpart.txt.tsl>
    @Test
    public void txtedTest45() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "01 alphanumeric123FOOBAR!" + System.lineSeparator() +
                "02 alphanumeric123FooBAR!" + System.lineSeparator() +
                "03 alphanumeric123FOOBar!" + System.lineSeparator() +
                "04 alphanumeric123FooBar!" + System.lineSeparator() +
                "05 abcdefgh2468fooBar!" + System.lineSeparator() +
                "06 alphanumeric123Foobar!" + System.lineSeparator() +
                "07 abcdefgh2468foobar!" + System.lineSeparator() +
                "08 alphanumeric123BAR!" + System.lineSeparator() +
                "09 alphanumeric123Bar!" + System.lineSeparator() +
                "10 alphanumeric123bar!" + System.lineSeparator() +
                "11 alphanumeric123FOO!" + System.lineSeparator() +
                "12 alphanumeric123Foo!" + System.lineSeparator() +
                "13 abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 46 in file catpart.txt.tsl>
    @Test
    public void txtedTest46() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "alphanumeric123FOOBAR!" + System.lineSeparator() +
                "alphanumeric123FooBAR!" + System.lineSeparator() +
                "alphanumeric123FOOBar!" + System.lineSeparator() +
                "alphanumeric123FooBar!" + System.lineSeparator() +
                "abcdefgh2468fooBar!" + System.lineSeparator() +
                "alphanumeric123Foobar!" + System.lineSeparator() +
                "abcdefgh2468foobar!" + System.lineSeparator() +
                "alphanumeric123BAR!" + System.lineSeparator() +
                "alphanumeric123Bar!" + System.lineSeparator() +
                "alphanumeric123bar!" + System.lineSeparator() +
                "alphanumeric123FOO!" + System.lineSeparator() +
                "alphanumeric123Foo!" + System.lineSeparator() +
                "abcdefgh2468foo!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 47 in file catpart.txt.tsl>
    @Test
    public void txtedTest47() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "01 abcdefgh2468foo!" + System.lineSeparator() +
                "02 alphanumeric123Foo!" + System.lineSeparator() +
                "03 alphanumeric123FOO!" + System.lineSeparator() +
                "04 alphanumeric123bar!" + System.lineSeparator() +
                "05 alphanumeric123Bar!" + System.lineSeparator() +
                "06 alphanumeric123BAR!" + System.lineSeparator() +
                "07 abcdefgh2468foobar!" + System.lineSeparator() +
                "08 alphanumeric123Foobar!" + System.lineSeparator() +
                "09 abcdefgh2468fooBar!" + System.lineSeparator() +
                "10 alphanumeric123FooBar!" + System.lineSeparator() +
                "11 alphanumeric123FOOBar!" + System.lineSeparator() +
                "12 alphanumeric123FooBAR!" + System.lineSeparator() +
                "13 alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 48 in file catpart.txt.tsl>
    @Test
    public void txtedTest48() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected = "abcdefgh2468foo!" + System.lineSeparator() +
                "alphanumeric123Foo!" + System.lineSeparator() +
                "alphanumeric123FOO!" + System.lineSeparator() +
                "alphanumeric123bar!" + System.lineSeparator() +
                "alphanumeric123Bar!" + System.lineSeparator() +
                "alphanumeric123BAR!" + System.lineSeparator() +
                "abcdefgh2468foobar!" + System.lineSeparator() +
                "alphanumeric123Foobar!" + System.lineSeparator() +
                "abcdefgh2468fooBar!" + System.lineSeparator() +
                "alphanumeric123FooBar!" + System.lineSeparator() +
                "alphanumeric123FOOBar!" + System.lineSeparator() +
                "alphanumeric123FooBAR!" + System.lineSeparator() +
                "alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", "!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 49 in file catpart.txt.tsl>
    @Test
    public void txtedTest49() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 alphanumeric123FOOBAR" + System.lineSeparator() +
                        "02 alphanumeric123FOOBar" + System.lineSeparator() +
                        "03 abcdefgh2468fooBar" + System.lineSeparator() +
                        "04 abcdefgh2468foobar" + System.lineSeparator() +
                        "05 alphanumeric123Bar" + System.lineSeparator() +
                        "06 alphanumeric123FOO" + System.lineSeparator() +
                        "07 abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-s", "0", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 50 in file catpart.txt.tsl>
    @Test
    public void txtedTest50() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "alphanumeric123FOOBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 51 in file catpart.txt.tsl>
    @Test
    public void txtedTest51() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo" + System.lineSeparator() +
                        "02 alphanumeric123FOO" + System.lineSeparator() +
                        "03 alphanumeric123Bar" + System.lineSeparator() +
                        "04 abcdefgh2468foobar" + System.lineSeparator() +
                        "05 abcdefgh2468fooBar" + System.lineSeparator() +
                        "06 alphanumeric123FOOBar" + System.lineSeparator() +
                        "07 alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 52 in file catpart.txt.tsl>
    @Test
    public void txtedTest52() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "abcdefgh2468foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "abcdefgh2468fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 53 in file catpart.txt.tsl>
    @Test
    public void txtedTest53() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator();

        String expected =
                "01 alphanumeric123bar" + System.lineSeparator() +
                        "02 alphanumeric123FOO" + System.lineSeparator() +
                        "03 alphanumeric123Foo" + System.lineSeparator() +
                        "04 abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }



    // Frame #: <test case 54 in file catpart.txt.tsl>
    @Test
    public void txtedTest54() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator();

        String expected =
                "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }



    // Frame #: <test case 55 in file catpart.txt.tsl>
    @Test
    public void txtedTest55() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator();

        String expected =
                "01 abcdefgh2468foo" + System.lineSeparator() +
                        "02 alphanumeric123Foo" + System.lineSeparator() +
                        "03 alphanumeric123FOO" + System.lineSeparator() +
                        "04 alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }



    // Frame #: <test case 56 in file catpart.txt.tsl>
    @Test
    public void txtedTest56() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }



    // Frame #: <test case 57 in file catpart.txt.tsl>
    @Test
    public void txtedTest57() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "01 abcdefgh2468foo!" + System.lineSeparator() +
                        "02 alphanumeric123FOO!" + System.lineSeparator() +
                        "03 abcdefgh2468foobar!" + System.lineSeparator() +
                        "04 alphanumeric123FOOBAR!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        //#baar
        String[] args = {"-n","1","-e", "Bar", "-s", "0", "-n", "2", "-x", "!", inputFile.getPath()};
        Main.main(args);

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }



    // Frame #: <test case 59 in file catpart.txt.tsl>
    @Test
    public void txtedTest59() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", inputFile.getPath()};
        Main.main(args);

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 60 in file catpart.txt.tsl>
    @Test
    public void txtedTest60() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "" + System.lineSeparator() ;

        File inputFile = createInputFile(input);
        String[] args = {"-s", "1", inputFile.getPath()};
        Main.main(args);

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 61 in file catpart.txt.tsl>
    @Test
    public void txtedTest61() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());


    }

    // Frame #: <test case 62 in file catpart.txt.tsl>
    @Test
    public void txtedTest62() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }

    // Frame #: <test case 63 in file catpart.txt.tsl>
    @Test
    public void txtedTest63() throws Exception {
        String input = "";
        String expected =
                "" ;

        File inputFile = createInputFile(input);
        String[] args = {"-r", inputFile.getPath()};
        Main.main(args);
        //   assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 64 in file catpart.txt.tsl>
    @Test
    public void txtedTest64() throws Exception {
        String input = "" + System.lineSeparator();
        String expected = "" ;

        File inputFile = createInputFile(input);
        String[] args = {"-f", inputFile.getPath()};
        Main.main(args);

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }


    // Frame #: <test case 65 in file catpart.txt.tsl>
    @Test
    public void txtedTest65() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }


    // Frame #: <test case 66 in file catpart.txt.tsl>
    @Test
    public void txtedTest66() throws Exception {
        String input =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator() +
                        "alphanumeric123Bar" + System.lineSeparator() +
                        "alphanumeric123BAR" + System.lineSeparator() +
                        "abcdefgh2468foobar" + System.lineSeparator() +
                        "alphanumeric123Foobar" + System.lineSeparator() +
                        "abcdefgh2468fooBar" + System.lineSeparator() +
                        "alphanumeric123FooBar" + System.lineSeparator() +
                        "alphanumeric123FOOBar" + System.lineSeparator() +
                        "alphanumeric123FooBAR" + System.lineSeparator() +
                        "alphanumeric123FOOBAR" + System.lineSeparator();

        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", "", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }
    // Frame #: <test case 67 in file catpart.txt.tsl>
    @Test
    public void txtedTest67() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n","-", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());


    }

    // Frame #: <test case 68 in file catpart.txt.tsl>
    @Test
    public void txtedTest68() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "abcdefgh2468foo" + System.lineSeparator() +
                        "alphanumeric123Foo" + System.lineSeparator() +
                        "alphanumeric123FOO" + System.lineSeparator() +
                        "alphanumeric123bar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s","-", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());

    }


    // Frame #: <test case 69 in file catpart.txt.tsl>
    @Test
    public void txtedTest69() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "" + System.lineSeparator() ;
        File inputFile = createInputFile(input);
        String[] args = {"-e","foo","-x","foo", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

    // Frame #: <test case 70 in file catpart.txt.tsl>
    @Test
    public void txtedTest70() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                "" + System.lineSeparator();
        File inputFile = createInputFile(input);
        String[] args = {"-e","foo","-x","foo","-n","1", inputFile.getPath()};
        Main.main(args);

        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: <test case 71 in file catpart.txt.tsl>
    @Test
    public void txtedTest71() throws Exception {
        String input = "abcdefgh2468foo" + System.lineSeparator();
        String expected =
                " abcdefgh2468foo" + System.lineSeparator() ;

        File inputFile = createInputFile(input);
        String[] args = {"-n","0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));

    }

}
















