package view.actions;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgendamentoViewCRUDactions implements ActionListener {
    JTextField nomeField;
    JTextField codChamadoField;
    JFormattedTextField telefoneField;
    JFormattedTextField horarioField;
    JComboBox<String> situacao;
    JButton btnAgendar;
    JButton btnAtualizar;
    public AgendamentoViewCRUDactions(
            JTextField nomeField,
            JTextField codChamadoField,
            JFormattedTextField telefoneField,
            JFormattedTextField horarioField,
            JComboBox<String> situacao,
            JButton btnAgendar,
            JButton btnAtualizar
    ) {
        this.nomeField = nomeField;
        this.codChamadoField = codChamadoField;
        this.telefoneField = telefoneField;
        this.horarioField = horarioField;
        this.situacao = situacao;
        this.btnAgendar = btnAgendar;
        this.btnAtualizar = btnAtualizar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println( e);

        if(e.getSource().equals(btnAgendar)){
            System.out.println("AGENDADO");
        }
        else if(e.getSource().equals(btnAtualizar)){
            System.out.println("Atualizado!!");
        }
    }
}
