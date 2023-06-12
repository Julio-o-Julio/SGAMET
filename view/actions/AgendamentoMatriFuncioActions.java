package view.actions;

import model.Funcionario;
import persistence.FuncionarioDAO;
import view.Mensagem;

import javax.swing.*;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static view.ViewUtils.restringirParaInteiro;

public class AgendamentoMatriFuncioActions implements FocusListener {
        private final JTextField matriFuncioField;

        public AgendamentoMatriFuncioActions(JTextField matriFuncioField) {
            this.matriFuncioField = matriFuncioField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            matriFuncioField.setText(matriFuncioField.getText().replaceAll("[^\\d]", ""));
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
                    PlainDocument pd = (PlainDocument)matriFuncioField.getDocument();
                    pd.setDocumentFilter(null);
                    matriFuncioField.setDocument(pd);
                    matriFuncioField.setText(matriculaInserida.concat(" - ").concat(funcionario.getNome()));
                    restringirParaInteiro(matriFuncioField);
                }
                return;
            }
            matriFuncioField.setBorder(UIManager.getBorder("TextField.border"));
        }
}
