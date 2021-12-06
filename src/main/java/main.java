import model.AutomatoFinito;
import service.AutomatoDigestService;
import enums.ComandoEnum;
import utils.TextUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws Exception {

        AutomatoDigestService service = new AutomatoDigestService();
        Scanner scan = new Scanner(System.in);

        File f = new File("/home/emerson/Documents/exemplos/exemploRemocaoMovVazios2.txt");
        if (!f.exists()) {
            throw new Exception("Arquivo exemplo inexistente!");
        }

        List<String> texto = Files.readAllLines(f.toPath());
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
        texto.add("Q = {q0,q1,q2,q3,q4}");
        texto.add("Σ = {a,b}");
        texto.add("E = q0");
        texto.add("F = {q2}");
        texto.add("δ(q0,a) = {q1}");
        texto.add("δ(q0,b) = {q3}");
        texto.add("δ(q1,a) = {q1}");
        texto.add("δ(q3,a) = {q4}");
        texto.add("δ(q1,b) = {q2}");

//        texto.add("M = (Q, Σ, δ, E, F)");
//        texto.add("Q = {q0,q1,q2}");
//        texto.add("Σ = {0,1,2}");
//        texto.add("E = q0");
//        texto.add("F = {q2}");
//        texto.add("δ(q0,0) = {q0}");
//        texto.add("δ(q0,ε) = {q1}");
//        texto.add("δ(q1,1) = {q1}");
//        texto.add("δ(q1,ε) = {q2}");
//        texto.add("δ(q2,2) = {q2}");

//        texto.add("M = (Q, Σ, δ, E, F)");
//        texto.add("Q = {q0,q1}");
//        texto.add("Σ = {a,b}");
//        texto.add("E = q0");
//        texto.add("F = {q1}");
//        texto.add("δ(q0,a) = {q0}");
//        texto.add("δ(q0,ε) = {q1}");
//        texto.add("δ(q1,b) = {q1}");

//        texto.add("M = (Q, Σ, δ, E, F)");
//        texto.add("Q = {q1,q2,q3,q4}");
//        texto.add("Σ = {a,b,c}");
//        texto.add("E = q1");
//        texto.add("F = {q1,q2,q3,q4}");
//        texto.add("δ(q1,a) = {q1}");
//        texto.add("δ(q1,ε) = {q2}");
//        texto.add("δ(q2,b) = {q2}");
//        texto.add("δ(q2,ε) = {q3}");
//        texto.add("δ(q3,c) = {q3}");
//        texto.add("δ(q3,ε) = {q4}");
//        texto.add("δ(q4,a) = {q4}");
        return texto;
    }

}
