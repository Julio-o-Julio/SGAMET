package view;

import view.actions.VisitaCodChamadoActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static view.ViewUtils.*;


public class VisitaPane extends JPanel{

    public VisitaPane() {
        super();
        Dimension defaultFieldDimension = new Dimension(400, 40);
        String[] situacoes = {"Pendente", "Em atendimento", "Atendido", "Cancelado"};
        JTextField codChamadoField = new JTextField();
        restringirParaInteiro(codChamadoField);

        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> {
            JTabbedPane jtPane = ((JTabbedPane) this.getParent());
            jtPane.remove(this);
        });

        JPanel codChamadoPanel = criarItemPanel("Código do chamado:", codChamadoField, defaultFieldDimension);
        JPanel listContainer = new JPanel();
        JPanel listPane = new JPanel();
//        listPane.setLayout(new GridBagLayout());
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));

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

        codChamadoField.addActionListener(e -> {
            if (e.getModifiers() == 0 && e.getID() == ActionEvent.ACTION_PERFORMED) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                codChamadoField.requestFocus();
            }
        });
        scrollAgdto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
            }
        });
        this.setVisible(true);
        this.requestFocus();
    }
}
