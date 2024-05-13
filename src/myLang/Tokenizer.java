package myLang;

import myLang.exception.TokenizerException;
import myLang.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.*;

public class Tokenizer {

    private static final Set<String> specialCharacters = Set.of("(", ")", ",", "+", "-", "*");

    public static List<Token> tokenize(String input) {
        return split(input).stream().map(Token::fromString).toList();
    }

    static List<String> split(String input) {
        StringBuilder patternBuilder = new StringBuilder();
        for (String ch : specialCharacters) {
            patternBuilder.append(Pattern.quote(ch));
        }
        String tokenPattern = "([a-zA-Z0-9]+|[" + patternBuilder + "])";
        if (!Pattern.compile("((" + tokenPattern + ")|\s)*").matcher(input).matches())
            throw new TokenizerException(" Invalid input");

        ArrayList<String> returnValue = new ArrayList<>();
        Matcher matcher = Pattern.compile(tokenPattern).matcher(input);
        while (matcher.find()) {
            returnValue.add(matcher.group());
        }
        return returnValue;
    }
}
