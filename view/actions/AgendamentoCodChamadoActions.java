package view.actions;
import model.Chamado;
import persistence.ChamadoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AgendamentoCodChamadoActions implements FocusListener {
        private final JTextField codChamadoField;

        public AgendamentoCodChamadoActions(JTextField codChamadoField) {
            this.codChamadoField = codChamadoField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            return;
        }
        @Override
        public void focusLost(FocusEvent e) {
            String codChamadoInserido = codChamadoField.getText();
            if(codChamadoInserido.length()>0){
                Chamado chamado = ChamadoDAO.pesquisarChamado(Integer.parseInt(codChamadoInserido));
                if(chamado==null){
                    codChamadoField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                }else{
                    codChamadoField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                }
                return;
            }
            codChamadoField.setBorder(UIManager.getBorder("TextField.border"));
        }
}
