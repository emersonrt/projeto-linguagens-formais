package model;

import utils.TextUtils;

import java.util.List;

public class AutomatoFinito {

    private List<String> estadosNaoTerminais;
    private List<String> estadosTerminais;
    private List<RegraTransicaoAF> regrasTransicao;
    private String simboloInicial;
    private List<String> estadosFinais;
    private AutomatoFinitoTipo tipo;

    public List<String> getEstadosNaoTerminais() {
        return estadosNaoTerminais;
    }

    public void setEstadosNaoTerminais(List<String> estadosNaoTerminais) {
        this.estadosNaoTerminais = estadosNaoTerminais;
    }

    public List<String> getEstadosTerminais() {
        return estadosTerminais;
    }

    public void setEstadosTerminais(List<String> estadosTerminais) {
        this.estadosTerminais = estadosTerminais;
    }

    public List<RegraTransicaoAF> getRegrasTransicao() {
        return regrasTransicao;
    }

    public void setRegrasTransicao(List<RegraTransicaoAF> regrasTransicao) {
        this.regrasTransicao = regrasTransicao;
    }

    public String getSimboloInicial() {
        return simboloInicial;
    }

    public void setSimboloInicial(String simboloInicial) {
        this.simboloInicial = simboloInicial;
    }

    public List<String> getEstadosFinais() {
        return estadosFinais;
    }

    public void setEstadosFinais(List<String> estadosFinais) {
        this.estadosFinais = estadosFinais;
    }

    public AutomatoFinitoTipo getTipo() {
        return tipo;
    }

    public void setTipo(AutomatoFinitoTipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "M = (" + TextUtils.arrayToStringLF(estadosNaoTerminais) +
                ", " + TextUtils.arrayToStringLF(estadosTerminais) +
                ", " + regrasTransicao.toString() +
                ", " + simboloInicial +
                ", " + TextUtils.arrayToStringLF(estadosFinais) +
                ')';
    }
}
