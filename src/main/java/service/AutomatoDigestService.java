package service;

import model.AutomatoFinito;

import java.util.List;

public class AutomatoDigestService {

    public AutomatoFinito lerAutomatoFinito(List<String> comandos) {

        String linhaInicial = comandos.get(0);

        AutomatoFinito af = new AutomatoFinito();
        af.setEstadosNaoTerminais(lerEstadosNT(linhaInicial));

        return null;
    }

    private List<Character> lerEstadosNT(String comando) {
//        comando.split("", );
        return null;
    }

}
