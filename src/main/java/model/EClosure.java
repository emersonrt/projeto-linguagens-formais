package model;

import java.util.List;

public class EClosure {

    private String estado;
    private List<String> vaiPara;

    public EClosure(String estado, List<String> vaiPara) {
        this.estado = estado;
        this.vaiPara = vaiPara;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getVaiPara() {
        return vaiPara;
    }

    public void setVaiPara(List<String> vaiPara) {
        this.vaiPara = vaiPara;
    }

    @Override
    public String toString() {
        return "EClosure{" +
                "estado='" + estado + '\'' +
                ", vaiPara=" + vaiPara +
                '}';
    }
}
