import model.AutomatoFinito;
import service.AutomatoDigestService;
import service.funcoesAF.ComandoEnum;
import service.funcoesAF.FuncaoAF;
import service.funcoesAF.RemoverTransicoesVaziasService;
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
        while (execucao) {
            System.out.println("\n\n-------------------- Linguagens Formais - Menu -------------------------------------------");
            System.out.println("0 - Encerrar execução");
            System.out.println("1 - Exibir AF");
            System.out.println("2 - Remover transições vazias");
            System.out.println("3 - Determinizar AF");
            System.out.println("4 - Minimizar AF");
            Integer comando = scan.nextInt();

            execucao = executarComando(comando, af);
        }

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
        texto.add("M = (Q, Σ, δ, E, F)");
        texto.add("Q = {S,A,B,X}");
        texto.add("Σ = {a, b}");
        texto.add("E = q0");
        texto.add("F = {X}");
        texto.add("δ(S,a) = {A}");
        texto.add("δ(S,b) = {B}");
        texto.add("δ(A,a) = {A}");
        texto.add("δ(A,b) = {B}");
        texto.add("δ(B,b) = {B, X}");
        return texto;
    }

}
