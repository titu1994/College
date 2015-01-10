package college.sem6.spacc.compiler.lexer;

import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Lexer { 

    /** The possible syntactic categories of the tokens returned by
     *  nextToken.  The arguments to the enumerals are the lexemes
     *  corresponding to the Category, when these are unique.  The
     *  Categories EOF and ERROR are artificial; they mark the end
     *  of the token stream and wrong tokens, respectively. */
    public static enum Category {
        GTEQ(">="), LTEQ("<="), GT(">"), LT("<"), ARROW("-->"),
        PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), ASSIGN("="),
        EQUALS("=="), LPAR ("("), RPAR (")"), SEMI(";"), COMMA(","), OR("||"), AND("&&"),
        IF("if"), DEF("def"), ELSE("else"), FI("fi"), WHILE("while"),RETURN("return"),
        IDENTITY(null), NUMERAL(null), EOF(null), ERROR (null);

        private final String lexeme;
        Category (String s) {
            lexeme = s;
        }
    }

    /** The lexeme read by the last call to nextToken.  Undefined after
     *  nextToken returns EOF or before nextToken is called.  Contains
     *  the erroneous character after nextToken returns ERROR. */
    public String lastLexeme;

    /** Mapping of lexemes represented by Categories with single
     *  members to those categories. */
    private static HashMap<String, Category> tokenMap = new HashMap<String, Category> ();
    static {
        for (Category c : Category.values ())
            tokenMap.put(c.lexeme, c);
    }

    /** Input source. */
    private Scanner inp;

    /** A pattern that always matches the next token or erroneous
     *  character, except at end of file.  Group 1, if present is
     *  whitespace, group 2 is an identifier, group 3 is a numeral. */
    private static final Pattern tokenPat = Pattern.compile ("(\\s+|#.*)" + //Group 0 Any String with empty spaces
                         "|>=|<=|-->|==|&&|if|def|else|fi|while|return" + //Group 1 Specific occurrences
                         "|([a-zA-Z][a-zA-Z0-9]*)" + //Group 2 Any Identifier name
                         "|(\\d+)" + //Group 3 Any Number
                         "|."); 

    /** A new Lexer taking input from READER. */		
    public Lexer (Reader reader) {
        inp = new Scanner (reader);
    }

    /** Read the next token, storing it in lastLexeme, and returning
     *  its Category.  Returns EOF at end of file, and ERROR for 
     *  wrong input (one character). */
    public Category nextToken () {
        if (inp.findWithinHorizon (tokenPat, 0) == null) //Matcher is applied here with the Target Pattern
            return Category.EOF;
        else {
            lastLexeme = inp.match ().group(0);
            
            if (inp.match().start(1) != -1)
                return nextToken();
            else if (inp.match().start(2) != -1) 
                return Category.IDENTITY;
            else if (inp.match().start(3) != -1)
                return Category.NUMERAL;
            
            Category result = tokenMap.get(lastLexeme);
            if (result == null)
                return Category.ERROR;
            else
                return result;
        }
    }
}