package myLang.exception;

public class InterpreterException extends MyLangException {
    public InterpreterException(String message) {
        super("IE: " + message);
    }
}
