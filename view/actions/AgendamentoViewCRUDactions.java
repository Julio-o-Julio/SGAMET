package view.actions;


import javax.swing.*;

import controladora.AgendamentoController;
import model.Chamado;
import model.Funcionario;
import view.Mensagem;
import view.validation.HorarioValidador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AgendamentoViewCRUDactions implements ActionListener {
    JTextField nomeField;
    JTextField codChamadoField;
    JTextField matriculaFuncionarioField;
    JFormattedTextField telefoneField;
    JFormattedTextField horarioField;
    JComboBox<String> situacao;
    JButton btnAgendar;
    JButton btnAtualizar;
    public AgendamentoViewCRUDactions(
            JTextField nomeField,
            JTextField codChamadoField,
            JTextField matriculaFuncionarioField,
            JFormattedTextField telefoneField,
            JFormattedTextField horarioField,
            JComboBox<String> situacao,
            JButton btnAgendar,
            JButton btnAtualizar
    ) {
        this.nomeField = nomeField;
        this.codChamadoField = codChamadoField;
        this.matriculaFuncionarioField = matriculaFuncionarioField;
        this.telefoneField = telefoneField;
        this.horarioField = horarioField;
        this.situacao = situacao;
        this.btnAgendar = btnAgendar;
        this.btnAtualizar = btnAtualizar;
    }
    private boolean fieldsValidos(){
        if(!(codChamadoField.getText().length() > 0)){
            return false;
        }
        if(!(matriculaFuncionarioField.getText().length()>0)){
            return false;
        }
        if(! (nomeField.getText().length() > 0)){
            return false;
        }
        if(! (telefoneField.getText().length() > 0)){
            return false;
        }
        if(!HorarioValidador.ehValido(horarioField)){
            return false;
        }
        return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!fieldsValidos()) return;

        if(e.getSource().equals(btnAgendar)){
            int codChamado = Integer.parseInt( codChamadoField.getText() );
            if(!AgendamentoController.existeChamado(codChamado)){
                Mensagem.showError("Chamado não existe!");
                return;
            }
            Chamado chamado = AgendamentoController.buscarChamado(codChamado);

            int matriculaFuncionario = Integer.parseInt(matriculaFuncionarioField.getText());
            if(!AgendamentoController.existeFuncionario(matriculaFuncionario)){
                Mensagem.showError("Nenhum funcionário pôde ser encontrado a partir da matrícula informada");
                return;
            }
            Funcionario funcionario = AgendamentoController.buscarFuncionario(matriculaFuncionario);
            String nomeRes = nomeField.getText();
            String telefoneRes = telefoneField.getText();
            LocalDateTime horario = HorarioValidador.getValor(horarioField);
            AgendamentoController.registrarAgendamento(chamado, horario, funcionario, nomeRes, telefoneRes); 
        }
        else if(e.getSource().equals(btnAtualizar)){
            System.out.println("Atualizado!!");
        }
    }
}
