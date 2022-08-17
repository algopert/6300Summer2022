package edu.gatech.seclass.texttool;

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
         * TEST UTILITIES
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
                OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file),
                                StandardCharsets.UTF_8);
                fileWriter.write(input);
                fileWriter.close();
                return file;
        }

        private String getFileContent(String filename) {
                String content = "";
                try {
                        content = Files.readString(Paths.get(filename), charset);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return content;
        }

        /*
         * TEST CASES
         */

        /*
         * texttool -f FILE input FILE: alphanumeric123foobar↵
         * 
         * edited FILE: alphanumeric123foobar↵
         * 
         * stdout: nothing sent to stdout stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest1() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -r 123 456 FILE input FILE: alphanumeric123foobar123↵
         * 
         * edited FILE: file not edited stdout: alphanumeric456foobar123↵
         * 
         * stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest2() throws Exception {
                String input = "alphanumeric123foobar123" + System.lineSeparator();

                String expected = "alphanumeric456foobar123" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", "123", "456", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output does not match", expected, outStream.toString());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -i -r foo candy FILE input FILE: alphanumeric123FOObar123↵
         * 
         * edited FILE: file not edited stdout: alphanumeric123candybar123↵
         * 
         * stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest3() throws Exception {
                String input = "alphanumeric123FOObar123" + System.lineSeparator();

                String expected = "alphanumeric123candybar123" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-i", "-r", "foo", "candy", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output does not match", expected, outStream.toString());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -p ## FILE input FILE: alphanumeric123foobar↵
         * 
         * edited FILE: file not edited stdout: ##alphanumeric123foobar↵
         * 
         * stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest4() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                String expected = "##alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-p", "##", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output does not match", expected, outStream.toString());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -d 3 FILE input FILE: alphanumeric123foobar↵
         * 
         * edited FILE: file not edited stdout: alphanumeric123foobar↵
         * alphanumeric123foobar↵ alphanumeric123foobar↵ alphanumeric123foobar↵
         * 
         * stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest5() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                String expected = "alphanumeric123foobar" + System.lineSeparator() + "alphanumeric123foobar"
                                + System.lineSeparator() + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "3", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output does not match", expected, outStream.toString());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -r foo FOO -f -p !!! -d 1 FILE input FILE: foobar0Foobar1↵
         * foobar2foobar3↵ foobar4Foobar5↵ foobar6foobar7↵ foobar8Foobar9↵
         * 
         * edited FILE: !!!FOObar0Foobar1↵ !!!FOObar0Foobar1↵ !!!FOObar2foobar3↵
         * !!!FOObar2foobar3↵ !!!FOObar4Foobar5↵ !!!FOObar4Foobar5↵ !!!FOObar6foobar7↵
         * !!!FOObar6foobar7↵ !!!FOObar8Foobar9↵ !!!FOObar8Foobar9↵
         * 
         * stdout: nothing sent to stdout stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest6() throws Exception {
                String input = "foobar0Foobar1" + System.lineSeparator() + "foobar2foobar3" + System.lineSeparator()
                                + "foobar4Foobar5" + System.lineSeparator() + "foobar6foobar7" + System.lineSeparator()
                                + "foobar8Foobar9" + System.lineSeparator();

                String expected = "!!!FOObar0Foobar1" + System.lineSeparator() + "!!!FOObar0Foobar1"
                                + System.lineSeparator() + "!!!FOObar2foobar3" + System.lineSeparator()
                                + "!!!FOObar2foobar3" + System.lineSeparator() + "!!!FOObar4Foobar5"
                                + System.lineSeparator() + "!!!FOObar4Foobar5" + System.lineSeparator()
                                + "!!!FOObar6foobar7" + System.lineSeparator() + "!!!FOObar6foobar7"
                                + System.lineSeparator() + "!!!FOObar8Foobar9" + System.lineSeparator()
                                + "!!!FOObar8Foobar9" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", "foo", "FOO", "-f", "-p", "!!!", "-d", "1", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -c -20 -d 5 -d 1 -p !!! -p ## FILE input FILE:
         * alphanumeric123foobar↵
         * 
         * edited FILE: file not edited stdout: ##grvngtaskxoi123luuhgx↵
         * ##grvngtaskxoi123luuhgx↵
         * 
         * stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest7() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                String expected = "##grvngtaskxoi123luuhgx" + System.lineSeparator() + "##grvngtaskxoi123luuhgx"
                                + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", "-20", "-d", "5", "-d", "1", "-p", "!!!", "-p", "##", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output does not match", expected, outStream.toString());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool input FILE: 01234abc↵ 56789def↵ 01234ABC↵ 56789DEF↵
         * 
         * edited FILE: file not edited stdout: nothing sent to stdout stderr: Usage:
         * texttool [ -f | -o output_file_name | -i | -r old new | -p prefix | -c n | -d
         * n ] FILE
         */
        @Test
        public void exampleTest8() throws Exception {
                String input = "01234abc" + System.lineSeparator() + "56789def" + System.lineSeparator() + "01234ABC"
                                + System.lineSeparator() + "56789DEF" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = {};
                MyMain.main(args);

                assertEquals("stderr output does not match",
                                "Usage: texttool [ -f | -o output_file_name | -i | -r old new | -p prefix | -c n | -d n ] FILE",
                                errStream.toString().strip());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        /*
         * texttool -c 1 FILE input FILE: alphanumeric123foobar↵
         * 
         * edited FILE: file not edited stdout: bmqibovnfsjd123gppcbs↵
         * 
         * stderr: nothing sent to stderr
         */
        @Test
        public void exampleTest9() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                String expected = "bmqibovnfsjd123gppcbs" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", "1", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output does not match", expected, outStream.toString());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 1 <error>
        // Output to FileName : -o <none>
        @Test
        public void TestCase1() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-o", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 2 <error> (follows [if editfile])
        // Output to FileName : -o <outfilename>
        @Test
        public void TestCase2() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-o", "aaa", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 3 <error>
        // Output to FileName : -o <alreadyExist>
        @Test
        public void TestCase3() throws Exception {
                String input = "123ABC" + System.lineSeparator();
                File inputFile = createInputFile(input);
                String filename = "aaa";
                Files.writeString(Paths.get(filename), "fileContent", StandardCharsets.UTF_8);

                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 4 <error>
        // Replace : -r <none>
        @Test
        public void TestCase4() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 5 <error>
        // Replace : -r <oneParam>
        @Test
        public void TestCase5() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", "one", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 6 <error> (follows [else] without -r)
        // Insensitive : -i
        @Test
        public void TestCase6() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-i", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 7 <error>
        // Prefix : -p <none>
        @Test
        public void TestCase7() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-p", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 8 <error>
        // Duplicate : -d <none>
        @Test
        public void TestCase8() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 9 <error>
        // Duplicate : -d <out of range>
        @Test
        public void TestCase9() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "11", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 9 <error>
        // Duplicate : -d <out of range>
        @Test
        public void TestCase9_1() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "0", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 9 <error>
        // Duplicate : -d <out of range>
        @Test
        public void TestCase9_2() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "0", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 9 <error>
        // Duplicate : -d <out of range>
        @Test
        public void TestCase9_3() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "11", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 10 <error>
        // Duplicate : -d <none integer>
        @Test
        public void TestCase10() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "uu", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 11 <error>
        // Cipher : -c <none>
        @Test
        public void TestCase11() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 12 <error> (follows [if] with -r)
        // Cipher : -c <n>
        @Test
        public void TestCase12() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r 123 456 -c -7", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 13 <error>
        // Cipher : -c <none integer>
        @Test
        public void TestCase13() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", "x", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 14 <error>
        // Cipher : -c <out of range>
        @Test
        public void TestCase14() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", "26", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 14 <error>
        // Cipher : -c <out of range>
        @Test
        public void TestCase14_1() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", "-26", inputFile.getPath() };
                MyMain.main(args);

                assertTrue("stderr occurred", !errStream.toString().isEmpty());
                assertTrue("stdout output does not match", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 15 (Key = 1.0.3.1.2.4.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase15() throws Exception {
                String input = "sacalphanumericAlp123foobar" + System.lineSeparator()
                                + "csahanumericAlp123" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "ALP", "TET", "-i", "-p", "Web.", "-d", "2", inputFile.getPath() };
                String expected = "Web.sacTEThanumericAlp123foobar" + System.lineSeparator()
                                + "Web.sacTEThanumericAlp123foobar" + System.lineSeparator()
                                + "Web.sacTEThanumericAlp123foobar" + System.lineSeparator()
                                + "Web.csahanumericTET123" + System.lineSeparator()
                                + "Web.csahanumericTET123" + System.lineSeparator()
                                + "Web.csahanumericTET123" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 16 (Key = 1.0.3.1.2.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Prefix : -p <prefix>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase16() throws Exception {
                String input = "sacalphanumericAlp123foobar" + System.lineSeparator()
                                + "csahanumericAlp123" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "ALP", "TET", "-i", "-p", "Web.", inputFile.getPath() };
                String expected = "Web.sacTEThanumericAlp123foobar" + System.lineSeparator()
                                + "Web.csahanumericTET123" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 17 (Key = 1.0.3.1.3.4.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Duplicate : -d <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase17() throws Exception {
                String input = "sacalphanumericAlp123foobar" + System.lineSeparator()
                                + "csahanumericAlp123" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "ALP", "TET", "-i", "-d", "1", inputFile.getPath() };
                String expected = "sacTEThanumericAlp123foobar" + System.lineSeparator()
                                + "sacTEThanumericAlp123foobar" + System.lineSeparator()
                                + "csahanumericTET123" + System.lineSeparator()
                                + "csahanumericTET123" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 18 (Key = 1.0.3.1.3.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase18() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "Pha", "456", "-i", inputFile.getPath() };
                String expected = "al456numeric123foobar" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 18 (Key = 1.0.3.1.3.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Input File : edited
        // STDOUT : empty
        // @Test
        // public void TestCase18_1() throws Exception {
        //         String input = "" ;

        //         File inputFile = createInputFile(input);
        //         String[] args = { "-f", "-i", inputFile.getPath() };
        //         String expected = "";

        //         MyMain.main(args);

        //         assertTrue("stdout output is empty", outStream.toString().isEmpty());
        //         assertTrue("stderr output is empty", !errStream.toString().isEmpty());
        //         assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        // }

        // Test Case 19 (Key = 1.0.3.2.2.4.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase19() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "Pha", "456", "-p", "www", "-d", "1", inputFile.getPath() };
                String expected = "wwwalphanumeric123foobar" + System.lineSeparator()
                                + "wwwalphanumeric123foobar" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 20 (Key = 1.0.3.2.2.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Prefix : -p <prefix>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase20() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "Pha", "456", "-p", "www", inputFile.getPath() };
                String expected = "wwwalphanumeric123foobar" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 21 (Key = 1.0.3.2.3.4.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Duplicate : -d <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase21() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "Pha", "456", "-d", "10", inputFile.getPath() };
                String expected = "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 22 (Key = 1.0.3.2.3.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Replace : -r <old> <new>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase22() throws Exception {
                String input = "alphanuALPmeric123foobar" + System.lineSeparator()
                                + "AlphanumALPALPeric" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-r", "ALP", "000", inputFile.getPath() };
                String expected = "alphanu000meric123foobar" + System.lineSeparator()
                                + "Alphanum000ALPeric" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 23 (Key = 1.0.4.2.2.4.2.0.1.2.0.)
        // Edit file input File : -f
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Cipher : -c <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase23() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-p", "123", "-d", "2", "-c", "-15", inputFile.getPath() };
                String expected = "123lwaslyfxpctn123qzzmlc" + System.lineSeparator()
                                + "123lwaslyfxpctn123qzzmlc" + System.lineSeparator()
                                + "123lwaslyfxpctn123qzzmlc" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 24 (Key = 1.0.4.2.2.4.5.0.1.2.0.)
        // Edit file input File : -f
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase24() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-p", "123", "-d", "2", inputFile.getPath() };
                String expected = "123alphanumeric123foobar" + System.lineSeparator()
                                + "123alphanumeric123foobar" + System.lineSeparator()
                                + "123alphanumeric123foobar" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 25 (Key = 1.0.4.2.2.5.2.0.1.2.0.)
        // Edit file input File : -f
        // Prefix : -p <prefix>
        // Cipher : -c <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase25() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-p", "123", "-c", "2", inputFile.getPath() };
                String expected = "123cnrjcpwogtke123hqqdct" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 26 (Key = 1.0.4.2.2.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Prefix : -p <prefix>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase26() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator()
                                + "abbe" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-p", "123", inputFile.getPath() };
                String expected = "123alphanumeric123foobar" + System.lineSeparator()
                                + "123abbe" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 27 (Key = 1.0.4.2.3.4.2.0.1.2.0.)
        // Edit file input File : -f
        // Duplicate : -d <n>
        // Cipher : -c <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase27() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-d", "2", "-c", "-2", inputFile.getPath() };
                String expected = "yjnfylskcpga123dmmzyp" + System.lineSeparator()
                                + "yjnfylskcpga123dmmzyp" + System.lineSeparator()
                                + "yjnfylskcpga123dmmzyp" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 28 (Key = 1.0.4.2.3.4.5.0.1.2.0.)
        // Edit file input File : -f
        // Duplicate : -d <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase28() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator()
                                + "abcd" + System.lineSeparator()
                                + "efgh" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-d", "2", inputFile.getPath() };
                String expected = "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "abcd" + System.lineSeparator()
                                + "abcd" + System.lineSeparator()
                                + "abcd" + System.lineSeparator()
                                + "efgh" + System.lineSeparator()
                                + "efgh" + System.lineSeparator()
                                + "efgh" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 29 (Key = 1.0.4.2.3.5.2.0.1.2.0.)
        // Edit file input File : -f
        // Cipher : -c <n>
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase29() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-c", "20", inputFile.getPath() };
                String expected = "ufjbuhogylcw123ziivul" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        @Test
        public void TestCase29_1() throws Exception {
                String input = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12334567890+_)(*&^%$#@!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-c", "-20", inputFile.getPath() };
                String expected = "ghijklmnopqrstuvwxyzabcdefGHIJKLMNOPQRSTUVWXYZABCDEF12334567890+_)(*&^%$#@!" + System.lineSeparator();

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", expected, getFileContent(inputFile.getPath()));
        }

        // Test Case 30 (Key = 1.0.4.2.3.5.5.0.1.2.0.)
        // Edit file input File : -f
        // Input File : edited
        // STDOUT : empty
        @Test
        public void TestCase30() throws Exception {
                String input = "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator()
                                + "alphanumeric123foobar" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", inputFile.getPath() };

                MyMain.main(args);

                assertTrue("stdout output is empty", outStream.toString().isEmpty());
                assertTrue("stderr output is empty", errStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 31 (Key = 2.2.3.1.2.4.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase31() throws Exception {
                String input = "xyzXYZabc123!#$@" + System.lineSeparator()
                + "xxYzYZabc!#$@" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "xyz456XYZabc123!#$@" + System.lineSeparator() + "xyz456XYZabc123!#$@" + System.lineSeparator()
                + "xyzx456YZabc!#$@" + System.lineSeparator() + "xyzx456YZabc!#$@" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "xyz", "456", "-i", "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 32 (Key = 2.2.3.1.2.5.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Prefix : -p <prefix>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase32() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "xyz123ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "xyz", "456", "-i", "-p", "xyz",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 33 (Key = 2.2.3.1.3.4.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase33() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "456ABC" + System.lineSeparator()
                                + "456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 34 (Key = 2.2.3.1.3.5.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase34() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "456ABC" + System.lineSeparator();
                // + "456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-i",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 35 (Key = 2.2.3.2.2.4.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase35() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "xyz456ABC" + System.lineSeparator()
                                + "xyz456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 36 (Key = 2.2.3.2.2.5.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Prefix : -p <prefix>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed

        @Test
        public void TestCase36() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "xyz456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-p", "xyz",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 37 (Key = 2.2.3.2.3.4.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase37() throws Exception {
                String input = "12ABC" + System.lineSeparator()
                +"12aABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "456BC" + System.lineSeparator()
                                + "456BC" + System.lineSeparator()
                                +"12aABC" + System.lineSeparator()
                                + "12aABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "12A", "456", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 38 (Key = 2.2.3.2.3.5.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Replace : -r <old> <new>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase38() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 39 (Key = 2.2.4.2.2.4.2.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase39() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "zab123CDE" + System.lineSeparator()
                                + "zab123CDE" + System.lineSeparator();
                String[] args = { "-o", filename, "-p", "xyz", "-d", "1", "-c", "2",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 40 (Key = 2.2.4.2.2.4.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase40() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "xyz123ABC" + System.lineSeparator()
                                + "xyz123ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 41 (Key = 2.2.4.2.2.5.2.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Prefix : -p <prefix>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase41() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "yza123BCD" + System.lineSeparator();

                String[] args = { "-o", filename, "-p", "xyz", "-c", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 42 (Key = 2.2.4.2.2.5.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Prefix : -p <prefix>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase42() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "xyz123ABC" + System.lineSeparator();

                String[] args = { "-o", filename, "-p", "xyz",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 43 (Key = 2.2.4.2.3.4.2.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Duplicate : -d <n>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase43() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "123BCD" + System.lineSeparator()
                                + "123BCD" + System.lineSeparator();

                String[] args = { "-o", filename, "-d", "1", "-c", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 44 (Key = 2.2.4.2.3.4.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase44() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "123ABC" + System.lineSeparator()
                                + "123ABC" + System.lineSeparator();

                String[] args = { "-o", filename, "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 45 (Key = 2.2.4.2.3.5.2.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase45() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "123BCD" + System.lineSeparator();

                String[] args = { "-o", filename, "-c", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 46 (Key = 2.2.4.2.3.5.5.0.2.2.1.)
        // Output to FileName : -o <outfilename>
        // Input File : not edited
        // STDOUT : empty
        // FILEOUT : outputed
        @Test
        public void TestCase46() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "ccc";
                String expected = "123ABC" + System.lineSeparator();

                String[] args = { "-o", filename,
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("output file content matched", expected, getFileContent(filename));

                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 47 (Key = 2.4.3.1.2.4.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase47() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "www234ABC" + System.lineSeparator()
                                + "www234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-i", "-p", "www", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 48 (Key = 2.4.3.1.2.5.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Prefix : -p <prefix>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase48() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "www234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-i", "-p", "www",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 49 (Key = 2.4.3.1.3.4.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase49() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "234ABC" + System.lineSeparator()
                                + "234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-i", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 50 (Key = 2.4.3.1.3.5.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Insensitive : -i
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase50() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-i",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 51 (Key = 2.4.3.2.2.4.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase51() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123234ABC" + System.lineSeparator()
                                + "123234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-p", "123", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 52 (Key = 2.4.3.2.2.5.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Prefix : -p <prefix>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase52() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-p", "123",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 53 (Key = 2.4.3.2.3.4.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase53() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "234ABC" + System.lineSeparator()
                                + "234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 54 (Key = 2.4.3.2.3.5.5.0.2.1.0.)
        // Replace : -r <old> <new>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase54() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "234ABC" + System.lineSeparator();

                String[] args = { "-r", "123", "234",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 55 (Key = 2.4.4.2.2.4.2.0.2.1.0.)
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase55() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "zzz123DEF" + System.lineSeparator()
                                + "zzz123DEF" + System.lineSeparator();

                String[] args = { "-p", "www", "-d", "1", "-c", "3",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 56 (Key = 2.4.4.2.2.4.5.0.2.1.0.)
        // Prefix : -p <prefix>
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase56() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "www123ABC" + System.lineSeparator()
                                + "www123ABC" + System.lineSeparator();

                String[] args = { "-p", "www", "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 57 (Key = 2.4.4.2.2.5.2.0.2.1.0.)
        // Prefix : -p <prefix>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase57() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "xxx123BCD" + System.lineSeparator();

                String[] args = { "-p", "www", "-c", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 58 (Key = 2.4.4.2.2.5.5.0.2.1.0.)
        // Prefix : -p <prefix>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase58() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "www123ABC" + System.lineSeparator();

                String[] args = { "-p", "www",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 59 (Key = 2.4.4.2.3.4.2.0.2.1.0.)
        // Duplicate : -d <n>
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase59() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123CDE" + System.lineSeparator()
                                + "123CDE" + System.lineSeparator();

                String[] args = { "-d", "1", "-c", "2",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 60 (Key = 2.4.4.2.3.4.5.0.2.1.0.)
        // Duplicate : -d <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase60() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123ABC" + System.lineSeparator()
                                + "123ABC" + System.lineSeparator();

                String[] args = { "-d", "1",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 61 (Key = 2.4.4.2.3.5.2.0.2.1.0.)
        // Cipher : -c <n>
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase61() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123VWX" + System.lineSeparator();

                String[] args = { "-c", "21",
                                inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", expected, outStream.toString());

        }

        // Test Case 62 (Key = 2.4.4.2.3.5.5.1.2.1.0.)
        // Omitted : error
        // Input File : not edited
        // STDOUT : stdout
        @Test
        public void TestCase62() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String[] args = { inputFile.getPath() };
                MyMain.main(args);
                assertTrue("stderr output Error", errStream.toString().isEmpty());
                assertEquals("stdout output content matched", input, outStream.toString());
        }

}
