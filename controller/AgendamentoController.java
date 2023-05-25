package controller;

import java.time.LocalDateTime;

import dao.FuncionarioDAO;
import dao.AgendamentoVisitaDAO;
import dao.ChamadoDAO;
import model.Funcionario;
import model.AgendamentoVisita;
import model.Chamado;

public class AgendamentoController {

   Funcionario funcionario;
   Chamado chamado;
   public Funcionario buscarFuncionario(int matricula) {
       return FuncionarioDAO.searchQuery(matricula);
   }
   public boolean existeFuncionario(int matricula){
        return FuncionarioDAO.searchQuery(matricula)!=null;
   }

   public boolean consultaDisponibilidadeHorarios(int nroMatricula, LocalDateTime horarioDesejado) {

       funcionario = this.buscarFuncionario(nroMatricula);

       for (LocalDateTime horario : funcionario.getHorAgendados()) {
           LocalDateTime inicioIntervalo = horarioDesejado.minusHours(1);
           LocalDateTime fimIntervalo = horarioDesejado.plusHours(1);

           if (horario.isAfter(inicioIntervalo) && horario.isBefore(fimIntervalo)) {
               return false;
           }
       }

       return true;
   }

   public boolean existeChamado(int codChamado) {
       return ChamadoDAO.searchQuery(codChamado)!=null;
   }

   public Chamado buscarChamado(int codChamado) {
       return ChamadoDAO.searchQuery(codChamado);
   }

   public void registrarAgendamento(Chamado chamado, LocalDateTime horario, Funcionario funcionario, String nomeRes, String telefoneRes) {
       AgendamentoVisita agendamento = new AgendamentoVisita(horario, nomeRes, telefoneRes, chamado, funcionario);
       funcionario.adicionarHorarioAgendado(agendamento.getHorario());
       AgendamentoVisitaDAO.insert(agendamento);
       FuncionarioDAO.update(funcionario);
   }
}