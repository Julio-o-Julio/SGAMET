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
        JPanel agdtoItemPane = new JPanel();
        agdtoItemPane.setLayout(new GridBagLayout());
        agdtoItemPane.setPreferredSize(new Dimension(300, 50));
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridy=0;
        constraint.gridx=0;

        LocalDateTime horaAgendamento = agdto.getHorario();
        String horaFormatada = horaAgendamento.format(DateTimeFormatter.ofPattern("hh:mm"));
        JLabel hora = new JLabel(horaFormatada);
        agdtoItemPane.add(hora);

        return agdtoItemPane;
    }
    public VisitaPane() {
        super();
        Dimension defaultFieldDimension = new Dimension(400, 40);
        String[] situacoes = {"Pendente", "Em atendimento", "Atendido", "Cancelado"};
        JTextField codChamadoField = new JTextField();
        restringirParaInteiro(codChamadoField);


        JButton btnAgendar = new JButton("Agendar");
        JButton btnAtualizar = new JButton("Atualizar");
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
        listContainer.setLayout(new BoxLayout(listContainer,BoxLayout.Y_AXIS));
        AgendamentoVisita mock = new AgendamentoVisita(LocalDateTime.now(), "As", "111111111", new Chamado(1, "1", "1","11"), new Funcionario(1, "as", "as", "as", "as", "as", "as", new ArrayList<LocalDateTime>()));
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        JPanel btnsPanel = new JPanel();
        GridLayout layoutBtnPane = new GridLayout(1, 3);
        layoutBtnPane.setHgap(5);

        btnsPanel.setLayout(layoutBtnPane);
        btnsPanel.add(btnAgendar);
        btnsPanel.add(btnAtualizar);
        btnsPanel.add(btnCancelar);

        this.setLayout(new FlowLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(codChamadoPanel);
        contentPanel.add(new JSeparator());
        this.add(contentPanel);
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));

        listContainer.add(genAgdtoItemPane(mock));

        listContainer.add(genAgdtoItemPane(mock));

        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));
        listContainer.add(genAgdtoItemPane(mock));


        JScrollPane scrollAgdto = new JScrollPane(listContainer);
        scrollAgdto.setPreferredSize(new Dimension(350, 300));
        this.add(scrollAgdto);
        this.add(btnsPanel);



        this.setVisible(true);
    }
}
