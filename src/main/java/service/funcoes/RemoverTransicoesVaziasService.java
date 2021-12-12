package service.funcoes;

import model.AutomatoFinito;
import model.AutomatoFinitoTipo;
import model.EClosure;
import model.RegraTransicaoAF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoverTransicoesVaziasService implements FuncaoAF {

    @Override
    public Boolean executar(AutomatoFinito af) {

        if (af.getTipo().equals(AutomatoFinitoTipo.DETERMINISTICO)) {
            System.out.println("O autômato é Determinístico!");
            return true;
        } else if (!possuiTransicoesVazias(af)) {
            System.out.println("O autômato já é ε-livre!");
            return true;
        }

        System.out.println("----------------- AF antes da remoção de estados vazios -----------------");
        System.out.println(af);

        //passo 1 - decobrir o eClosure
        List<EClosure> eclosures = gerarEClosures(af.getEstados(), af.getRegrasTransicao());

        //passo 2 - substituir os "vai para" pelos seus eClosure
        List<RegraTransicaoAF> novasRegras = atualizarRegrasTransicao(af.getRegrasTransicao(), eclosures);

        //passo 3 - remover transições vazias
        novasRegras = removerTransicoesVazias(novasRegras);

        //passo 4 - adicionar transições para os estados do fechamento
        novasRegras = atualizarTransicoes(novasRegras);
        novasRegras = resolveExcessaoSimbolosRepetidos(novasRegras);
        af.setRegrasTransicao(novasRegras);

        //passo 5 - atualizar os estados finais
        af.setEstadosFinais(atualizarEstadosFinais(af.getEstadosFinais(), eclosures));

        System.out.println("\n----------------- AF após remoção de estados vazios -----------------");
        System.out.println(af);

        return true;
    }

    private List<RegraTransicaoAF> atualizarTransicoes(List<RegraTransicaoAF> regras) {
        List<RegraTransicaoAF> novasRegras = new ArrayList<>();
        novasRegras.addAll(regras);

        regras.forEach( regraTransicao -> {
            regraTransicao.getVaiPara().forEach(vaiPara -> {
                if (!vaiPara.equals(regraTransicao.getEstado())) {
                    adicionarTransicoesArbitrarias(regras, novasRegras, regraTransicao, vaiPara);
                }
            });
        });

        return novasRegras.stream()
                .sorted((a1, a2) -> a1.toString().compareTo(a2.toString()))
                .collect(Collectors.toList());
    }

    private List<RegraTransicaoAF> resolveExcessaoSimbolosRepetidos(List<RegraTransicaoAF> regras) {
        List<RegraTransicaoAF> novasRegras = new ArrayList<>();
        novasRegras.addAll(regras);
        for (RegraTransicaoAF regra : regras) {
            for (RegraTransicaoAF regra2 : regras) {
                if (ehRegraEstadoLeituraRepetida(regra, regra2)) {
                    RegraTransicaoAF regraErrada = getRegraTransicaoMaisCompleta(regra, regra2);
                    novasRegras.remove(regraErrada);
                }
            }
        }
        return novasRegras;
    }

    private RegraTransicaoAF getRegraTransicaoMaisCompleta(RegraTransicaoAF regra, RegraTransicaoAF regra2) {
        RegraTransicaoAF regraErrada = regra.getVaiPara().size() > regra2.getVaiPara().size() ? regra2 : regra;
        return regraErrada;
    }

    private boolean ehRegraEstadoLeituraRepetida(RegraTransicaoAF regra, RegraTransicaoAF regra2) {
        return regra2.getEstado().equals(regra.getEstado())
                && regra2.getLeitura().equals(regra.getLeitura())
                && !regra2.getVaiPara().equals(regra.getVaiPara());
    }

    private void adicionarTransicoesArbitrarias(List<RegraTransicaoAF> regras, List<RegraTransicaoAF> novasRegras, RegraTransicaoAF regraTransicao, String vaiPara) {
        regras.forEach(regraTransicaoAF -> {
            if (regraTransicaoAF.getEstado().equals(vaiPara)) {
                novasRegras.add(new RegraTransicaoAF(regraTransicao.getEstado(), regraTransicaoAF.getLeitura(), regraTransicaoAF.getVaiPara()));
            }
        });
    }

    private List<String> atualizarEstadosFinais(List<String> estadosFinais, List<EClosure> eClosures) {
        List<String> estadosFinaisAjustados = new ArrayList<>();
        eClosures.forEach(eClosure -> {
            estadosFinais.forEach(estadoFinal -> {
                if (eClosure.getVaiPara().contains(estadoFinal)) {
                    estadosFinaisAjustados.add(eClosure.getEstado());
                }
            });
        });
        return estadosFinaisAjustados.stream().distinct().collect(Collectors.toList());
    }

    private List<RegraTransicaoAF> removerTransicoesVazias(List<RegraTransicaoAF> novasRegras) {
        return novasRegras.stream()
                .filter(regraTransicaoAF -> !regraTransicaoAF.getLeitura().equals("ε"))
                .collect(Collectors.toList());
    }

    private List<RegraTransicaoAF> atualizarRegrasTransicao(List<RegraTransicaoAF> regrasTransicao, List<EClosure> eclosures) {
        for (RegraTransicaoAF regraTransicaoAF : regrasTransicao) {
            List<String> listaVaiPara = regraTransicaoAF.getVaiPara();
            for (EClosure eclosure : eclosures) {
                if (listaVaiPara.contains(eclosure.getEstado())) {
                    adicionaEstadosEclosureSemDuplicados(listaVaiPara, eclosure);
                }
            }
        }
        return regrasTransicao;
    }

    private void adicionaEstadosEclosureSemDuplicados(List<String> listaVaiPara, EClosure eclosure) {
        listaVaiPara.addAll(eclosure.getVaiPara().stream().filter(s -> !listaVaiPara.contains(s)).collect(Collectors.toList()));
    }

    private List<EClosure> gerarEClosures(List<String> estados, List<RegraTransicaoAF> regrasTransicao) {
        List<EClosure> lista = new ArrayList<>();
        estados.forEach(estado -> {
            List<String> vaiParaList = getVaiParaEclosure(estado, regrasTransicao);
            lista.add(new EClosure(estado, vaiParaList));
        });
        return lista;
    }

    private List<String> getVaiParaEclosure(String estado, List<RegraTransicaoAF> regrasTransicao) {
        List<String> estadosVaiPara = new ArrayList<>();
        estadosVaiPara.add(estado);
        estadosVaiPara.addAll(getVaiParaEclosureRecursivo(estado, regrasTransicao));
        return estadosVaiPara;
    }

    private List<String> getVaiParaEclosureRecursivo(String estado, List<RegraTransicaoAF> regrasTransicao) {
        List<String> estadosVaiPara = new ArrayList<>();
        for (RegraTransicaoAF regraTransicaoAF : regrasTransicao) {
            if (ehEstadoAtualETransicaoVazia(estado, regraTransicaoAF)) {
                estadosVaiPara.addAll(regraTransicaoAF.getVaiPara());
                regraTransicaoAF.getVaiPara()
                        .stream()
                        .map(vaiPara -> getVaiParaEclosureRecursivo(vaiPara, regrasTransicao))
                        .forEach(estadosVaiPara::addAll);
            }
        }
        return estadosVaiPara;
    }

    private boolean ehEstadoAtualETransicaoVazia(String estado, RegraTransicaoAF regraTransicaoAF) {
        return regraTransicaoAF.getEstado().equals(estado) && regraTransicaoAF.getLeitura().equals("ε");
    }

    private boolean possuiTransicoesVazias(AutomatoFinito af) {
        List<RegraTransicaoAF> regras = af.getRegrasTransicao();
        return regras.stream()
                .filter(regraTransicaoAF -> regraTransicaoAF.getLeitura().equals("ε"))
                .findFirst()
                .isPresent();
    }

}
