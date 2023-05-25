import java.time.LocalDateTime;
import java.util.ArrayList;

class Funcionario {
    private int nroMatricula;
    private String nome;
    private String cargo;
    private String pais;
    private String estado;
    private String cidade;
    private String telefone;
    public ArrayList horAgendados;

  public Funcionario(int nroMatricula, String nome, String cargo, String pais, String estado, String cidade,
      String telefone, ArrayList<LocalDateTime> horAgendados) {
    this.nroMatricula = nroMatricula;
    this.nome = nome;
    this.cargo = cargo;
    this.pais = pais;
    this.estado = estado;
    this.cidade = cidade;
    this.telefone = telefone;
    this.horAgendados = new ArrayList<>();
  }

  public int getNroMatricula() {
    return nroMatricula;
  }

  public void setNroMatricula(int nroMatricula) {
    this.nroMatricula = nroMatricula;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
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

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public ArrayList<LocalDateTime> getHorAgendados() {
    return horAgendados;
  }

  public void setHorAgendados(ArrayList<LocalDateTime> horAgendados) {
    this.horAgendados = horAgendados;
  }

  public void adicionarHorarioAgendado(LocalDateTime horario) {
        horAgendados.add(horario);
  }

  public void removerHorarioAgendado(LocalDateTime horario) {
    horAgendados.remove(horario);
  }
}