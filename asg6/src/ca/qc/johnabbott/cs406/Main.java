package ca.qc.johnabbott.cs406;
import java.util.*;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
	// write your code here
        ThreeMensMorris board = new ThreeMensMorris();
        board.play(1, 1);
        board.play(2, 2);
        board.play(1, 3);
        board.play(3, 1);
        board.play(3, 2);
        ThreeMensMorris board2 = board.copy();
        board.play(1, 2);
        ThreeMensMorris board3 = new ThreeMensMorris("  ●○     ".toCharArray());
        System.out.println(board.toString());
        System.out.println(board2.toString());
        System.out.println(board3.toString());


        List<ThreeMensMorris> testResult = generate(board3);
        System.out.println(testResult.toString());
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial){
        List<ThreeMensMorris> list = new LinkedList<ThreeMensMorris>();
        generateHelper(initial, list);
        Set<ThreeMensMorris> tmp = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(tmp);
        return list;
    }

    // A recursive method generating every possible endstate for ThreeMensMorris given a starter.
    private static void generateHelper(ThreeMensMorris current, List<ThreeMensMorris> acc){
        boolean justPlayed;
        for(int i = 1; i <= current.TABLESIZE; i++){
            for (int j = 1; j <= current.TABLESIZE; j++){
                ThreeMensMorris cp = current.copy();
                justPlayed = cp.play(i, j); // prime the loop
                while(justPlayed){
                    justPlayed = cp.play(i, j); // update within loop
                    generateHelper(cp, acc);
                }
            }
        }

        if(current.gameOver())
            acc.add(current);

        return;
    }
}
