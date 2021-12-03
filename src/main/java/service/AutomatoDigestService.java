package service;

import model.AutomatoFinito;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AutomatoDigestService {

    private final String LETRA_INICIAL_DEFINICAO_AF = "M";
    private final String LETRA_INICIAL_ESTADOS = "Q";
    private final String LETRA_INICIAL_ALFABETO = "Σ";
    private final String LETRA_INICIAL_FINAIS = "F";
    private final String LETRA_INICIAL_TRANSICAO = "δ";

    public AutomatoFinito lerAutomatoFinito(List<String> comandos) {

        //encontra as linhas pelas iniciais definidas acima
        String linhaDefinicaoAF = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_DEFINICAO_AF);
        String linhaEstadosNaoTerminais = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_ESTADOS);
        String linhaEstadosTerminais = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_ALFABETO);
        String linhaEstadosFinais = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_FINAIS);


        AutomatoFinito af = new AutomatoFinito();
        af.setSimboloInicial(findSimboloInicial(linhaDefinicaoAF));

        System.out.println(af.getSimboloInicial());
//        af.setEstadosNaoTerminais(lerEstadosNT(linhaInicial));

        return null;
    }

    private String findSimboloInicial(String linhaDefinicaoAF) {
        List<String> definicoesAF = quebrarStringPorPadrao(linhaDefinicaoAF,",");
        return definicoesAF.get(3);
    }

    private List<String> quebrarStringPorPadrao(String str, String pattern) {
        return Pattern.compile(pattern)
                .splitAsStream(str)
                .collect(Collectors.toList());
    }

    //encontra o a linha pela letra inicial
    private String encontrarLinhaPorInicial(List<String> comandos, String inicial) {
        return comandos.stream()
                .filter(s -> s.startsWith(inicial))
                .findFirst()
                .orElse(null);
    }

}
