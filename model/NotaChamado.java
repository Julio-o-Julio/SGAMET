package model;

public class NotaChamado {
    public int numNota;
    public String descricao;

    // Construtor da classe
    /**
     * @param numNota
     * @param descricao
     */
    public NotaChamado(int numNota, String descricao) {
        this.numNota = numNota;
        this.descricao = descricao;
    }

    // Métodos getters e setters para acessar e modificar os atributos
    public int getNumNota() {
        return numNota;
    }

    public void setNumNota(int numNota) {
        this.numNota = numNota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
