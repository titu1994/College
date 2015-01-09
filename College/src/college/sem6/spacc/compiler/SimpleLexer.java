package college.sem6.spacc.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimpleLexer {
    public static enum Type {
        // This Scheme-like language has three token types:
        // open parens, close parens, and an "atom" type
        LPAREN, RPAREN, ATOM;
    }
    public static class Token {
        public final Type t;
        public final String c; // contents mainly for atom tokens
        // could have column and line number fields too, for reporting errors later
        public Token(Type t, String c) {
            this.t = t;
            this.c = c;
        }
        public String toString() {
            if(t == Type.ATOM) {
                return "ATOM<" + c + ">";
            }
            return t.toString();
        }
    }

    /*
     * Given a String, and an index, get the atom starting at that index
     */
    public static String getAtom(String s, int i) {
        int j = i;
        for( ; j < s.length(); ) {
            if(Character.isLetter(s.charAt(j))) {
                j++;
            } else {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }

    public static List<Token> lex(String input) {
        List<Token> result = new ArrayList<Token>();
        for(int i = 0; i < input.length(); ) {
            switch(input.charAt(i)) {
            case '(':
                result.add(new Token(Type.LPAREN, "("));
                i++;
                break;
            case ')':
                result.add(new Token(Type.RPAREN, ")"));
                i++;
                break;
            default:
                if(Character.isWhitespace(input.charAt(i))) {
                    i++;
                } else {
                    String atom = getAtom(input, i);
                    i += atom.length();
                    result.add(new Token(Type.ATOM, atom));
                }
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
    	BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Enter some text : ");
    	String text = bb.readLine();
        List<Token> tokens = lex(text);
        for(Token t : tokens) {
            System.out.println(t);
        }
    }
}