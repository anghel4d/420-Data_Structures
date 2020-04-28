package com.company;
import javax.print.DocFlavor;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.print("\nn value: ");
            System.out.println(factorial(BigInteger.valueOf(s.nextInt())));
        }
        */
        for(int i = 1; i <= 20000; i++){
            System.out.print("\u001B[36m\"" + i + "\u001B[0m" + ": ");
            System.out.println(factorial(BigInteger.valueOf(i)));
        }
    }

    private static BigInteger factorial(BigInteger n){
        if(n.compareTo(BigInteger.ONE) <= 0){
            return BigInteger.ONE;
        }

        return n.multiply(factorial(new BigInteger(String.valueOf(n.subtract(BigInteger.ONE)))));
    }
}
