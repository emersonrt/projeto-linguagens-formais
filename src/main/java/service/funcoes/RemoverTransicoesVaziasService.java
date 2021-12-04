package service.funcoes;

import model.AutomatoFinito;
import model.AutomatoFinitoTipo;
import model.RegraTransicaoAF;

import java.util.List;

public class RemoverTransicoesVaziasService implements FuncaoAF {

    @Override
    public Boolean executar(AutomatoFinito af) {

        if (af.getTipo().equals(AutomatoFinitoTipo.DETERMINISTICO)) {
            System.out.println("O autômato é Determinístico!");
            return true;
        } else if (!possuiTransicoesVazias(af)) {
            System.out.println("O autômato já é ε-livre!");
        }

        //passo 1 - decobrir o eClosure

        //passo 2 - substituir os "vai para" pelos seus eClosure

        //passo 3 - remover duplicados (passo necessário no nosso sistema

        //passo 4 - atualizar os estados finais

        return true;
    }

    private boolean possuiTransicoesVazias(AutomatoFinito af) {
        List<RegraTransicaoAF> regras = af.getRegrasTransicao();
        return regras.stream()
                .filter(regraTransicaoAF -> regraTransicaoAF.getLeitura().equals("ε"))
                .findFirst()
                .isPresent();
    }

//        texto.add("δ(q0,0) = {q0}");
//        texto.add("δ(q0,ε) = {q1}");
//        texto.add("δ(q1,1) = {q1}");
//        texto.add("δ(q1,ε) = {q2}");
//        texto.add("δ(q2,2) = {q2}");

//    private List<EClosure> passo1DescobrirEclosure(List<RegraTransicaoAF> regrasTransicao) {
//        List<EClosure> teste = new ArrayList<>();
//        List<String> vaiParaQ0 = new ArrayList<>();
//        vaiParaQ0.add("q0");
//        vaiParaQ0.add("q1");
//        List<String> vaiParaQ1 = new ArrayList<>();
//        vaiParaQ1.add("q1");
//        teste.add(new EClosure("q0", vaiParaQ0));
//        teste.add(new EClosure("q0", vaiParaQ1));
//        return teste;
//    }

}
