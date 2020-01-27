package ca.qc.johnabbott.cs406;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Started...");
        try {
            int charsCopied = copyTxtFile("data/in.txt", "data/out.txt");
            System.out.println(charsCopied);;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done...");
    }

    public static double average(String inputFile) throws FileNotFoundException {
        FileReader reader = new FileReader(inputFile);
        Scanner scanner = new Scanner(reader);

        double sum = 0.0;
        int count = 0;
        while(scanner.hasNextDouble()){
            sum += scanner.nextDouble();
            count++;
        }
        scanner.close();
        return sum / count;
    }

    private static int copyTxtFile(String source, String target) throws IOException {
        FileReader reader = new FileReader(source);
        FileWriter writer = new FileWriter(target);

        int currentChar = reader.read();
        int count = 0;
        while(currentChar != -1){
            writer.write(currentChar);
            count++;
            currentChar = reader.read();
        }

        reader.close();
        writer.close();

        return count;
    }
}
