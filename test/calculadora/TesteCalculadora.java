package calculadora;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TesteCalculadora {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {
        Calculadora calc = new Calculadora();

        System.out.println("=== TESTES UNITARIOS - CALCULADORA (versao inicial) ===\n");

        testar(calc, "Soma",                 2, 3, "+", 5);
        testar(calc, "Subtracao",           10, 4, "-", 6);
        testar(calc, "Multiplicacao",        3, 5, "*", 15);
        testar(calc, "Divisao",              8, 2, "/", 4);
        testar(calc, "Divisao por zero",     8, 0, "/", 0);
        testar(calc, "Operacao invalida",    5, 5, "x", 0);

        System.out.println("\nAprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void testar(Calculadora calc, String nome, int a, int b, String op, int esperado) {
        // Silencia a saida interna da calculadora para exibir apenas o relatorio do teste
        PrintStream original = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        int obtido;
        try {
            obtido = calc.calc(a, b, op);
        } finally {
            System.setOut(original);
        }

        boolean ok = (obtido == esperado);
        if (ok) aprovados++; else reprovados++;

        System.out.println("[" + (ok ? "PASSOU" : "FALHOU") + "] " + nome);
        System.out.println("   Entradas : a=" + a + ", b=" + b + ", op=\"" + op + "\"");
        System.out.println("   Esperado : " + esperado);
        System.out.println("   Obtido   : " + obtido);
    }
}
