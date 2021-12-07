package model;

import utils.TextUtils;

import java.util.List;

public class AutomatoFinito {

    private List<String> estados;
    private List<String> simbolos; //ou alfabeto
    private List<RegraTransicaoAF> regrasTransicao;
    private String estadoInicial;
    private List<String> estadosFinais;
    private AutomatoFinitoTipo tipo;

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<String> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(List<String> simbolos) {
        this.simbolos = simbolos;
    }

    public List<RegraTransicaoAF> getRegrasTransicao() {
        return regrasTransicao;
    }

    public void setRegrasTransicao(List<RegraTransicaoAF> regrasTransicao) {
        this.regrasTransicao = regrasTransicao;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
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
        return "M = (" + TextUtils.arrayToStringLF(this.estados) +
                ", " + TextUtils.arrayToStringLF(this.simbolos) +
                ", δ" +
                ", " + this.estadoInicial +
                ", " + TextUtils.arrayToStringLF(this.estadosFinais) +
                ")\n" + this.regrasTransicao.toString();
    }
}
