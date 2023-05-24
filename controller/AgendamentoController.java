package controller;

import java.time.LocalDateTime;

import model.Funcionario;
import model.Chamado;

public class AgendamentoController {

    public Funcionario buscarFuncionario(int matricula) {
        return null;
    }

    public boolean consultaDisponibilidadeHorario(int nroMatricula) {
        return true;
    }

    public boolean buscarChamado(int codChamado) {
        return true;
    }

    public void registrarAgendamento(Chamado chamado, LocalDateTime horario, Funcionario funcionario, String nomeRes, String telefoneRes) {

    }
}