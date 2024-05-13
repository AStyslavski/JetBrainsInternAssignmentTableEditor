import myLang.Parser;
import myLang.Tokenizer;

import java.util.Scanner;

public class ParserChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter an input string (or type 'exit' to quit): ");
            String inputstr = scanner.nextLine();

            if (inputstr.equalsIgnoreCase("exit")) {
                break;  // Exit the loop if user types "exit"
            }

            // Assuming Tokenizer.tokenize and Parser.parseList are implemented correctly.
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
