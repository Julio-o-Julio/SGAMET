package model;

public class Equipamento {
    public String numSerie;
    public String descricao;
    public String marca;
    public String modelo;

    // Construtor da classe
    /**
     * @param numSerie
     * @param descricao
     * @param marca
     * @param modelo
     */
    public Equipamento(String numSerie, String descricao, String marca, String modelo) {
        this.numSerie = numSerie;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
    }

    // Métodos getters e setters para acessar e modificar os atributos
    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
