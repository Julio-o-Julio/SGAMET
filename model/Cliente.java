package model;

public class Cliente {
    private String cpfCnpj;
    private String companhia;
    private String cep;
    private String pais;
    private String estado;
    private String cidade;
    private String idiomaPreferencia;

    public Cliente(String cpfCnpj, String companhia, String cep, String pais, String estado, String cidade, String idiomaPreferencia) {
        this.cpfCnpj = cpfCnpj;
        this.companhia = companhia;
        this.cep = cep;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.idiomaPreferencia = idiomaPreferencia;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getCompanhia() {
        return companhia;
    }

    public void setCompanhia(String companhia) {
        this.companhia = companhia;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getIdiomaPreferencia() {
        return idiomaPreferencia;
    }

    public void setIdiomaPreferencia(String idiomaPreferencia) {
        this.idiomaPreferencia = idiomaPreferencia;
    }
}
