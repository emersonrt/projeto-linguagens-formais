package service;

import model.RegraTransicaoAF;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class RegraTransicaoDigestService {


    public List<RegraTransicaoAF> criarRegrasTransicao(List<String> transicoes) {

        List<RegraTransicaoAF> regras = new ArrayList<>();

        transicoes.forEach( transicao -> {
            String estado = getEstado(transicao);
            String leitura = getLeitura(transicao);
            List<String> vaiPara = getVaiPara(transicao);
            regras.add(new RegraTransicaoAF(estado, leitura, vaiPara));
        });

        return regras;
    }

    private String getEstado(String transicao) {
        String estadoLeitura = TextUtils.quebrarStringPorPadrao(transicao, "=").get(0);
        String estadoDefinicao = TextUtils.quebrarStringPorPadrao(estadoLeitura, ",").get(0);
        return removerDeltaEParenteses(estadoDefinicao);
    }

    private String getLeitura(String transicao) {
        String estadoLeitura = TextUtils.quebrarStringPorPadrao(transicao, "=").get(0);
        String estadoDefinicao = TextUtils.quebrarStringPorPadrao(estadoLeitura, ",").get(1);
        return removerDeltaEParenteses(estadoDefinicao);
    }

    private List<String> getVaiPara(String transicao) {
        String conjuntoEstados = TextUtils.quebrarStringPorPadrao(transicao, "=").get(1);
        String conjuntoSemColchetes = TextUtils.removeColchetes(conjuntoEstados);
        return TextUtils.quebrarStringPorPadrao(conjuntoSemColchetes, ",");
    }

    private String removerDeltaEParenteses(String str) {
        String strSemDelta = TextUtils.removerDelta(str);
        return TextUtils.removerParenteses(strSemDelta);
    }

}
