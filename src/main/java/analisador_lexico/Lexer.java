package analisador_lexico;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Analisador léxico principal. Responsável por:
 * - Varrer o código fonte caractere por caractere
 * - Identificar tokens usando regras léxicas
 * - Gerenciar estados (comentários, strings, etc)
 * - Gerar lista de tokens para o parser
 * - Reportar erros léxicos com localização
 */

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int column = 1;
    
    private enum State { DEFAULT, STRING, BLOCK_COMMENT }
    private Stack<State> states = new Stack<>();

    private static final Map<String, Token.TokenType> keywords = new HashMap<>();
    static {
        keywords.put("if", Token.TokenType.IF);
        keywords.put("else", Token.TokenType.ELSE);
        keywords.put("while", Token.TokenType.WHILE);
        keywords.put("for", Token.TokenType.FOR);
        keywords.put("fun", Token.TokenType.FUN);
        keywords.put("return", Token.TokenType.RETURN);
    }

    public Lexer(String source) {
        this.source = source;
        this.states.push(State.DEFAULT);
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
        if (states.peek() == State.BLOCK_COMMENT) {
            handleBlockComment();
            return;
        } else if (states.peek() == State.STRING) {
            handleString();
            return;
        }

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
            case '!': handleNotEqual(); break;
            case '<': handleLess(); break;
            case '>': handleGreater(); break;
            
            // Strings
            case '"': 
                states.push(State.STRING);
                start = current; // Ignora a aspa inicial
                break;

            // Controle de linha/coluna
            case '\n':
                line++;
                column = 1;
                break;
                
            case ' ':
            case '\r':
                break;
                
            case '\t':
                column += 3; // Tabs como 4 espaços
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

    private void handleBlockComment() {
        while (!isAtEnd()) {
            if (peek() == '*' && peekNext() == '/') {
                advance(); // Consome *
                advance(); // Consome /
                states.pop();
                return;
            }
            advance();
        }
        error("Comentário de bloco não fechado");
    }

    private void handleString() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\\') handleEscape();
            advance();
        }
        
        if (isAtEnd()) {
            error("String não fechada");
            return;
        }
        
        advance(); // Fecha as aspas
        addToken(Token.TokenType.STRING_LITERAL);
        states.pop();
    }

    private void handleEscape() {
        advance(); // Consome a \
        char c = peek();
        switch (c) {
            case 'n': case 't': case 'r': case '"': case '\\':
                advance();
                break;
            default:
                error("Sequência de escape inválida: \\" + c);
        }
    }

    private void handleSlash() {
        if (match('/')) {
            // Comentário de linha
            while (peek() != '\n' && !isAtEnd()) advance();
        } else if (match('*')) {
            states.push(State.BLOCK_COMMENT);
        } else {
            addToken(Token.TokenType.DIV);
        }
    }

    private void handleEqual() {
        addToken(match('=') ? Token.TokenType.EQEQ : Token.TokenType.EQ);
    }

    private void handleNotEqual() {
        addToken(match('=') ? Token.TokenType.NEQ : Token.TokenType.ERROR);
    }

    private void handleLess() {
        addToken(match('=') ? Token.TokenType.LTE : Token.TokenType.LT);
    }

    private void handleGreater() {
        addToken(match('=') ? Token.TokenType.GTE : Token.TokenType.GT);
    }

    private void number() {
        while (Character.isDigit(peek())) advance();

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

    // Métodos auxiliares
    private char advance() {
        if (isAtEnd()) return '\0';
        char c = source.charAt(current++);
        column++;
        return c;
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
        return (current + 1 >= source.length()) ? '\0' : source.charAt(current + 1);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void addToken(Token.TokenType type) {
        String text = source.substring(start, current);
        int tokenColumn = column - (current - start);
        tokens.add(new Token(type, text, line, tokenColumn));
    }

    private void error(String message) {
        String text = source.substring(start, current);
        int errorColumn = column - (current - start);
        tokens.add(new Token(Token.TokenType.ERROR, text, line, errorColumn));
        System.err.println("Erro léxico [Linha " + line + ", Coluna " + errorColumn + "]: " + message);
    }
}