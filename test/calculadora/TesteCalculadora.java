package calculadora;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Testes unitários da classe {@link Calculadora}.
 *
 * <p>Cada teste exibe as <strong>entradas utilizadas</strong>, o <strong>resultado esperado</strong>
 * e o <strong>resultado obtido</strong>, informando se foi aprovado ou reprovado. Caso algum teste
 * falhe, o programa termina com código de saída {@code 1}.</p>
 *
 * <p>Execução: {@code java -cp out calculadora.TesteCalculadora}</p>
 *
 * @author Nome do aluno
 * @version 2.0
 */
public class TesteCalculadora {

    /** Quantidade de testes aprovados. */
    private static int aprovados = 0;

    /** Quantidade de testes reprovados. */
    private static int reprovados = 0;

    /**
     * Construtor privado: a classe é apenas um executável de testes e não deve ser instanciada.
     */
    private TesteCalculadora() {
    }

    /**
     * Executa toda a bateria de testes da {@link Calculadora}.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Calculadora calc = new Calculadora();

        System.out.println("=== TESTES UNITARIOS - CALCULADORA (versao refatorada) ===");

        System.out.println("\n--- Operacoes validas: calcular() ---");
        testarCalculo(calc, "Soma",                  2, 3, "+", 5);
        testarCalculo(calc, "Subtracao",            10, 4, "-", 6);
        testarCalculo(calc, "Multiplicacao",         3, 5, "*", 15);
        testarCalculo(calc, "Divisao",               8, 2, "/", 4);
        testarCalculo(calc, "Soma com negativos",   -2, -3, "+", -5);
        testarCalculo(calc, "Divisao inteira",       7, 2, "/", 3);

        System.out.println("\n--- Erros esperados: calcular() lanca excecao ---");
        testarExcecao(calc, "Divisao por zero",      8, 0, "/", "Divisão por zero não é permitida.");
        testarExcecao(calc, "Operacao invalida",     5, 5, "x", "Operador inválido: x");
        testarExcecao(calc, "Operador nulo",         5, 5, null, "Operador inválido: o operador não pode ser nulo.");

        System.out.println("\n--- Compatibilidade: calc() nunca interrompe a execucao ---");
        testarCalcSeguro(calc, "calc() soma",             2, 3, "+", 5);
        testarCalcSeguro(calc, "calc() divisao por zero", 8, 0, "/", 0);
        testarCalcSeguro(calc, "calc() operador invalido", 5, 5, "x", 0);
        testarCalcSeguro(calc, "calc() operador nulo",    5, 5, null, 0);

        System.out.println("\nAprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    /**
     * Testa uma operação válida executada por {@link Calculadora#calcular(int, int, String)}.
     *
     * @param calc     instância da calculadora
     * @param nome     descrição do teste
     * @param a        primeiro operando
     * @param b        segundo operando
     * @param operador operador da operação
     * @param esperado resultado esperado
     */
    private static void testarCalculo(Calculadora calc, String nome, int a, int b, String operador, int esperado) {
        String obtido;
        try {
            obtido = String.valueOf(calc.calcular(a, b, operador));
        } catch (RuntimeException e) {
            obtido = "excecao inesperada: " + e.getMessage();
        }
        registrar(nome, a, b, operador, String.valueOf(esperado), obtido);
    }

    /**
     * Testa se {@link Calculadora#calcular(int, int, String)} lança {@link IllegalArgumentException}
     * com a mensagem esperada.
     *
     * @param calc             instância da calculadora
     * @param nome             descrição do teste
     * @param a                primeiro operando
     * @param b                segundo operando
     * @param operador         operador da operação
     * @param mensagemEsperada mensagem esperada na exceção
     */
    private static void testarExcecao(Calculadora calc, String nome, int a, int b, String operador, String mensagemEsperada) {
        String esperado = "IllegalArgumentException(\"" + mensagemEsperada + "\")";
        String obtido;
        try {
            obtido = "sem excecao, resultado = " + calc.calcular(a, b, operador);
        } catch (IllegalArgumentException e) {
            obtido = "IllegalArgumentException(\"" + e.getMessage() + "\")";
        } catch (RuntimeException e) {
            obtido = "excecao inesperada: " + e;
        }
        registrar(nome, a, b, operador, esperado, obtido);
    }

    /**
     * Testa o método seguro {@link Calculadora#calc(int, int, String)}, garantindo que ele
     * retorna o valor esperado e não propaga exceções.
     *
     * @param calc     instância da calculadora
     * @param nome     descrição do teste
     * @param a        primeiro operando
     * @param b        segundo operando
     * @param op       operador da operação
     * @param esperado resultado esperado
     */
    private static void testarCalcSeguro(Calculadora calc, String nome, int a, int b, String op, int esperado) {
        PrintStream original = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream())); // silencia a saida interna
        String obtido;
        try {
            obtido = String.valueOf(calc.calc(a, b, op));
        } catch (RuntimeException e) {
            obtido = "excecao propagada: " + e;
        } finally {
            System.setOut(original);
        }
        registrar(nome, a, b, op, String.valueOf(esperado), obtido);
    }

    /**
     * Compara esperado e obtido, atualiza os contadores e imprime o relatório do teste.
     *
     * @param nome     descrição do teste
     * @param a        primeiro operando utilizado
     * @param b        segundo operando utilizado
     * @param op       operador utilizado
     * @param esperado resultado esperado
     * @param obtido   resultado obtido
     */
    private static void registrar(String nome, int a, int b, String op, String esperado, String obtido) {
        boolean ok = esperado.equals(obtido);
        if (ok) {
            aprovados++;
        } else {
            reprovados++;
        }

        System.out.println("[" + (ok ? "PASSOU" : "FALHOU") + "] " + nome);
        System.out.println("   Entradas : a=" + a + ", b=" + b + ", operador=" + (op == null ? "null" : "\"" + op + "\""));
        System.out.println("   Esperado : " + esperado);
        System.out.println("   Obtido   : " + obtido);
    }
}
