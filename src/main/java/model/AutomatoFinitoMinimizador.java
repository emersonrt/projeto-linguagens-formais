/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author rafael
 */
public class AutomatoFinitoMinimizador {
    private RegraTransicaoAF transicao;
    private Integer contador;
    
    public AutomatoFinitoMinimizador(RegraTransicaoAF transicao, Integer contador) {
        this.transicao = transicao;
        this.contador = contador;
    }

    public RegraTransicaoAF getTransicao() {
        return transicao;
    }

    public void setTransicao(RegraTransicaoAF transicao) {
        this.transicao = transicao;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }
    
    
}
