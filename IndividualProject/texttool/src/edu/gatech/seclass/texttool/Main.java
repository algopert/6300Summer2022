package edu.gatech.seclass.texttool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Main {
    // Empty Main class for compiling Individual Project.
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement
    // it
    // -f
    private static Boolean bEditFileinPlace = false;

    // -o <output_file_name>
    private static Boolean bOutput = false;
    private static String sOutputFileName = "";

    // -r <old> <new>
    private static Boolean bReplace = false;
    private static String sReplaceOld = "", sReplaceNew = "";

    // -i
    private static Boolean bCaseInsensitive = false;

    // -p <prefix>
    private static Boolean bAddPrefix = false;
    private static String sPrefix = "";

    // -d <n>
    private static Boolean bDuplicate = false;
    private static Integer nDuplicate = 0;

    // -c <n>
    private static Boolean bCipher = false;
    private static Integer nCipher = 0;

    private static String fileName = "";
    private static String fileContent = null;

    private static Boolean parseArgs(String[] args) {

        int argsLength = 0;

        int i = 0;

        argsLength = args.length;
        if (argsLength <= 0) {
            usage();
            return false;
        }

        for (i = 0; i < argsLength - 1; i++) {
            switch (args[i]) {
                case "-f":
                    bEditFileinPlace = true;
                    break;
                case "-o":
                    bOutput = true;

                    sOutputFileName = args[++i];
                    break;
                case "-r":
                    bReplace = true;

                    sReplaceOld = args[++i];
                    sReplaceNew = args[++i];
                    break;
                case "-i":

                    bCaseInsensitive = true;
                    break;
                case "-p":
                    bAddPrefix = true;

                    sPrefix = args[++i];
                    break;
                case "-d":
                    bDuplicate = true;

                    nDuplicate = Integer.parseInt(args[++i]);
                    break;
                case "-c":
                    bCipher = true;
                    // if (!validationParams(args, i, 1))
                    // return false;
                    nCipher = Integer.parseInt(args[++i]);
                    break;
            }
        }

        if (i != argsLength - 1) {
            usage();
            return false;
        }
        fileName = args[argsLength - 1];

        if (bEditFileinPlace && bOutput) {
            usage();
            return false;
        }

        if (bReplace && bCipher) {
            usage();
            return false;
        }

        if (bDuplicate && (nDuplicate < 1 || nDuplicate > 10)) {
            usage();
            return false;
        }

        if (bCipher && (nCipher < -25 || nCipher > 25)) {
            usage();
            return false;
        }

        if (!bReplace && bCaseInsensitive) {
            usage();
            return false;
        }
        // System.out.println("ok");

        return true;

    }

    private static Boolean getFileContent() {
        try {
            fileContent = Files.readString(Paths.get(fileName), StandardCharsets.UTF_8);
            return true;

        } catch (IOException e) {
            usage();
            return false;
        }
       
    }

    private static String encryptData(String inputStr, int shiftKey, String ALPHABET) {
        // encryptStr to store encrypted data
        String encryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {
            // get position of each character of inputStr in ALPHABET
            int pos = ALPHABET.indexOf(inputStr.charAt(i));
            if (pos < 0) {
                encryptStr += inputStr.charAt(i);
                continue;
            }

            // get encrypted char for each char of inputStr
            int encryptPos = (shiftKey + pos + 26) % 26;
            char encryptChar = ALPHABET.charAt(encryptPos);

            // add encrypted char to encrypted string
            encryptStr += encryptChar;
        }

        // return encrypted string
        // encryptStr = encryptStr.substring(0, encryptStr.length() - 1);
        return encryptStr;
    }

    private static void textPrcess() {
        int index = -1;

        if (bReplace) {
            String[] lines = fileContent.split("\\R");
            for (int i = 0; i < lines.length; i++) {
                if (bCaseInsensitive)
                    index = lines[i].toLowerCase().indexOf(sReplaceOld.toLowerCase());
                else
                    index = lines[i].indexOf(sReplaceOld);

                if (index >= 0) {
                    lines[i] = lines[i].substring(0, index) + sReplaceNew
                            + lines[i].substring(index + sReplaceOld.length());
                }
            }
            fileContent = String.join(System.lineSeparator(), lines);
            fileContent += System.lineSeparator();
        }

        if (bAddPrefix) {
            String[] lines = fileContent.split("\\R");
            for (int i = 0; i < lines.length; i++) {
                lines[i] = sPrefix + lines[i];
                // System.out.printf("%s", fileContent);
            }
            fileContent = String.join(System.lineSeparator(), lines);
            fileContent += System.lineSeparator();
        }
        if (bCipher) {
            // System.out.printf("qqq %s", fileContent);
            fileContent = encryptData(fileContent, nCipher, "abcdefghijklmnopqrstuvwxyz");
            // System.out.printf("%s", fileContent);
            fileContent = encryptData(fileContent, nCipher, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            // System.out.printf("%s", fileContent);

        }

        if (bDuplicate) {

            String[] lines = fileContent.split("\\R"); // System.lineSeparator()
            // System.out.println(lines.length);
            fileContent = "";
            for (int i = 0; i < lines.length; i++) {
                for (int j = 0; j <= nDuplicate; j++) {
                    fileContent += lines[i] + System.lineSeparator();
                }
            }
        }

    }

    public static void main(String[] args) {
        bEditFileinPlace = false;
        bOutput = false;
        sOutputFileName = "";
        bReplace = false;
        sReplaceOld = "";
        sReplaceNew = "";
        bCaseInsensitive = false;
        bAddPrefix = false;
        sPrefix = "";
        bDuplicate = false;
        nDuplicate = 0;
        bCipher = false;
        nCipher = 0;
        fileName = "";
        fileContent = null;

        try {
            
            if (!parseArgs(args)) {
                return;
            }
            
            if(!getFileContent())
                return;
            
            // System.out.println(fileContent);
            textPrcess();

            if (bEditFileinPlace) {
                try {
                    Files.writeString(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);
                } catch (Exception e) {
                    usage();
                }
            } else if (bOutput) {
                if (Files.exists(Paths.get(sOutputFileName))) {
                    Files.delete(Paths.get(sOutputFileName));
                    usage();
                    return;
                }
                try {
                    Files.writeString(Paths.get(sOutputFileName), fileContent, StandardCharsets.UTF_8);
                } catch (Exception e) {
                    usage();
                }

            } else {

                System.out.print(fileContent);
            }
        } catch (Exception e) {
            usage();
        }
    }

    private static void usage() {
        System.err.println(
                "Usage: texttool [ -f | -o output_file_name | -i | -r old new | -p prefix | -c n | -d n ] FILE");

    }
}