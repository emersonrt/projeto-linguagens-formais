import model.AutomatoFinito;
import service.AutomatoDigestService;
import enums.ComandoEnum;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        AutomatoDigestService service = new AutomatoDigestService();
        Scanner scan = new Scanner(System.in);

        List<String> texto = cargaInicial();
        texto = TextUtils.removeWhiteSpacesArray(texto);

        AutomatoFinito af = service.lerAutomatoFinito(texto);

        Boolean execucao = true;
        do {
            System.out.println("\n-------------------- Linguagens Formais - Menu -------------------------------------------");
            System.out.println("0 - Encerrar execução");
            System.out.println("1 - Exibir AF");
            System.out.println("2 - Remover transições vazias");
            System.out.println("3 - Determinizar AF");
            System.out.println("4 - Minimizar AF");
            Integer comando = scan.nextInt();
            System.out.println("------------------------------------------------------------------------------------------");

            execucao = executarComando(comando, af);
        } while (execucao);

        scan.close();
    }

    private static Boolean executarComando(Integer comandoCodigo, AutomatoFinito af) {
        List<ComandoEnum> comandoList = Arrays.asList(ComandoEnum.values());
        ComandoEnum comando = comandoList.stream()
                .filter(comandoEnum -> comandoEnum.getCodigo().equals(comandoCodigo))
                .findFirst()
                .orElse(ComandoEnum.ENCERRAR);
        return comando.getFuncao().executar(af);
    }

    private static List<String> cargaInicial() {
        List<String> texto = new ArrayList<>();
//        texto.add("M = (Q, Σ, δ, E, F)");
//        texto.add("Q = {S,A,B,X}");
//        texto.add("Σ = {a, b}");
//        texto.add("E = q0");
//        texto.add("F = {X}");
//        texto.add("δ(S,a) = {A}");
//        texto.add("δ(S,b) = {B}");
//        texto.add("δ(A,a) = {A}");
//        texto.add("δ(A,b) = {B}");
//        texto.add("δ(B,b) = {B, X}");

        texto.add("M = (Q, Σ, δ, E, F)");
        texto.add("Q = {q0,q1,q2}");
        texto.add("Σ = {0,1}");
        texto.add("E = q0");
        texto.add("F = {q2}");
        texto.add("δ(q0,0) = {q0}");
        texto.add("δ(q0,ε) = {q1}");
        texto.add("δ(q1,1) = {q1}");
        texto.add("δ(q1,ε) = {q2}");
        texto.add("δ(q2,2) = {q2}");
        return texto;
    }

}
