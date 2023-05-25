package model;

public class NotaChamado {
    private Chamado chamado;
    private int numNota;
    private String descricao;

    public NotaChamado(Chamado chamado, int numNota, String descricao) {
        this.chamado = chamado;
        this.numNota = numNota;
        this.descricao = descricao;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public int getNumChamado() {
        return chamado.getCodChamado();
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

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