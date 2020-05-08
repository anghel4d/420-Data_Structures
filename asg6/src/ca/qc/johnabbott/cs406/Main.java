package ca.qc.johnabbott.cs406;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
	// write your code here
        ThreeMensMorris test = new ThreeMensMorris("● ●○○   ○".toCharArray());
        ThreeMensMorris test2 = test.copy();
        System.out.println(test.play(2, 1)); // doesnt play cause position is occupied
        System.out.println(test.play(3, 1));
        System.out.println(test.play(2, 3)); // doesn't run cause turns are used up

        System.out.println(test2.play(1, 2)); // Should run and cause win for white team.

        System.out.println(test.toString());
        System.out.println(test2.toString());
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial){
        List<ThreeMensMorris> list = new LinkedList<ThreeMensMorris>();
        list.add(initial);
        return list;
    }

    private static void generateHelper(ThreeMensMorris current, List<ThreeMensMorris> acc){

    }
}
