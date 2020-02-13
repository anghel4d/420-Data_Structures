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

    public static void merge(String in1, String in2, String out){
        // Declare IO Objects
        Scanner scanner1;
        Scanner scanner2;
        PrintWriter printWriter;

        // Keep track of line numbers and active file
        String currentFile = in1;
        int scanner1Line = 0;
        int scanner2Line = 0;
        int writerLine = 0;

        

    }
}

