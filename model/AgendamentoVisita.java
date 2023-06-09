package model;

import java.time.LocalDateTime;

public class AgendamentoVisita {
    private int id = -1; // PARA O BANCO DE DADOS
    private LocalDateTime horario;
    private String nomeReceptor;
    private String telefoneReceptor;
    private String situacao;

    private Chamado chamado;
    private Funcionario funcionario;
    public final static class SITUACAO {
        public final static String PENDENTE = "Pendente";
        public final static String EM_ATENDIMENTO = "Em atendimento";
        public final static String REALIZADA = "Realizada";
        public final static String CANCELADA = "Cancelada";

    }
    public AgendamentoVisita(LocalDateTime horario, String nomeReceptor, String telefoneReceptor, String situacao, Chamado chamado, Funcionario funcionario) {
        this.horario = horario;
        this.nomeReceptor = nomeReceptor;
        this.telefoneReceptor = telefoneReceptor;
        this.situacao = situacao;
        this.chamado = chamado;
        this.funcionario = funcionario;
    }

    public AgendamentoVisita(LocalDateTime horario, String nomeReceptor, String telefoneReceptor, Chamado chamado, Funcionario funcionario) {
        this.horario = horario;
        this.nomeReceptor = nomeReceptor;
        this.telefoneReceptor = telefoneReceptor;
        this.situacao = SITUACAO.PENDENTE;
        this.funcionario = funcionario;
        this.chamado = chamado;
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

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
}
