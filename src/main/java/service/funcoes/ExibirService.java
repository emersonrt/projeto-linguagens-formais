package service.funcoes;

import model.AutomatoFinito;

public class ExibirService implements FuncaoAF {

    @Override
    public Boolean executar(AutomatoFinito af) {
        System.out.println("\n---------------------------------- Exibindo AF ------------------------------------");
        System.out.println(af);
        System.out.println("Tipo: " + af.getTipo());
        System.out.println("-----------------------------------------------------------------------------------");
        return true;
    }

}
