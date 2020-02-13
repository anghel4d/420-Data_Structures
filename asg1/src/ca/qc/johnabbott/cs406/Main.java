package ca.qc.johnabbott.cs406;

import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String input1 = "data/test-full/in1.txt";
        final String input2 = "data/test-full/in2.txt";
        final String output = "data/test-full/out.txt";

        merge(input1, input2, output);
    }

    /**
     * Merge two input files containing Log objects into a sorted output file.
     * I was going use functions with pointers to avoid code duplication but I couldn't due to Java's limit on passing by reference.
     * OOP or clever restructuring would do the trick but at this stage I am not down for that.
     * So sorry about code duplication :[
     * @param in1
     * @param in2
     * @param out
     */
    public static void merge(String in1, String in2, String out){
        // Keep track of line numbers and active file
        String currentFile = in1;
        int scanner1Line = 0;
        int scanner2Line = 0;
        int writerLine = 0;

        // Declare IO objects here so that they will be dumped when they
        // fall out of scope of the try-catch block.
        try(Scanner scanner1 = new Scanner(new File(in1));
            Scanner scanner2 = new Scanner(new File(in2));
            PrintWriter printWriter = new PrintWriter(new File(out));){

            // Creating empty Log objects to populate output with when lines are scanned.
            Log log1 = new Log();
            Log log2 = new Log();

            // Set default log values on first lines of each input stream.
            if(scanner1.hasNextLine())
                log1 = new Log(scanner1.nextLine());
            if (scanner2.hasNextLine())
                log2 = new Log(scanner2.nextLine());

            // Loop over all input lines for as long as there is at least one line left in either input stream.
            while (scanner1.hasNextLine() || scanner2.hasNextLine()) {
                // If one of the input streams is exhausted, dump the remainder of the other one.
                if (!scanner1.hasNextLine()) {
                    currentFile = out;
                    writerLine++;
                    scanner2Line++;
                    printWriter.println(new Log(scanner2.nextLine()));
                } else if (!scanner2.hasNextLine()) {
                    currentFile = out;
                    writerLine++;
                    scanner1Line++;
                    printWriter.println(new Log(scanner1.nextLine()));
                }
                // If both input streams have lines remaining, perform comparison operations and merging.
                else {
                    // Print both at once if lines are equivalent.
                    if(log1.compareTo(log2) == 0) {
                        currentFile = out;
                        writerLine += 2;
                        printWriter.println(log1);
                        printWriter.println(log2);

                        currentFile = in1;
                        scanner1Line++;
                        log1 = new Log(scanner1.nextLine());

                        currentFile = in2;
                        scanner2Line++;
                        log2 = new Log(scanner2.nextLine());
                    }
                    // Print and read next line if file1 line is greater than file2 line, reverse if opposite situation.
                    else if(log1.compareTo(log2) < 0) {
                        currentFile = out;
                        writerLine++;
                        printWriter.println(log1);

                        currentFile = in1;
                        scanner1Line++;
                        log1 = new Log(scanner1.nextLine());
                    } else {
                        currentFile = out;
                        writerLine++;
                        printWriter.println(log2);

                        currentFile = in2;
                        scanner2Line++;
                        log2 = new Log(scanner2.nextLine());
                    }

                    if(!scanner1.hasNextLine() || !scanner2.hasNextLine()){
                        currentFile = out;
                        writerLine += 2;
                        printWriter.println(log1);
                        printWriter.println(log2);
                    }
                }
            }
        }
        catch (Exception e){
            int errLine = 0;
            if( currentFile == in1){
                errLine = scanner1Line;
            }
            else if (currentFile == in2){
                errLine = scanner2Line;
            }
            else{
                errLine = writerLine;
            }

            System.err.println(e.getMessage());
            System.err.println("Error on line [" + errLine + "] of file [" + currentFile + "]");
        }

    }
}

