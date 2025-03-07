package analisador_lexico;

/**
 * Processa comentários do código, incluindo:
 * - Comentários de linha (// ...)
 * - Comentários de bloco (/* ... * /)
 * - Aninhamento de comentários de bloco
 * - Validação de comentários não fechados
 */

public class CommentHandler {
    private final Lexer lexer;
    private final PositionTracker position;

    public CommentHandler(Lexer lexer, PositionTracker position) {
        this.lexer = lexer;
        this.position = position;
    }

    public void processBlockComment() {
        int nestingLevel = 1;
        // Implementação para comentários aninhados
    }
}