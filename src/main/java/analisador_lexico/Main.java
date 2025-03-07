package analisador_lexico;

import java.util.List;

/**
 * Classe de teste e demonstração.
 * Permite executar o lexer com entradas de exemplo
 * e visualizar os tokens gerados.
 */

 public class Main {
    public static void main(String[] args) {
        String code = "if (x == 5) {\n" +
                      "  print(\"Hello\\nWorld\");\n" +
                      "}";
        
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.scanTokens();
        
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}