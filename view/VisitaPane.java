package view;

import model.AgendamentoVisita;
import model.Chamado;
import model.Funcionario;
import view.actions.AgendamentoCodChamadoActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static view.ViewUtils.*;


public class VisitaPane extends JPanel{
    private JPanel genAgdtoItemPane(AgendamentoVisita agdto){
        JPanel itemContainer = new JPanel();
        itemContainer.setPreferredSize(new Dimension(300, 50));

        JPanel agdtoItemPane = new JPanel();
        agdtoItemPane.setPreferredSize(new Dimension(300, 50));
        agdtoItemPane.setLayout(new GridBagLayout());
        agdtoItemPane.setBackground(Color.BLUE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy=0;
        constraints.gridx=0;

        LocalDateTime horaAgendamento = agdto.getHorario();
        String horaFormatada = horaAgendamento.format(DateTimeFormatter.ofPattern("hh:mm"));
        JLabel hora = new JLabel(horaFormatada);
        agdtoItemPane.add(hora);

        itemContainer.add(agdtoItemPane);
        return itemContainer;
    }
    public VisitaPane() {
        super();
        Dimension defaultFieldDimension = new Dimension(400, 40);
        String[] situacoes = {"Pendente", "Em atendimento", "Atendido", "Cancelado"};
        JTextField codChamadoField = new JTextField();
        restringirParaInteiro(codChamadoField);

        JButton btnCancelar = new JButton("Cancelar");

        codChamadoField.addFocusListener(new AgendamentoCodChamadoActions(codChamadoField));
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

        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));

        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));
        listPane.add(genAgdtoItemPane(mock));








        listContainer.add(listPane);
        JScrollPane scrollAgdto = new JScrollPane(listContainer);
        scrollAgdto.setPreferredSize(new Dimension(350, 300));
        this.add(scrollAgdto);
        this.add(btnsPanel);

        this.setVisible(true);
    }
}
