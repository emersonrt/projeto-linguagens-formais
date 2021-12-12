package service.funcoes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.AutomatoFinito;
import model.AutomatoFinitoMinimizador;
import model.AutomatoFinitoTipo;
import model.EClosure;
import model.RegraTransicaoAF;

public class MinimizarService implements FuncaoAF{

    @Override
    public Boolean executar(AutomatoFinito af) {

        if (af.getTipo() == AutomatoFinitoTipo.NAO_DETERMINISTICO) {
            System.out.println("\nPrograma Não-Determinístico, impossível minimizar");
            return true;
        }
        
        System.out.println("\n----------------- AF antes de Minimizar -----------------");
        System.out.println(af);

        List<AutomatoFinitoMinimizador> listaAutomatosMinimizados = new ArrayList();

        //passo 1 - encontrar sentenças inacessiveis
        listaAutomatosMinimizados = gerarListaAutomato(af);
        listaAutomatosMinimizados = encontrarSentencaInacessivel(listaAutomatosMinimizados, af);
        
        af = atualizarAF(listaAutomatosMinimizados, af);
        
        //passo 2 - encontrar sentenças mortas
        listaAutomatosMinimizados = gerarListaAutomato(af);
        listaAutomatosMinimizados = encontrarSentencaMorta(listaAutomatosMinimizados, af);
        
        af = atualizarAF(listaAutomatosMinimizados, af);

        //passo 3 - encontrar sentenças equivalentes
        listaAutomatosMinimizados = gerarListaAutomato(af);
        listaAutomatosMinimizados = encontrarSentencaEquivalente(listaAutomatosMinimizados, af);

        af = atualizarAF(listaAutomatosMinimizados, af);

        System.out.println("\n----------------- AF após Minimização -----------------");
        System.out.println(af);

        return true;
    }

    private List<AutomatoFinitoMinimizador> encontrarSentencaInacessivel(List<AutomatoFinitoMinimizador> listaAutomatosMinimizados, AutomatoFinito af) {

        listaAutomatosMinimizados.forEach(lista -> {
                        
            if (af.getEstadoInicial().contains(lista.getTransicao().getEstado())){

                lista.setContador(lista.getContador()+1);
                
            }
        });
        
        af.getRegrasTransicao().forEach(sentenca -> {
               
            sentenca.getVaiPara().forEach(vaiPara ->{
                
                listaAutomatosMinimizados.forEach(lista -> {
                        
                    if (vaiPara.contains(lista.getTransicao().getEstado())){
                        lista.setContador(lista.getContador()+1);
                    }
                });
            });
        });
        
        List<AutomatoFinitoMinimizador> listaAutomatosMinimizadosNova = new ArrayList();
        
        listaAutomatosMinimizados.forEach(lista ->{
        
            if (lista.getContador() > 0)
                listaAutomatosMinimizadosNova.add(lista);
        });
        
        return listaAutomatosMinimizadosNova;
    }

    private List<AutomatoFinitoMinimizador> gerarListaAutomato(AutomatoFinito af) {
        List<AutomatoFinitoMinimizador> listaAutomatosMinimizados = new ArrayList();
        
        af.getRegrasTransicao().forEach(listaRegrasTransicao -> {
        listaAutomatosMinimizados.add(new AutomatoFinitoMinimizador(listaRegrasTransicao, 0));
        });
        
        return listaAutomatosMinimizados;
    }

    private AutomatoFinito atualizarAF(List<AutomatoFinitoMinimizador> listaAutomatosMinimizados, AutomatoFinito af) {
        List<String> afsExistentes = new ArrayList();
        List<RegraTransicaoAF> regrasNovas = new ArrayList();
                
        af.getEstados().forEach(naoRemoverEstado -> {
            listaAutomatosMinimizados.forEach(estadosNovos ->{
                if (naoRemoverEstado.contains(estadosNovos.getTransicao().getEstado())){
                    afsExistentes.add(naoRemoverEstado);
                }
            });
        });
        
        List<String> listaSemRepeticao = afsExistentes.stream().distinct().collect(Collectors.toList());
        
        listaAutomatosMinimizados.forEach(atualizar -> {
            regrasNovas.add(atualizar.getTransicao());
        });
        af.setRegrasTransicao(regrasNovas);
        
        af.setEstados(listaSemRepeticao);
        
        return af;
    }

