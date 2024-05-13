package myLang.exception;

public class ParserException extends MyLangException {
    public ParserException(String message) {
        super("PE: " + message);
    }

    public ParserException(String message, int index) {
        super("PE: " + message + "\n index: " + index);
    }
}
