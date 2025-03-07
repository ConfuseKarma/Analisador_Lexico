# Especificação da Linguagem

## Visão Geral
Linguagem hipotética com sintaxe similar a C/JavaScript, desenvolvida para fins educacionais na disciplina de Compiladores.  
**Principais características**:
- Tipagem estática simples
- Funções como cidadãos de primeira classe
- Suporte a estruturas de controle clássicas

---

## Lista de Tokens

### Palavras Reservadas
| Token       | Exemplos      | Expressão Regular      |
|-------------|---------------|------------------------|
| `IF`        | `if`          | `if`                   |
| `ELSE`      | `else`        | `else`                 |
| `WHILE`     | `while`       | `while`                |
| `FOR`       | `for`         | `for`                  |
| `FUN`       | `fun`         | `fun`                  |
| `RETURN`    | `return`      | `return`               |

### Literais
| Token               | Exemplos          | Expressão Regular              |
|---------------------|-------------------|--------------------------------|
| `IDENT`             | `x`, `var1`       | `[a-zA-Z_][a-zA-Z0-9_]*`       |
| `INT_LITERAL`       | `123`, `42`       | `\d+`                          |
| `FLOAT_LITERAL`     | `3.14`, `0.5`     | `\d+\.\d+`                     |
| `STRING_LITERAL`    | `"Hello"`         | `"(\\.|[^"\\])*"`              |

### Operadores
| Token       | Símbolos         |
|-------------|------------------|
| `PLUS`      | `+`              |
| `MINUS`     | `-`              |
| `MULT`      | `*`              |
| `DIV`       | `/`              |
| `EQ`        | `=`              |
| `EQEQ`      | `==`             |
| `NEQ`       | `!=`             |
| `LT`        | `<`              |
| `LTE`       | `<=`             |
| `GT`        | `>`              |
| `GTE`       | `>=`             |

### Delimitadores
| Token       | Símbolos         |
|-------------|------------------|
| `LPAREN`    | `(`              |
| `RPAREN`    | `)`              |
| `LBRACE`    | `{`              |
| `RBRACE`    | `}`              |
| `COMMA`     | `,`              |
| `SEMICOLON` | `;`              |

---

## Regras Léxicas

### 1. Comentários
- **Linha única**: `// Comentário até o fim da linha`
- **Bloco**: `/* Comentário com múltiplas linhas */`  
  **Atenção**: Aninhamento não permitido

### 2. Strings
- Delimitadas por aspas duplas (`"`)
- Suportam sequências de escape:
  - `\n`: Nova linha
  - `\t`: Tabulação
  - `\"`: Aspas
  - `\\`: Barra invertida

### 3. Números
- **Inteiros**: Sequência de 1+ dígitos (`42`)
- **Flutuantes**: Dois grupos de dígitos separados por ponto (`3.14`)

### 4. Identificadores
- Começam com letra ou `_`
- Podem conter letras, dígitos e `_`
- Case-sensitive

### 5. Espaços em Branco
- Espaços, tabs (`\t`) e novas linhas (`\n`) são ignorados
- Servem como separadores entre tokens

---

## Tratamento de Erros
O analisador léxico detecta e reporta:
1. **Caracteres inválidos**:  
   Exemplo: `@`, `$` (quando não em strings)
2. **Strings não fechadas**:  
   Exemplo: `"Hello World`
3. **Comentários não finalizados**:  
   Exemplo: `/* Comentário sem fim`
4. **Números mal formados**:  
   Exemplo: `123.` (ponto sem dígitos após)

**Mecanismo de recuperação**:  
- Em caso de erro, o analisador:
  1. Reporta a posição exata (linha/coluna)
  2. Pula caracteres até encontrar um token válido
  3. Continua a análise

---

## Exemplos

### Código Válido
```js
fun factorial(n) {
  if (n == 0) {
    return 1;
  } else {
    return n * factorial(n - 1);
  }
}
```

### Saída de Tokens (Exemplo Parcial):
```
[FUN]       "fun"        @ 1:1
[IDENT]     "factorial"  @ 1:5
[LPAREN]    "("          @ 1:14
...
```

### Código com Erro
```js
var price = 12.#3; // Erro: número mal formado
```

### Report de Erro:
```
Erro léxico [Linha 1, Coluna 12]: Número mal formado
```

---

## Decisões de Projeto

1. **Prioridade de Tokens**:
   - Palavras reservadas têm prioridade sobre identificadores
   - `==` é reconhecido antes de `=`

2. **Regra "Longest Match"**:
   - Exemplo: `123.45` é reconhecido como um único token float

3. **Strings Multilinha**:
   - Não implementadas na versão atual

4. **Compatibilidade**:
   - Não suporta Unicode em identificadores
   - Números hexadecimais/binários não implementados

---

## Limitações Conhecidas
1. Não suporta interpolação em strings
2. Ausência de caracteres especiais (`?`, `~`, etc)
3. Tipos complexos (arrays, objetos) não implementados

---

## Ambiente de Desenvolvimento
- **Linguagem**: Java 11+
- **Build**: Maven
- **Testes**: JUnit 5