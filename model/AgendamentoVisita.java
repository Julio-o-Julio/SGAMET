package model;

import java.time.LocalDateTime;

public class AgendamentoVisita {
    private LocalDateTime horario;
    private String nomeReceptor;
    private String telefoneReceptor;
    private String situacao;

    private Chamado chamado;
    private Funcionario funcionario;

    public AgendamentoVisita(LocalDateTime horario, String nomeReceptor, String telefoneReceptor, String situacao) {
        this.horario = horario;
        this.nomeReceptor = nomeReceptor;
        this.telefoneReceptor = telefoneReceptor;
        this.situacao = situacao;
    }

    public AgendamentoVisita(LocalDateTime horario, String nomeReceptor, String telefoneReceptor) {
        this.horario = horario;
        this.nomeReceptor = nomeReceptor;
        this.telefoneReceptor = telefoneReceptor;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getNomeReceptor() {
        return nomeReceptor;
    }

    public void setNomeReceptor(String nomeReceptor) {
        this.nomeReceptor = nomeReceptor;
    }

    public String getTelefoneReceptor() {
        return telefoneReceptor;
    }

    public void setTelefoneReceptor(String telefoneReceptor) {
        this.telefoneReceptor = telefoneReceptor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
