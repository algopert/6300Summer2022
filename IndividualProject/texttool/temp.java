public class temp {
    

        // Test Case 1 <error>
        // Output to file : -o <wrong param>
        @Test
        public void exampleTest11() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-o", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 2 <error>
        // Replace string : -r <wrong param>
        @Test
        public void exampleTest12() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 3 <error>
        // Add prefix : -p <wrong param>
        @Test
        public void exampleTest13() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 4 <error>
        // Dupicate N times : -d <wrong param>
        @Test
        public void exampleTest14() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 5 <single>
        // Dupicate N times : -d <out of range>
        @Test
        public void exampleTest15() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-d", "100", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 6 <error>
        // Encode : -c <wrong param>
        @Test
        public void exampleTest16() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 7 <single>
        // Encode : -c <out of range>
        @Test
        public void exampleTest17() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-c", "-100", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 8 <single>
        // input FILE : Empty
        @Test
        public void exampleTest18() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r" };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 9 <single>
        // input FILE : File not found
        @Test
        public void exampleTest19() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-r", "20" };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 10 (Key = 1.2.2.1.2.3.3.2.0.1.0.0.)
        // Edit file input File : -f
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest20() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f -o FILE -r 123 456 -i -p 345 -d 5 -c 12", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 11 (Key = 1.2.2.1.2.3.4.2.0.1.0.0.)
        // Edit file input File : -f
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest21() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-f", "-o", "FILE", "-r", "123", "456", "-i", "-p", "345", "-d", "5",
                                inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 42 (Key = 2.2.2.1.2.3.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest22() throws Exception {
                String input = "alphanumeric123foobar!" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String[] args = { "-o", "FILE", "-r", "123", "456", "-i", "-p", "345", "-d", "5", "-c", "5",
                                inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 43 (Key = 2.2.2.1.2.3.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest23() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                // File outputFile = File.createTempFile("prefix-","-suffix");
                String filename = "aaa";
                String expected = "xyz456ABC" + System.lineSeparator() + "xyz456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        @Test
        public void exampleTest23_Repeatetion() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                // File outputFile = File.createTempFile("prefix-","-suffix");
                String filename = "aaa";
                Files.writeString(Paths.get(filename), "fileContent", StandardCharsets.UTF_8);

                String expected = "xyz456ABC" + System.lineSeparator() + "xyz456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content not matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 44 (Key = 2.2.2.1.2.4.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>

        @Test
        public void exampleTest24() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "xyz456ABC" + System.lineSeparator() + "xyz456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-p", "xyz", "-d", "1",
                                inputFile.getPath() };
                Main.main(args);
                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 45 (Key = 2.2.2.1.2.4.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest25() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "xyz456ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-p", "xyz", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 46 (Key = 2.2.2.1.3.3.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Dupicate N times : -d <n>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest26() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-d", "5", "-c", "3", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 47 (Key = 2.2.2.1.3.3.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest27() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "4563ABC" + System.lineSeparator() + "4563ABC" + System.lineSeparator() + "4563ABC"
                                + System.lineSeparator() + "4563ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "12", "456", "-i", "-d", "3", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 48 (Key = 2.2.2.1.3.4.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest28() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-r", "123", "456", "-i", "-c", "15", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 49 (Key = 2.2.2.1.3.4.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Case Insensitive : -i
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest29() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "4563ABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "12", "456", "-i", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 50 (Key = 2.2.2.2.2.3.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest30() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-r", "123", "456", "p", "qr", "-d", "5", "-c", "15",
                                inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 51 (Key = 2.2.2.2.2.3.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest31() throws Exception {
                String input = "123ABC" + System.lineSeparator() + "Q123DEF" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "WWW.456ABC" + System.lineSeparator() + "WWW.456ABC" + System.lineSeparator()
                                + "WWW.Q456DEF" + System.lineSeparator() + "WWW.Q456DEF" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-p", "WWW.", "-d", "1", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 52 (Key = 2.2.2.2.2.4.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Add prefix : -p <prefix>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest32() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-r", "123", "456", "p", "www", "-c", "15", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 53 (Key = 2.2.2.2.2.4.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Add prefix : -p <prefix>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest33() throws Exception {
                String input = "123ABC" + System.lineSeparator() + "Q123DEF" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "WWW.456ABC" + System.lineSeparator() + "WWW.Q456DEF" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-p", "WWW.", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 54 (Key = 2.2.2.2.3.3.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Dupicate N times : -d <n>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest34() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-r", "123", "456", "-d", "2", "-c", "15", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 55 (Key = 2.2.2.2.3.3.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest35() throws Exception {
                String input = "123ABC" + System.lineSeparator() + "Q123DEF" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "456ABC" + System.lineSeparator() + "456ABC" + System.lineSeparator() + "456ABC"
                                + System.lineSeparator() + "Q456DEF" + System.lineSeparator() + "Q456DEF"
                                + System.lineSeparator() + "Q456DEF" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", "-d", "2", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 56 (Key = 2.2.2.2.3.4.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest36() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-r", "123", "456", "-c", "15", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 57 (Key = 2.2.2.2.3.4.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Replace string : -r <old> <new>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest37() throws Exception {
                String input = "123ABC" + System.lineSeparator() + "Q123DEF" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "456ABC" + System.lineSeparator() + "Q456DEF" + System.lineSeparator();
                String[] args = { "-o", filename, "-r", "123", "456", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 58 (Key = 2.2.3.1.2.3.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest38() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-i", "-p", "456", "-d", "2", "-c", "2", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 59 (Key = 2.2.3.1.2.3.4.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest39() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-i", "-p", "456", "-d", "2", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 60 (Key = 2.2.3.1.2.4.3.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // Encode : -c <n>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest40() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-i", "-p", "456", "-d", "2", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 61 (Key = 2.2.3.1.2.4.4.2.0.1.0.0.)
        // Output to file : -o <output_file_name>
        // Case Insensitive : -i
        // Add prefix : -p <prefix>
        // input FILE : File exist
        // stderr : content
        // stdout : <n/a>
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest41() throws Exception {
                String input = "123ABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String[] args = { "-o", filename, "-i", "-p", "456", inputFile.getPath() };
                Main.main(args);

                assertTrue("output file content empty", getFileContent(filename).isEmpty());
                assertTrue("stderr output should be not empty", !errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));

        }

        // Test Case 67 (Key = 2.2.3.2.2.3.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest42() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "123123abcABC" + System.lineSeparator() + "123123abcABC" + System.lineSeparator()
                                + "123123abcABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-p", "123", "-d", "2", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 69 (Key = 2.2.3.2.2.4.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Add prefix : -p <prefix>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest43() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "123123abcABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-p", "123", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 71 (Key = 2.2.3.2.3.3.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest44() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "123abcABC" + System.lineSeparator() + "123abcABC" + System.lineSeparator()
                                + "123abcABC" + System.lineSeparator();
                String[] args = { "-o", filename, "-d", "2", inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 73 (Key = 2.2.3.2.3.4.4.2.0.0.0.1.)
        // Output to file : -o <output_file_name>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : <n/a>
        // edited OutFILE : content
        @Test
        public void exampleTest45() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);
                String filename = "aaa";
                String expected = "123abcABC" + System.lineSeparator();

                String[] args = { "-o", filename, inputFile.getPath() };
                Main.main(args);

                assertEquals("output file content matched", expected, getFileContent(filename));
                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertTrue("stdout output should be empty", outStream.toString().isEmpty());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
                if (Files.exists(Paths.get(filename)))
                        Files.delete(Paths.get(filename));
        }

        // Test Case 83 (Key = 2.3.2.2.2.3.4.2.0.0.1.0.)
        // Replace string : -r <old> <new>
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest46() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "XABCabcABC" + System.lineSeparator() + "XABCabcABC" + System.lineSeparator();

                String[] args = { "-r", "123", "ABC", "-p", "X", "-d", "1", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 85 (Key = 2.3.2.2.2.4.4.2.0.0.1.0.)
        // Replace string : -r <old> <new>
        // Add prefix : -p <prefix>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest47() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "XABCabcABC" + System.lineSeparator();

                String[] args = { "-r", "123", "ABC", "-p", "X", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 87 (Key = 2.3.2.2.3.3.4.2.0.0.1.0.)
        // Replace string : -r <old> <new>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest48() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "ABCabcABC" + System.lineSeparator() + "ABCabcABC" + System.lineSeparator();

                String[] args = { "-r", "123", "ABC", "-d", "1", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 89 (Key = 2.3.2.2.3.4.4.2.0.0.1.0.)
        // Replace string : -r <old> <new>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest49() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "ABCabcABC" + System.lineSeparator();

                String[] args = { "-r", "123", "ABC", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 99 (Key = 2.3.3.2.2.3.4.2.0.0.1.0.)
        // Add prefix : -p <prefix>
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest50() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123123abcABC" + System.lineSeparator() + "123123abcABC" + System.lineSeparator();

                String[] args = { "-p", "123", "-d", "1", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 101 (Key = 2.3.3.2.2.4.4.2.0.0.1.0.)
        // Add prefix : -p <prefix>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>

        @Test
        public void exampleTest51() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123123abcABC" + System.lineSeparator();

                String[] args = { "-p", "123", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

        // Test Case 103 (Key = 2.3.3.2.3.3.4.2.0.0.1.0.)
        // Dupicate N times : -d <n>
        // input FILE : File exist
        // stderr : <n/a>
        // stdout : content
        // edited OutFILE : <n/a>
        @Test
        public void exampleTest52() throws Exception {
                String input = "123abcABC" + System.lineSeparator();

                File inputFile = createInputFile(input);

                String expected = "123abcABC" + System.lineSeparator() + "123abcABC" + System.lineSeparator()
                                + "123abcABC" + System.lineSeparator();

                String[] args = { "-d", "2", inputFile.getPath() };
                Main.main(args);

                assertTrue("stderr output should be empty", errStream.toString().isEmpty());
                assertEquals("stdout output should be empty", expected, outStream.toString());
                assertEquals("input file content matched", input, getFileContent(inputFile.getPath()));
        }

    
}
