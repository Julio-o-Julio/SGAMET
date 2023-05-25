//package controller;
//
//import java.time.LocalDateTime;
//
//import dao.FuncionarioDAO;
//import dao.ChamadoDAO;
//import model.Funcionario;
//import model.AgendamentoVisita;
//import model.Chamado;
//
//public class AgendamentoController {
//
//    Funcionario funcionario;
//    Chamado chamado;
//
//    public Funcionario buscarFuncionario(int matricula) {
//        return pesquisarFuncionario(matricula);
//    }
//
//    public boolean consultaDisponibilidadeHorarios(int nroMatricula, LocalDateTime horarioDesejado) {
//
//        funcionario = this.buscarFuncionario(nroMatricula);
//
//        for (LocalDateTime horario : funcionario.getHorAgendados()) {
//            LocalDateTime inicioIntervalo = horarioDesejado.minusHours(1);
//            LocalDateTime fimIntervalo = horarioDesejado.plusHours(1);
//
//            if (horario.isAfter(inicioIntervalo) && horario.isBefore(fimIntervalo)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean buscarChamado(int codChamado) {
//
//        if (ChamadoDAO.pesquisarChamado(codChamado)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    public void registrarAgendamento(Chamado chamado, LocalDateTime horario, Funcionario funcionario, String nomeRes, String telefoneRes) {
//        AgendamentoVisita agendamento = new AgendamentoVisita(chamado.getCodChamado(), funcionario.getNroMatricula(), horario, nomeRes, telefoneRes);
//    }
//}