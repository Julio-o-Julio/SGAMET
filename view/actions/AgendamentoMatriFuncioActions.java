package view.actions;
import model.Funcionario;
import persistence.FuncionarioDAO;
import view.Mensagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AgendamentoMatriFuncioActions implements FocusListener {
        private final JTextField matriFuncioField;

        public AgendamentoMatriFuncioActions(JTextField matriFuncioField) {
            this.matriFuncioField = matriFuncioField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            return;
        }

        @Override
        public void focusLost(FocusEvent e) {
            String matriculaInserida = matriFuncioField.getText();
            if(matriculaInserida.length()>0){
                Funcionario funcionario = FuncionarioDAO.pesquisarFuncionario(Integer.parseInt(matriculaInserida));
                if(funcionario==null){
                    matriFuncioField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                }else{
                    matriFuncioField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                }
                return;
            }
            matriFuncioField.setBorder(UIManager.getBorder("TextField.border"));
        }
}
