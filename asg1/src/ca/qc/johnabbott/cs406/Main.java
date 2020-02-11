package ca.qc.johnabbott.cs406;

import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // (Testing)
        System.out.println("Creating test log...");
        try {
            Log testlog1 = new Log("10.32.43.55	http	2018-01-22 11:03:16.989	8773");
            Log testlog2 = new Log("10.32.43.54	ssh	2018-01-22 11:03:16.989	8773");
            System.out.println(testlog1.compareTo(testlog2));
        } catch (ParseException e) {
            e.getMessage();
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Done.");
    }

    // TODO: 1 - Figure out defaults for log1 and log2
    //       2 - Find a way to remove currentFile = x duplication
    public static void merge(String in1, String in2, String out) {
        // Keep track of the file currently being scanned or written to.
        String currentFile = in1;

        try (Scanner s1 = new Scanner(new File(in1));
             Scanner s2 = new Scanner(new File(in2));
             PrintWriter pw = new PrintWriter(out)) {

            // Wrap the scanners into isolated containers.
            WrappedScanner ws1 = new WrappedScanner(s1);
            WrappedScanner ws2 = new WrappedScanner(s2);

            // Create logs for comparison and printing
            currentFile = in1;
            Log log1 = new Log(ws1.getNextLine());
            currentFile = in2;
            Log log2 = new Log(ws2.getNextLine());

            // Loop continuously over lines of the input files, printing to output as we go.
            while (ws1.hasNextLine() || ws2.hasNextLine()) {
                if (!ws1.hasNextLine()) {
                    currentFile = out;
                    pw.println(ws2.getNextLine());
                } else if (!ws2.hasNextLine()) {
                    currentFile = out;
                    pw.println(ws1.getNextLine());
                } else {
                    if(log1.compareTo(log2) == 0) {
                        currentFile = out;
                        pw.println(log1);
                        pw.println(log2);

                        currentFile = in1;
                        log1 = new Log(ws1.getNextLine());
                        currentFile = in2;
                        log2 = new Log(ws2.getNextLine());
                    }
                    else if(log1.compareTo(log2) < 0) {
                        currentFile = out;
                        pw.println(log1);
                        currentFile = in1;
                        log1 = new Log(ws1.getNextLine());
                    } else {
                        currentFile = out;
                        pw.println(log2);
                        currentFile = in2;
                        log2 = new Log(ws2.getNextLine());
                    }
                }
            }
        } catch (Exception e) {
            int errorLine = 0; //TODO Give this a proper value
            String errorMessage = e.getMessage() + "\n" + "IO/Error on file [" + currentFile + "] on line [" + errorLine + "].";
            System.out.println(errorMessage);
        }
    }
}

// A simple wrapper for Java's Scanner, adding a counter "count" for the number of parsed lines.
class WrappedScanner {
    // Fields
    private Scanner scanner;
    private int count;

    // Constructor
    public WrappedScanner(Scanner scanner) {
        this.scanner = scanner;
        this.count = 0;
    }

    // Getters
    public String getNextLine() {
        this.count++;
        return this.scanner.nextLine();
    }

    public int getCount() {
        return this.count;
    }

    // Methods
    public boolean hasNextLine() {
        return this.scanner.hasNextLine();
    }
}




