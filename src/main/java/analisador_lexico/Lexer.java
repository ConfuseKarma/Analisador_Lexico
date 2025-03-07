package analisador_lexico;

import java.util.ArrayList;
import java.util.List;

import scala.collection.concurrent.Map;
import scala.collection.mutable.HashMap;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int column = 1;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(Token.TokenType.EOF, "", line, column));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            // Delimitadores
            case '(': addToken(Token.TokenType.LPAREN); break;
            case ')': addToken(Token.TokenType.RPAREN); break;
            case '{': addToken(Token.TokenType.LBRACE); break;
            case '}': addToken(Token.TokenType.RBRACE); break;
            case ',': addToken(Token.TokenType.COMMA); break;
            case ';': addToken(Token.TokenType.SEMICOLON); break;

            // Operadores
            case '+': addToken(Token.TokenType.PLUS); break;
            case '-': addToken(Token.TokenType.MINUS); break;
            case '*': addToken(Token.TokenType.MULT); break;
            case '/': handleSlash(); break;
            case '=': handleEqual(); break;
            
            // Ignorar whitespace
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                column = 1;
                break;

            default:
                if (Character.isDigit(c)) {
                    number();
                } else if (Character.isLetter(c)) {
                    identifier();
                } else {
                    error("Caractere inesperado: '" + c + "'");
                }
                break;
        }
    }

    private void handleSlash() {
        if (match('/')) {
            // Comentário de linha
            while (peek() != '\n' && !isAtEnd()) advance();
        } else if (match('*')) {
            // Comentário de bloco
            blockComment();
        } else {
            addToken(Token.TokenType.DIV);
        }
    }

    private void blockComment() {
        int depth = 1;
        while (depth > 0 && !isAtEnd()) {
            if (peek() == '/' && peekNext() == '*') {
                advance();
                advance();
                depth++;
            } else if (peek() == '*' && peekNext() == '/') {
                advance();
                advance();
                depth--;
            } else {
                advance();
            }
        }
        if (depth > 0) {
            error("Comentário de bloco não fechado");
        }
    }

    private void handleEqual() {
        if (match('=')) {
            addToken(Token.TokenType.EQEQ);
        } else {
            addToken(Token.TokenType.EQ);
        }
    }

    private void number() {
        while (Character.isDigit(peek())) advance();

        // Verificar parte fracionária
        if (peek() == '.' && Character.isDigit(peekNext())) {
            advance();
            while (Character.isDigit(peek())) advance();
            addToken(Token.TokenType.FLOAT_LITERAL);
        } else {
            addToken(Token.TokenType.INT_LITERAL);
        }
    }

    private void identifier() {
        while (Character.isLetterOrDigit(peek())) advance();
        String text = source.substring(start, current);
        Token.TokenType type = keywords.getOrDefault(text, Token.TokenType.IDENT);
        addToken(type);
    }

    private static final Map<String, Token.TokenType> keywords = new HashMap<>();
    static {
        keywords.put("if", Token.TokenType.IF);
        keywords.put("else", Token.TokenType.ELSE);
        keywords.put("while", Token.TokenType.WHILE);
        keywords.put("for", Token.TokenType.FOR);
        keywords.put("fun", Token.TokenType.FUN);
        keywords.put("return", Token.TokenType.RETURN);
    }

    // Métodos auxiliares
    private char advance() {
        current++;
        column++;
        return source.charAt(current - 1);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        column++;
        return true;
    }

    private char peek() {
        return isAtEnd() ? '\0' : source.charAt(current);
    }

    private char peekNext() {
        return current + 1 >= source.length() ? '\0' : source.charAt(current + 1);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void addToken(Token.TokenType type) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, line, column - (current - start)));
    }

    private void error(String message) {
        String text = source.substring(start, current);
        tokens.add(new Token(Token.TokenType.ERROR, text, line, column - (current - start)));
        System.err.println("Erro léxico na linha " + line + ", coluna " + (column - (current - start)) + ": " + message);
    }
}