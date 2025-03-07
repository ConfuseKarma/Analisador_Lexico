package analisador_lexico;

import java.util.Stack;

/**
 * Gerencia a máquina de estados do lexer.
 * Controla transições entre estados como:
 * - STRING: Dentro de uma string
 * - TRIPLE_STRING: String multilinha
 * - BLOCK_COMMENT: Comentário de bloco
 * - INTERPOLATION: Dentro de ${...}
 * 
 * Utiliza pilha para permitir aninhamento de estados
 */

public class LexerStateMachine {
    public enum State {
        DEFAULT,
        STRING,
        TRIPLE_STRING,
        INTERPOLATION,
        BLOCK_COMMENT
    }

    private Stack<State> states = new Stack<>();

    public LexerStateMachine() {
        states.push(State.DEFAULT);
    }

    public State currentState() {
        return states.peek();
    }

    public void pushState(State newState) {
        states.push(newState);
    }

    public void popState() {
        if (states.size() > 1) {
            states.pop();
        }
    }
}
