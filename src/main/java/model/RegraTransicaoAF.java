package model;

import utils.TextUtils;

import java.util.List;

public class RegraTransicaoAF {

    private String estado;
    private String leitura;
    private List<String> vaiPara;

    public RegraTransicaoAF(String estado, String leitura, List<String> vaiPara) {
        this.estado = estado;
        this.leitura = leitura;
        this.vaiPara = vaiPara;
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

    public Boolean getEhNaoDeterministico() {
        return this.vaiPara.size() > 1;
    }

    @Override
    public String toString() {
        return "δ(" + estado + ", " + leitura + ") = " + TextUtils.arrayToStringLF(vaiPara);
    }
}
