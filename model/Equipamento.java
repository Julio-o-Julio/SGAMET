package model;

import model.Cliente;
public class Equipamento {
    private String numSerie;
    private String descricao;
    private String marca;
    private String modelo;
    private Cliente cliente;

    public Equipamento(String numSerie, String descricao, String marca, String modelo, Cliente cliente) {
        this.numSerie = numSerie;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.cliente = cliente;
    }

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
