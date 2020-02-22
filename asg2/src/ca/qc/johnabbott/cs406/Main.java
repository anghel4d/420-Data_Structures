package ca.qc.johnabbott.cs406;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        String elements[] = {"A", "B", "C", "D"};
        Set set1 = new HashSet(Arrays.asList(elements));
        //throw new RuntimeException("Run tests not main()!");

        elements = new String[] { "A", "B", "C" };
        Set set2 = new HashSet(Arrays.asList(elements));

        System.out.println(set1.containsAll(set2));

        System.out.println("hello");
    }
}
