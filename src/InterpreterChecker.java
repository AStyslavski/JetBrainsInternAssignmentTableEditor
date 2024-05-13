import myLang.Interpreter;
import myLang.Parser;
import myLang.Tokenizer;
import myLang.value.IntegerValue;
import myLang.value.Value;

import java.util.Scanner;

public class InterpreterChecker {

    /**
     * Run this to test out the interpreter (together with parser and tokenizer).
     * The program will prompt you to enter an input string, which will be tokenized,
     * parsed, and interpreted. The result will be printed out. You can type ':quit'
     * to exit the program.
     * All cells from A1 to CV100 are initialized with the integer 1.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Value[][] cells = new Value[100][100];
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                cells[i][j] = new IntegerValue(1);
            }
        }

        while (true) {
            System.out.println("Enter an input string (or type ':quit' to quit): ");
            String inputstr = scanner.nextLine();

            if (inputstr.equalsIgnoreCase(":quit")) {
                break;
            }

            try {
                Parser p = new Parser(Tokenizer.tokenize(inputstr));
                System.out.println(Interpreter.interpret(p.getOutput(), cells));
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
