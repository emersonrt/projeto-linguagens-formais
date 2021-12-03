package model;

import utils.TextUtils;

import java.util.List;

public class RegraTransicaoAF {

    private String estado;
    private String leitura;
    private List<String> vaiPara;


    public void setRegraTransicaoCompleta(String regra) {
        //criar lógica aqui
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLeitura() {
        return leitura;
    }

    public void setLeitura(String leitura) {
        this.leitura = leitura;
    }

    public List<String> getVaiPara() {
        return vaiPara;
    }

    public void setVaiPara(List<String> vaiPara) {
        this.vaiPara = vaiPara;
    }

    @Override
    public String toString() {
        return "δ(" + estado + ", " + leitura + ") = " + TextUtils.arrayToStringLF(vaiPara);
    }
}
