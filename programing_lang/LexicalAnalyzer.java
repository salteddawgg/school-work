import java.io.*;

public class LexicalAnalyzer {
    // Global declarations
    private static int charClass;
    private static char[] lexeme = new char[100];
    private static char nextChar;
    private static int lexLen;
    private static int token;
    private static int nextToken;
    private static BufferedReader in_fp;

    // Character classes
    private static final int LETTER = 0;
    private static final int DIGIT = 1;
    private static final int UNKNOWN = 99;

    // Token codes
    private static final int INT_LIT = 10;
    private static final int IDENT = 11;
    private static final int ASSIGN_OP = 20;
    private static final int ADD_OP = 21;
    private static final int SUB_OP = 22;
    private static final int MULT_OP = 23;
    private static final int DIV_OP = 24;
    private static final int LEFT_PAREN = 25;
    private static final int RIGHT_PAREN = 26;
    private static final int EOF = -1;

    public static void main(String[] args) {
        try {
            // Open the input data file or read from command-line
            File file = new File("front.in");
            if (!file.exists()) {
                System.out.println("ERROR - cannot open front.in");
                return;
            }
            in_fp = new BufferedReader(new FileReader(file));
            getChar();
            do {
                lex();
            } while (nextToken != EOF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lookup operators and parentheses and return the token
    private static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    // Add nextChar to lexeme
    private static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
        } else {
            System.out.println("lexeme is too long");
        }
    }

    // Get the next character of input and determine its character class
    private static void getChar() {
        try {
            int charInt = in_fp.read();
            if (charInt != -1) {
                nextChar = (char) charInt;
                if (Character.isLetter(nextChar)) {
                    charClass = LETTER;
                } else if (Character.isDigit(nextChar)) {
                    charClass = DIGIT;
                } else {
                    charClass = UNKNOWN;
                }
            } else {
                charClass = EOF;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Call getChar until it returns a non-whitespace character
    private static void getNonBlank() {
        while (Character.isWhitespace(nextChar)) {
            getChar();
        }
    }

    // lexical analyzer for arithmetic expressions
    private static int lex() {
        lexLen = 0; // Reset lexeme length
        getNonBlank();
        switch (charClass) {
            // Parse identifiers
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;

            // Parse integer literals
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            // Parentheses and operators
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            // EOF
            case EOF:
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexLen = 3;
                break;
        } // End of switch
        System.out.println("Next token is: " + nextToken + ", Next lexeme is " + new String(lexeme, 0, lexLen));
        return nextToken;
    }
}