package view.actions;
import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AgendamentoCodChamadoActions implements FocusListener {
        private final JTextField codChamadoField;

        public AgendamentoCodChamadoActions(JTextField codChamadoField) {
            this.codChamadoField = codChamadoField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            // Ação a ser executada quando o JTextField ganha o foco
            String valor = codChamadoField.getText();
            System.out.println("Valor atual do JTextField:\n" + valor);
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Ação a ser executada quando o JTextField perde o foco
            String valor = codChamadoField.getText();
            System.out.println("Valor final do JTextField:\n" + valor);
        }
}
