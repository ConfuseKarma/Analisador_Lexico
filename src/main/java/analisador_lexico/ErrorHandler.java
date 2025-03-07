package analisador_lexico;

/**
 * Centraliza o tratamento de erros léxicos.
 * Gera mensagens de erro padronizadas com:
 * - Descrição do erro
 * - Localização (linha/coluna)
 * - Tipo de exceção específico para erros léxicos
 */

public class ErrorHandler {
    public static class LexicalError extends RuntimeException {
        public final int line;
        public final int column;

        public LexicalError(String message, int line, int column) {
            super(message);
            this.line = line;
            this.column = column;
        }
    }

    public static void report(String message, int line, int column) {
        throw new LexicalError("Erro Léxico [" + line + ":" + column + "]: " + message, line, column);
    }
}