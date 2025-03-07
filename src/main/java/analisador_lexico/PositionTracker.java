package analisador_lexico;

/**
 * Rastreia precisamente a posição atual no código fonte.
 * Calcula linha, coluna e offset levando em consideração:
 * - Caracteres de nova linha (\n)
 * - Tabuladores (\t) como 4 espaços
 * - Avanço padrão de caracteres
 */

public class PositionTracker {
    private int line = 1;
    private int column = 1;
    private int offset = 0;
    
    public void advance(char c) {
        if (c == '\n') {
            line++;
            column = 1;
        } else if (c == '\t') {
            column += 4 - (column % 4); // Tabs como 4 espaços
        } else {
            column++;
        }
        offset++;
    }
    
    // Getters
    public int getLine() { return line; }
    public int getColumn() { return column; }
    public int getOffset() { return offset; }
}
