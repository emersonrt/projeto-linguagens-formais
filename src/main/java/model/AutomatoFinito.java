package model;

import utils.TextUtils;

import java.util.List;

public class AutomatoFinito {

    private List<Character> estadosNaoTerminais;
    private List<Character> estadosTerminais;
    private List<RegraTransicaoAF> regrasTransicao;
    private Character simboloInicial;
    private List<Character> estadosFinais;
    private AutomatoFinitoTipo tipo;


    public List<Character> getEstadosNaoTerminais() {
        return estadosNaoTerminais;
    }

    public void setEstadosNaoTerminais(List<Character> estadosNaoTerminais) {
        this.estadosNaoTerminais = estadosNaoTerminais;
    }

    public List<Character> getEstadosTerminais() {
        return estadosTerminais;
    }

    public void setEstadosTerminais(List<Character> estadosTerminais) {
        this.estadosTerminais = estadosTerminais;
    }

    public List<RegraTransicaoAF> getRegrasTransicao() {
        return regrasTransicao;
    }

    public void setRegrasTransicao(List<RegraTransicaoAF> regrasTransicao) {
        this.regrasTransicao = regrasTransicao;
    }

    public Character getSimboloInicial() {
        return simboloInicial;
    }

    public void setSimboloInicial(Character simboloInicial) {
        this.simboloInicial = simboloInicial;
    }

    public List<Character> getEstadosFinais() {
        return estadosFinais;
    }

    public void setEstadosFinais(List<Character> estadosFinais) {
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
                ", {" + TextUtils.arrayToStringLF(estadosTerminais) +
                ", " + regrasTransicao.toString() +
                ", " + simboloInicial +
                ", " + TextUtils.arrayToStringLF(estadosFinais) +
                ')';
    }
}
