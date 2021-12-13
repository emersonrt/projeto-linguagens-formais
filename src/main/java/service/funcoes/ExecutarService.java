package service.funcoes;

import exeption.SucessoException;
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
    Integer totalSucesso = 0;

    @Override
    public Boolean executar(AutomatoFinito af) {
    
        List<String> sequenciaExecutavel = gerarSequenciaExecutavel();
        
        System.out.println(af);
        System.out.print("Sequência: ");
        sequenciaExecutavel.forEach(sequencia -> {
            System.out.print(sequencia + ", ");
        });
        System.out.print("\n\n");
        percorrerAutomato(af, sequenciaExecutavel, af.getEstadoInicial());
        
        if (totalSucesso > 0){
            System.out.println(SUCESSO);
        }
        else {
            System.out.println(ERRO);
        }
        
        return true;
    }

    private List<String> gerarSequenciaExecutavel() {
        List<String> sequenciaExecutavel = new ArrayList();
        sequenciaExecutavel.add("a");
        sequenciaExecutavel.add("a");
        sequenciaExecutavel.add("b");
        sequenciaExecutavel.add("b");
//        sequenciaExecutavel.add("");
//        sequenciaExecutavel.add("");
        
        return sequenciaExecutavel;
    }

    private void percorrerAutomato(AutomatoFinito af, List<String> sequenciaExecutavel, String estadoAtual) {
        
        List<String> listaEstados = new ArrayList();
        listaEstados.add(estadoAtual);
        
        verificarEstadoFinal(af, estadoAtual);
        
        for (Integer auxiliar = 0; auxiliar < sequenciaExecutavel.size(); auxiliar++){

            listaEstados = seguirSequencia(af, sequenciaExecutavel.get(auxiliar), listaEstados);
        
            listaEstados.forEach(estado ->{ 
                verificarEstadoFinal(af, estado);
            });
        }

    }

    private List<String> seguirSequencia(AutomatoFinito af, String sequenciaExecutavel, List<String> listaEstados){
        List<String> novaListaEstados = new ArrayList();
        for (Integer auxiliar = 0; auxiliar < af.getRegrasTransicao().size(); auxiliar++){
            
            for (Integer auxiliar2 = 0; auxiliar2 < listaEstados.size(); auxiliar2++){
                
                String estadoAtual = listaEstados.get(auxiliar2);
                RegraTransicaoAF regra = af.getRegrasTransicao().get(auxiliar);
                
                if (regra.getEstado().equals(estadoAtual) && regra.getLeitura().equals(sequenciaExecutavel)){
                    regra.getVaiPara().forEach(proximaExecucao -> {
                        novaListaEstados.add(proximaExecucao);
                    });
                }
            }
        }
        return novaListaEstados;
    }
    private void verificarEstadoFinal(AutomatoFinito af, String estadoAtual){
        
        for (Integer auxiliar = 0; auxiliar < af.getEstadosFinais().size(); auxiliar++){
            String estadoFinal = af.getEstadosFinais().get(auxiliar);
            if (estadoFinal.equals(estadoAtual)){
                totalSucesso++;
            }
        }
    }
}
