package model;

public class NotaChamado {
    private int numNota;
    private String descricao;

    // Construtor da classegit s
    public NotaChamado(int numNota, String descricao) {
        this.numNota = numNota;
        this.descricao = descricao;
    }

    // Métodos getters e setters para acessar e modificar as propriedades
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
