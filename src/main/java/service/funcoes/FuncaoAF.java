package service.funcoes;

import model.AutomatoFinito;

public interface FuncaoAF {

    default Boolean executar(AutomatoFinito af) {
        return false;
    }

}
