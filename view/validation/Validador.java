package view.validation;

public abstract class Validador<TipoCampo, TipoRetorno>{
    protected abstract boolean ehValido(TipoCampo valor);
    protected abstract TipoRetorno getValor();

}
