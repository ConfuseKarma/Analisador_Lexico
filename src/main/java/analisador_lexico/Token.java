package analisador_lexico;

import java.util.Objects;

/**
 * Representa um token léxico identificado pelo analisador.
 * Armazena o tipo, lexema e posição (linha/coluna) no código fonte.
 * 
 * Tipos de tokens incluem:
 * - Palavras reservadas (IF, ELSE, etc)
 * - Literais (números, strings)
 * - Operadores e delimitadores
 * - Marcadores especiais (EOF, ERROR)
 */

 public class Token {
    public enum TokenType {
        // Palavras reservadas
        IF, ELSE, WHILE, FOR, FUN, RETURN, TRUE, FALSE, NULL,
        // Literais
        IDENT, INT_LITERAL, FLOAT_LITERAL, STRING_LITERAL,
        // Operadores
        PLUS, MINUS, MULT, DIV, MOD, ASSIGN, EQ, NEQ, LT, GT, LTE, GTE,
        // Delimitadores
        LPAREN, RPAREN, LBRACE, RBRACE, COMMA, SEMICOLON, COLON,
        // Comentários e strings
        COMMENT,
        // Especiais
        INDENT, DEDENT, NEWLINE, EOF, ERROR
    }

    public final TokenType type;
    public final String lexeme;
    public final int line;
    public final int column;

    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("[%-7s] %-15s @ %d:%d", 
            type, "\"" + lexeme + "\"", line, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return line == token.line && column == token.column 
            && type == token.type && Objects.equals(lexeme, token.lexeme);
    }
}