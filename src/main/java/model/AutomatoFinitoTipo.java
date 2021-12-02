package model;

public enum AutomatoFinitoTipo {

    DETERMINISTICO(1),
    NAO_DETERMINISTICO(2);

    private final Integer tipo;

    AutomatoFinitoTipo(Integer tipo) {
        this.tipo = tipo;
    }
}
