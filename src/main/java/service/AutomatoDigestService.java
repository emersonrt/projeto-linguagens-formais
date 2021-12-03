package service;

import model.AutomatoFinito;
import model.AutomatoFinitoTipo;
import model.RegraTransicaoAF;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AutomatoDigestService {

    private final String LETRA_INICIAL_DEFINICAO_AF = "M";
    private final String LETRA_INICIAL_ESTADOS = "Q";
    private final String LETRA_INICIAL_ALFABETO = "Σ";
    private final String LETRA_INICIAL_ESTADO_INICIAL = "E";
    private final String LETRA_INICIAL_FINAIS = "F";
    private final String LETRA_INICIAL_TRANSICAO = "δ";

    public AutomatoFinito lerAutomatoFinito(List<String> comandos) {

        RegraTransicaoDigestService transicaoService = new RegraTransicaoDigestService();

        //encontra as linhas pelas iniciais definidas acima
        String linhaDefinicaoAF = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_DEFINICAO_AF);
        String linhaEstados = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_ESTADOS);
        String linhaSimbolos = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_ALFABETO);
        String linhaEstadoInicial = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_ESTADO_INICIAL);
        String linhaEstadosFinais = encontrarLinhaPorInicial(comandos, LETRA_INICIAL_FINAIS);
        List<String> transicoes = encontrarTransicoesPorInicial(comandos, LETRA_INICIAL_TRANSICAO);

        List<RegraTransicaoAF> regrasTransicao = transicaoService.criarRegrasTransicao(transicoes);

        AutomatoFinito af = new AutomatoFinito();
        af.setEstados(gerarListaPorConjunto(linhaEstados));
        af.setSimbolos(gerarListaPorConjunto(linhaSimbolos));
        af.setEstadoInicial(encontrarEstadoInicial(linhaEstadoInicial));
        af.setEstadosFinais(gerarListaPorConjunto(linhaEstadosFinais));
        af.setRegrasTransicao(regrasTransicao);
        af.setTipo(gerarTipoAF(regrasTransicao));

        return af;
    }

    private List<String> gerarListaPorConjunto(String linhaConjunto) {
        String estados = TextUtils.quebrarStringPorPadrao(linhaConjunto, "=").get(1);
        estados = TextUtils.removeColchetes(estados);
        return TextUtils.quebrarStringPorPadrao(estados, ",");
    }

    private AutomatoFinitoTipo gerarTipoAF(List<RegraTransicaoAF> regras) {
        Boolean possuiTransicaoNaoDeterministica = regras.stream()
                .anyMatch(regraTransicao -> regraTransicao.getEhNaoDeterministico());
        return possuiTransicaoNaoDeterministica ? AutomatoFinitoTipo.NAO_DETERMINISTICO : AutomatoFinitoTipo.DETERMINISTICO;
    }

    //encontra o estado inicial
    private String encontrarEstadoInicial(String linhaEstadoInicial) {
        List<String> definicoesAF = TextUtils.quebrarStringPorPadrao(linhaEstadoInicial,"=");
        return definicoesAF.get(1);
    }

    //encontra a lista de transições pela letra inicial
    private List<String> encontrarTransicoesPorInicial(List<String> comandos, String inicial) {
        return comandos.stream()
                .filter(s -> s.startsWith(inicial))
                .collect(Collectors.toList());
    }

    //encontra a String pela letra inicial
    private String encontrarLinhaPorInicial(List<String> comandos, String inicial) {
        return comandos.stream()
                .filter(s -> s.startsWith(inicial))
                .findFirst()
                .orElse(null);
    }

}
