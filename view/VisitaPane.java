package view;

import model.AgendamentoVisita;
import model.Chamado;
import model.Funcionario;
import view.actions.AgendamentoCodChamadoActions;
import view.actions.VisitaCodChamadoActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static view.ViewUtils.*;


public class VisitaPane extends JPanel{

    public VisitaPane() {
        super();
        Dimension defaultFieldDimension = new Dimension(400, 40);
        String[] situacoes = {"Pendente", "Em atendimento", "Atendido", "Cancelado"};
        JTextField codChamadoField = new JTextField();
        restringirParaInteiro(codChamadoField);

        JButton btnCancelar = new JButton("Cancelar");

        VisitaPane selfReference = this;
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTabbedPane jtPane = ((JTabbedPane) selfReference.getParent());
                jtPane.remove(selfReference);
            }
        });

        JPanel codChamadoPanel = criarItemPanel("Código do chamado:", codChamadoField, defaultFieldDimension);
        JPanel listContainer = new JPanel();
        JPanel listPane = new JPanel();
//        listPane.setLayout(new GridBagLayout());
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));


        AgendamentoVisita mock = new AgendamentoVisita(LocalDateTime.now(), "As", "111111111", new Chamado(1, "1", "1","11"), new Funcionario(1, "as", "as", "as", "as", "as", "as", new ArrayList<LocalDateTime>()));
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        JPanel btnsPanel = new JPanel();
        FlowLayout layoutBtnPane = new FlowLayout();
        btnsPanel.setLayout(layoutBtnPane);
        btnsPanel.setPreferredSize(new Dimension(500, 40));
        btnsPanel.add(btnCancelar);

        this.setLayout(new FlowLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(codChamadoPanel);
        contentPanel.add(new JSeparator());
        this.add(contentPanel);
        listContainer.add(listPane);
        JScrollPane scrollAgdto = new JScrollPane(listContainer);
        scrollAgdto.setPreferredSize(new Dimension(350, 300));
        this.add(scrollAgdto);
        this.add(btnsPanel);

        codChamadoField.addFocusListener(new VisitaCodChamadoActions(codChamadoField, listPane));
//        codChamadoField.addKeyListener(new KeyAdapter() {
//        });
        this.setVisible(true);
        this.requestFocus();
    }
}
