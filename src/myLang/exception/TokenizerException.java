package myLang.exception;

public class TokenizerException extends MyLangException {

    public TokenizerException(String message) {
        super("TE: " + message);
    }
}
