import myLang.Interpreter;
import myLang.Parser;
import myLang.Tokenizer;
import myLang.value.IntegerValue;
import myLang.value.Value;

import java.util.Scanner;

public class InterpreterChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter an input string (or type 'exit' to quit): ");
            String inputstr = scanner.nextLine();
            Value[][] cells = new Value[100][100];
            for(int i = 0; i < 100; i++) {
                for(int j = 0; j < 100; j++) {
                    cells[i][j] = new IntegerValue(i-j);
                }
            }


            if (inputstr.equalsIgnoreCase("exit")) {
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
