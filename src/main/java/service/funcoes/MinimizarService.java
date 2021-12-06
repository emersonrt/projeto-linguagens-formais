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
        
        
//        System.out.println(af);

        System.out.println(af.getTipo());
        
        if (af.getTipo() == AutomatoFinitoTipo.NAO_DETERMINISTICO)
        {
            System.out.println("\nPrograma Não-Determinístico, impossível minimizar");
            return true;
        }
        
        System.out.println("----------------- AF antes da remoção de Minimizar -----------------");

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
        
        
        af = atualizarAF(listaAutomatosMinimizados, af);
        

//        System.out.println("\n----------------- AF após remoção de estados vazios -----------------");
//        System.out.println(af);

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

}
