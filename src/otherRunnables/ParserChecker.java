package otherRunnables;

import myLang.Parser;
import myLang.Tokenizer;

import java.util.Scanner;

public class ParserChecker {

    /**
     * Run this to test out the parse (together with tokenizer).
     * The program will prompt you to enter an input string, which will be tokenized
     * and parsed. The result will be printed out. You can type ':quit'
     * to exit the program.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter an input string (or type ':quit' to quit): ");
            String inputstr = scanner.nextLine();

            if (inputstr.equalsIgnoreCase(":quit")) {
                break;
            }

            try {
                Parser p = new Parser(Tokenizer.tokenize(inputstr));
                System.out.println(p.getOutput());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
