package controladora;
import java.time.LocalDateTime;

import persistence.FuncionarioDAO;
import persistence.AgendamentoVisitaDAO;
import persistence.ChamadoDAO;
import model.Funcionario;
import model.AgendamentoVisita;
import model.Chamado;

public class AgendamentoController {

   Funcionario funcionario;
   Chamado chamado;
   public static Funcionario buscarFuncionario(int matricula) {
        return FuncionarioDAO.pesquisarFuncionario(matricula);
   }
   public static boolean existeFuncionario(int matricula){
        return FuncionarioDAO.pesquisarFuncionario(matricula) != null;
   }

   public static boolean consultaDisponibilidadeHorarios(int nroMatricula, LocalDateTime horarioDesejado) {

       Funcionario funcionario = buscarFuncionario(nroMatricula);

       for (LocalDateTime horario : funcionario.getHorAgendados()) {
           LocalDateTime inicioIntervalo = horarioDesejado.minusHours(1);
           LocalDateTime fimIntervalo = horarioDesejado.plusHours(1);

           if (horario.isAfter(inicioIntervalo) && horario.isBefore(fimIntervalo)) {
               return false;
           }
       }

       return true;
   }

   public static boolean existeChamado(int codChamado) {
       return ChamadoDAO.pesquisarChamado(codChamado)!=null;
   }

   public static Chamado buscarChamado(int codChamado) {
       return ChamadoDAO.pesquisarChamado(codChamado);
   }

   public static void registrarAgendamento(Chamado chamado, LocalDateTime horario, Funcionario funcionario, String nomeRes, String telefoneRes) {
       AgendamentoVisita agendamento = new AgendamentoVisita(horario, nomeRes, telefoneRes, chamado, funcionario);
       funcionario.adicionarHorarioAgendado(agendamento.getHorario());
       AgendamentoVisitaDAO.inserirAgendamentoChamado(agendamento);
       FuncionarioDAO.inserirFuncionario(funcionario);
   }
}