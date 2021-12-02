package model;

import utils.TextUtils;

import java.util.List;

public class RegraTransicaoAF {

    private Character estado;
    private Character leitura;
    private List<Character> vaiPara;


    public void setRegraTransicaoCompleta(String regra) {
        this.estado =  Character.valueOf(regra.charAt(0));
        this.leitura =  Character.valueOf(regra.charAt(1));
        this.vaiPara.add(Character.valueOf(regra.charAt(2)));
        //criar lógica aqui
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Character getLeitura() {
        return leitura;
    }

    public void setLeitura(Character leitura) {
        this.leitura = leitura;
    }

    public List<Character> getVaiPara() {
        return vaiPara;
    }

    public void setVaiPara(List<Character> vaiPara) {
        this.vaiPara = vaiPara;
    }

    @Override
    public String toString() {
        return "δ(" + estado + ", " + leitura + ") = " + TextUtils.arrayToStringLF(vaiPara);
    }
}
