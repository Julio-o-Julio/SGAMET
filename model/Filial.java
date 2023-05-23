package model;

public class Filial {
    private int numIdentificacao;
    private int nroTecnicos;
    private String nome;
    private String endereco;
    private String cidade;
    private String cep;
    private String estado;
    private String pais;

    public Filial(int numIdentificacao, int nroTecnicos, String nome, String endereco, String cidade, String cep, String estado, String pais) {
        this.numIdentificacao = numIdentificacao;
        this.nroTecnicos = nroTecnicos;
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.estado = estado;
        this.pais = pais;
    }

    public int getNumIdentificacao() {
        return numIdentificacao;
    }

    public void setNumIdentificacao(int numIdentificacao) {
        this.numIdentificacao = numIdentificacao;
    }

    public int getNroTecnicos() {
        return nroTecnicos;
    }

    public void setNroTecnicos(int nroTecnicos) {
        this.nroTecnicos = nroTecnicos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
