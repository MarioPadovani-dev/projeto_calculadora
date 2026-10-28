package calculadora;

/**
 * <p><strong>Projeto Calculadora</strong> - módulo de cálculos do FinançApp.</p>
 *
 * <p>Esta classe fornece as quatro operações matemáticas básicas (soma, subtração,
 * multiplicação e divisão) e um método principal que seleciona a operação desejada
 * a partir de um operador informado pelo usuário.</p>
 *
 * <p>Ela é utilizada para demonstrar:</p>
 * <ul>
 *     <li>testes unitários funcionais;</li>
 *     <li>tratamento de erros (divisão por zero e operador inválido);</li>
 *     <li>refatoração de código Java (extração de métodos e simplificação de condicionais);</li>
 *     <li>documentação técnica com JavaDoc.</li>
 * </ul>
 *
 * <p>Após a refatoração, os métodos {@link #somar}, {@link #subtrair}, {@link #multiplicar},
 * {@link #dividir} e {@link #calcular} são <strong>métodos puros</strong>: não possuem
 * efeitos colaterais, o que facilita a leitura e os testes unitários. O método
 * {@link #calc(int, int, String)} foi mantido por compatibilidade e preserva o
 * comportamento original (nunca interrompe a execução do programa).</p>
 *
 * @author Nome do aluno
 * @version 2.0
 */
public class Calculadora {

    /** Mensagem exibida quando ocorre tentativa de divisão por zero. */
    private static final String MSG_DIVISAO_POR_ZERO = "Divisão por zero não é permitida.";

    /** Mensagem exibida quando o operador informado é nulo. */
    private static final String MSG_OPERADOR_NULO = "Operador inválido: o operador não pode ser nulo.";

    /** Prefixo da mensagem exibida quando o operador informado não é suportado. */
    private static final String MSG_OPERADOR_INVALIDO = "Operador inválido: ";

    /**
     * Resultado da última operação executada por {@link #calc(int, int, String)}.
     * Vale {@code 0} enquanto nenhuma operação tiver sido realizada ou quando a
     * última operação foi inválida.
     */
    public int r = 0;

    /**
     * Cria uma nova calculadora com o resultado inicial ({@link #r}) igual a {@code 0}.
     */
    public Calculadora() {
        // Nenhuma inicialização adicional é necessária.
    }

    /**
     * Soma dois números inteiros.
     *
     * @param a primeiro operando
     * @param b segundo operando
     * @return o resultado da soma de {@code a} e {@code b}
     */
    public int somar(int a, int b) {
        return a + b;
    }

    /**
     * Subtrai dois números inteiros.
     *
     * @param a minuendo (valor do qual se subtrai)
     * @param b subtraendo (valor a ser subtraído)
     * @return o resultado de {@code a - b}
     */
    public int subtrair(int a, int b) {
        return a - b;
    }

    /**
     * Multiplica dois números inteiros.
     *
     * @param a primeiro fator
     * @param b segundo fator
     * @return o resultado de {@code a * b}
     */
    public int multiplicar(int a, int b) {
        return a * b;
    }

    /**
     * Divide dois números inteiros (divisão inteira, sem casas decimais).
     *
     * @param a dividendo
     * @param b divisor (não pode ser zero)
     * @return o quociente inteiro de {@code a / b}
     * @throws IllegalArgumentException se {@code b} for igual a zero
     */
    public int dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException(MSG_DIVISAO_POR_ZERO);
        }
        return a / b;
    }

    /**
     * Executa a operação matemática solicitada com base no operador informado.
     *
     * <p>Operadores aceitos:</p>
     * <ul>
     *     <li>{@code "+"}: soma;</li>
     *     <li>{@code "-"}: subtração;</li>
     *     <li>{@code "*"}: multiplicação;</li>
     *     <li>{@code "/"}: divisão.</li>
     * </ul>
     *
     * <p>Qualquer outro operador resulta em exceção.</p>
     *
     * @param a        primeiro operando
     * @param b        segundo operando
     * @param operador símbolo da operação desejada
     * @return o resultado da operação correspondente ao operador informado
     * @throws IllegalArgumentException se o operador for nulo ou não for um símbolo válido,
     *                                  ou se for solicitada uma divisão por zero
     */
    public int calcular(int a, int b, String operador) {
        if (operador == null) {
            throw new IllegalArgumentException(MSG_OPERADOR_NULO);
        }

        return switch (operador) {
            case "+" -> somar(a, b);
            case "-" -> subtrair(a, b);
            case "*" -> multiplicar(a, b);
            case "/" -> dividir(a, b);
            default -> throw new IllegalArgumentException(MSG_OPERADOR_INVALIDO + operador);
        };
    }

    /**
     * Método principal de uso seguro, mantido por compatibilidade com a versão inicial.
     *
     * <p>Delega o cálculo para {@link #calcular(int, int, String)}. Em caso de erro
     * (divisão por zero ou operador inválido), exibe uma mensagem informativa no console e
     * retorna {@code 0}, <strong>sem interromper a execução</strong> do programa.
     * O resultado fica armazenado no atributo {@link #r} e também é exibido no console.</p>
     *
     * @param a  primeiro operando
     * @param b  segundo operando
     * @param op símbolo da operação desejada ({@code +}, {@code -}, {@code *} ou {@code /})
     * @return o resultado da operação, ou {@code 0} se a operação for inválida
     */
    public int calc(int a, int b, String op) {
        try {
            r = calcular(a, b, op);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            r = 0;
        }

        System.out.println("Resultado = " + r);
        return r;
    }
}
