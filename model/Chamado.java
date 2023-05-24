package model;

public class Chamado {
    private int codChamado;
    private String codUrgencia;
    private String codSituacao;
    private String telefoneSuporte;

    public Chamado(int codChamado, String codUrgencia, String codSituacao, String telefoneSuporte) {
        this.codChamado = codChamado;
        this.codUrgencia = codUrgencia;
        this.codSituacao = codSituacao;
        this.telefoneSuporte = telefoneSuporte;
    }

    public int getCodChamado() {
        return codChamado;
    }

    public void setCodChamado(int codChamado) {
        this.codChamado = codChamado;
    }

    public String getCodUrgencia() {
        return codUrgencia;
    }

    public void setCodUrgencia(String codUrgencia) {
        this.codUrgencia = codUrgencia;
    }

    public String getCodSituacao() {
        return codSituacao;
    }

    public void setCodSituacao(String codSituacao) {
        this.codSituacao = codSituacao;
    }

    public String getTelefoneSuporte() {
        return telefoneSuporte;
    }

    public void setTelefoneSuporte(String telefoneSuporte) {
        this.telefoneSuporte = telefoneSuporte;
    }
}