    private List<AutomatoFinitoMinimizador> encontrarSentencaMorta(List<AutomatoFinitoMinimizador> listaAutomatosMinimizados, AutomatoFinito af) {
    
        List<String> estadosFinais = af.getEstadosFinais();
    
        do {
            List<String> novosEstadosFinais = new ArrayList();
            estadosFinais.forEach(estadoFinal ->{

                listaAutomatosMinimizados.forEach(lista ->{
                    lista.getTransicao().getVaiPara().forEach(estadoAnterior->{
                        if(estadoAnterior.contains(estadoFinal)){
                            lista.setContador(lista.getContador()+1);
                            
                            if (!lista.getTransicao().getEstado().equals(estadoFinal))
                                novosEstadosFinais.add(lista.getTransicao().getEstado());
                        }
                    });
                });

            });
            estadosFinais = novosEstadosFinais.stream().distinct().collect(Collectors.toList());
        }while ((estadosFinais.size() <= 1) && (!af.getEstadoInicial().equals(estadosFinais.get(0))));
    
        List<AutomatoFinitoMinimizador> listaAutomatosMinimizadosNova = new ArrayList();
        
        listaAutomatosMinimizados.forEach(lista ->{
        
            if (lista.getContador() > 0)
                listaAutomatosMinimizadosNova.add(lista);
        });
        
        return listaAutomatosMinimizadosNova;
    }

    private List<AutomatoFinitoMinimizador> encontrarSentencaEquivalente(List<AutomatoFinitoMinimizador> listaAutomatosMinimizados, AutomatoFinito af) {

        for(Integer auxiliar = 0; auxiliar < listaAutomatosMinimizados.size(); auxiliar++){
            
            List<RegraTransicaoAF> regrasNovas = new ArrayList();
            List<Integer> indexes = new ArrayList();
            
            for(Integer auxiliar2 = 0; auxiliar2 < listaAutomatosMinimizados.size(); auxiliar2++){
            
                if(
                listaAutomatosMinimizados.get(auxiliar).getTransicao().getLeitura() == listaAutomatosMinimizados.get(auxiliar2).getTransicao().getLeitura() &&
                listaAutomatosMinimizados.get(auxiliar).getTransicao().getVaiPara()== listaAutomatosMinimizados.get(auxiliar2).getTransicao().getVaiPara() &&
                auxiliar != auxiliar2){                    
                
                    regrasNovas.add(listaAutomatosMinimizados.get(auxiliar2).getTransicao());
                    indexes.add(auxiliar2);
                }
            }
            if (regrasNovas.size() > 1 ){
                af = removerEstadosFinais(af, regrasNovas, indexes, listaAutomatosMinimizados);
                af= removerEstados(af, regrasNovas, indexes, listaAutomatosMinimizados);
                af = removerRegrasTransicao(af, regrasNovas, indexes, listaAutomatosMinimizados);
            }
        }
        
        return listaAutomatosMinimizados;
    }

    private AutomatoFinito removerEstadosFinais(AutomatoFinito af, List<RegraTransicaoAF> regraRemocoes, List<Integer> indexes, List<AutomatoFinitoMinimizador> listaAutomatosMinimizados) {
        List<String> regrasAF = new ArrayList();
                
        af.getEstadosFinais().forEach(regraAntiga -> {
        
            regraRemocoes.forEach(regraRemocao -> {
                if (!regraAntiga.equals(regraRemocao)){
                    regrasAF.add(regraAntiga);
                }
            });
        });
        
        if (regrasAF.size() > 0){
            regrasAF.add(regraRemocoes.get(0).getEstado());

            af.setEstadosFinais(regrasAF.stream().distinct().collect(Collectors.toList()));
        }
        return af;
    }

    private AutomatoFinito removerEstados(AutomatoFinito af, List<RegraTransicaoAF> regraRemocoes, List<Integer> indexes, List<AutomatoFinitoMinimizador> listaAutomatosMinimizados) {
        
        List<String> regrasAF = new ArrayList();
        af.getEstadosFinais().forEach(regraAntiga -> {
        
            regraRemocoes.forEach(regraRemocao -> {
                if (!regraAntiga.equals(regraRemocao)){
                    regrasAF.add(regraAntiga);
                }
            });
        });
        
        if (regrasAF.size() > 0){
        regrasAF.add(regraRemocoes.get(0).getEstado());
        
        af.setEstados(regrasAF.stream().distinct().collect(Collectors.toList()));
        }
        return af;
    }

    private AutomatoFinito removerRegrasTransicao(AutomatoFinito af, List<RegraTransicaoAF> regrasNovas, List<Integer> indexes, List<AutomatoFinitoMinimizador> listaAutomatosMinimizados) {
        
        List<AutomatoFinitoMinimizador> listaAutomatosMinimizadosNova = new ArrayList();
        
        af.getRegrasTransicao().forEach(sentenca -> {
               
            sentenca.getVaiPara().forEach(vaiPara ->{
                
                listaAutomatosMinimizados.forEach(lista -> {
                        
                    if (vaiPara.contains(lista.getTransicao().getEstado())){
                        lista.setContador(lista.getContador()+1);
                    }
                });
            });
        });
        
        return af;
    }
}