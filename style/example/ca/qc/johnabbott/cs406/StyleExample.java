package ca.qc.johnabbott.cs406;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class StyleExample {

    public static final int PASSING_GRADE = 60;

    public static void main(String[] args) throws FileNotFoundException {

        FileReader reader = new FileReader("grades.txt");
        Scanner scanner = new Scanner(reader);

        int numberOfStudents = scanner.nextInt();
        // Advance Scanner to the next Student Record uwu
        scanner.nextLine();

        Grade[] arr = new Grade[numberOfStudents];
        int i = 0;
        while(scanner.hasNext()) {
            String currentLine;
            Grade grade;
            currentLine= scanner.nextLine();
            grade = new Grade(currentLine);
            if(grade.getGrade() < PASSING_GRADE)
                System.out.println(grade);
            arr[i++] = grade;
        }

        if(i != numberOfStudents)
            return;

        double s2 = 0;
        for(Grade i2 : arr)
            s2 += i2.getGrade();

        double a = s2 / numberOfStudents;

        Arrays.sort(arr);

        double m;
        if(numberOfStudents % 2 == 0)
            m = (arr[numberOfStudents / 2].getGrade() + arr[numberOfStudents / 2 - 1].getGrade()) / 2;
        else
            m = arr[numberOfStudents / 2].getGrade();

        System.out.println(a);
        System.out.println(m);
    }

}
