package analisador_lexico;

/**
 * Utilitários para verificação de caracteres.
 * Contém métodos auxiliares para:
 * - Identificar dígitos hexadecimais/binários
 * - Verificar letras e números
 * - Validar caracteres especiais
 */

public class LexerUtils {
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    public static boolean isHexDigit(char c) {
        return Character.isDigit(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    public static boolean isBinaryDigit(char c) {
        return c == '0' || c == '1';
    }
}