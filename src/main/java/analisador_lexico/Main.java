package analisador_lexico;

public class Main {
    public static void main(String[] args) {
        String input = "if (x == 42) {\n" +
                       "    print(\"Hello World\");\n" +
                       "}";
        
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.scanTokens();
        
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}