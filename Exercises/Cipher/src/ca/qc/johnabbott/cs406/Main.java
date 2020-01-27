package ca.qc.johnabbott.cs406;

public class Main {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final String CIPHER_ALPHABET = "datsrucebfghijklmnopqvwxyz";

    public static void main(String[] args) {
        System.out.println("test");

        String input = "hello, world";
        String result = "";

        for (int i = 0; i < input.length(); i++) {
            char x = input.charAt(i);
            System.out.println(x);

            if(ALPHABET.contains(String.valueOf(x))){
                for (int j = 0; j < ALPHABET.length(); j++) {
                    if(x == ALPHABET.charAt(j)){
                        System.out.println();
                        result += CIPHER_ALPHABET.charAt(j);
                        break;
                    }
                }
            }
            else{
                result += x;
            }
        }

        System.out.println(result);
    }
}
