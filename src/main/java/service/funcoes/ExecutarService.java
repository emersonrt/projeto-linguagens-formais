package service.funcoes;

import java.util.ArrayList;
import java.util.List;
import model.AutomatoFinito;
import model.RegraTransicaoAF;

/**
 *
 * @author rafael
 */
public class ExecutarService implements FuncaoAF{
    
    String SUCESSO = "A sequência conseguiu ser executada pelo Automato Finito";
    String ERRO = "A sequência não conseguiu ser executada pelo Automato Finito";

    @Override
    public Boolean executar(AutomatoFinito af) {
    
        List<String> sequenciaExecutal = gerarSequenciaExecutavel();

        percorrerAutomato(af, sequenciaExecutal, af.getEstadoInicial(), 0);
        
        return true;
    }

    private List<String> gerarSequenciaExecutavel() {
        List<String> sequenciaExecutal = new ArrayList();
        sequenciaExecutal.add("a");
        sequenciaExecutal.add("b");
//        sequenciaExecutal.add("");
//        sequenciaExecutal.add("");
//        sequenciaExecutal.add("");
//        sequenciaExecutal.add("");
        
        return sequenciaExecutal;
    }

    private void percorrerAutomato(AutomatoFinito af, List<String> sequenciaExecutal, String estadoAtual, Integer auxiliar) {
        
            for (Integer auxiliar2 = auxiliar; auxiliar2 < sequenciaExecutal.size(); auxiliar2++){
            
                verificarEstadoFinal(af, estadoAtual);

                seguirSequencia(af, sequenciaExecutal, estadoAtual, auxiliar2);
            }

    }

    private void seguirSequencia(AutomatoFinito af, List<String> sequenciaExecutal, String estadoAtual, Integer auxiliar){
            
        for (Integer auxiliar2 = 0; auxiliar2 < af.getRegrasTransicao().size(); auxiliar2++){
            
            RegraTransicaoAF regra = af.getRegrasTransicao().get(auxiliar2);
            if (regra.getEstado() == estadoAtual){
                if (regra.getLeitura() == sequenciaExecutal.get(auxiliar2)){
                    System.out.println("Passou aqui");
                    regra.getVaiPara().forEach(proximaExecucao -> {
                        percorrerAutomato(af, sequenciaExecutal, proximaExecucao, auxiliar);
                    });
                    
                }
            }
        }    
        System.out.println(ERRO);
    }

    private void verificarEstadoFinal(AutomatoFinito af, String estadoAtual) {
        
        af.getEstadosFinais().forEach(estadoFinal ->{
            if (estadoFinal == estadoAtual)
                System.out.println(SUCESSO);
        });
    }
}
