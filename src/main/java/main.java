import model.AutomatoFinito;
import service.AutomatoDigestService;
import enums.ComandoEnum;
import utils.TextUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws Exception {

        AutomatoDigestService service = new AutomatoDigestService();
        Scanner scan = new Scanner(System.in);

        File f = new File("/home/emerson/Documents/exemplos/exemploMinimizacao.txt");
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

}
