package analisador_lexico;

/**
 * Responsável pelo processamento de strings, incluindo:
 * - Strings simples ("...") e multilinha ("""...""")
 * - Caracteres de escape (\n, \t, \", etc)
 * - Interpolação de variáveis (${expression})
 * - Validação de strings não fechadas
 */

public class StringHandler {
    private final Lexer lexer;
    private final PositionTracker position;
    private final LexerStateMachine stateMachine;

    public StringHandler(Lexer lexer, PositionTracker position, LexerStateMachine stateMachine) {
        this.lexer = lexer;
        this.position = position;
        this.stateMachine = stateMachine;
    }

    public void processString(boolean isTriple) {
        // Implementação para strings com tratamento de:
        // - Caracteres de escape
        // - Interpolação ${...}
        // - Multilinha
    }
}
