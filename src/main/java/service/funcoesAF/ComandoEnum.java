package service.funcoesAF;

import model.AutomatoFinito;

public enum ComandoEnum {

    ENCERRAR(0, new FuncaoAF() {
        @Override
        public Boolean executar(AutomatoFinito af) {
            return FuncaoAF.super.executar(af);
        }
    }),
    EXIBIR(1, new ExibirService()),
    REMOVER_TRANSICOES_VAZIAS(2, new RemoverTransicoesVaziasService()),
    DETERMINIZAR(3, new DeterminizarService()),
    MINIMIZAR(4, new MinimizarService());

    private Integer codigo;
    private FuncaoAF funcao;

    ComandoEnum(Integer codigo, FuncaoAF funcao) {
        this.codigo = codigo;
        this.funcao = funcao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public FuncaoAF getFuncao() {
        return funcao;
    }
}
