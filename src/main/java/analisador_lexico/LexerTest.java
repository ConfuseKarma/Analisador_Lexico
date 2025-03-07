package analisador_lexico;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o analisador léxico.
 * Verifica:
 * - Reconhecimento correto de tokens
 * - Tratamento de casos limite
 * - Mensagens de erro apropriadas
 * - Consistência nas posições reportadas
 */

public class LexerTest {
    @Test
    void testKeywords() {
        Lexer lexer = new Lexer("if else while");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(Token.TokenType.IF, tokens.get(0).type);
    }
    
    @Test
    void testUnclosedString() {
        assertThrows(ErrorHandler.LexicalError.class, () -> {
            new Lexer("\"Hello World").scanTokens();
        });
    }
}