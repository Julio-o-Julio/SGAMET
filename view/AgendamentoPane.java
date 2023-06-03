package view;

import view.actions.AgendamentoCodChamadoActions;
import view.actions.AgendamentoMatriFuncioActions;
import view.actions.AgendamentoViewCRUDactions;

import javax.swing.*;
import java.awt.*;

import static view.ViewUtils.*;


public class AgendamentoPane extends JPanel{
    public AgendamentoPane() {
        super();
        Dimension defaultFieldDimension = new Dimension(400, 40);
        JTextField nomeField = new JTextField();
        JTextField codChamadoField = new JTextField();
        JTextField matriculaFuncionarioField = new JTextField();
        restringirParaInteiro(codChamadoField);
        restringirParaInteiro(matriculaFuncionarioField);
        JFormattedTextField telefoneField = new JFormattedTextField(criarMascara("(##) # ####-####", 'X'));
        JFormattedTextField horarioField = new JFormattedTextField(criarMascara("##/##/#### - ##h:##m", 'X'));

        JButton btnAgendar = new JButton("Agendar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnCancelar = new JButton("Cancelar");

        AgendamentoViewCRUDactions actionListenerCrud = new AgendamentoViewCRUDactions(nomeField,
                codChamadoField,
                matriculaFuncionarioField,
                telefoneField,
                horarioField,
                btnAgendar,
                btnAtualizar);
        codChamadoField.addFocusListener(new AgendamentoCodChamadoActions(codChamadoField));
        matriculaFuncionarioField.addFocusListener(new AgendamentoMatriFuncioActions(matriculaFuncionarioField));
        btnAgendar.addActionListener(actionListenerCrud);
        btnAtualizar.addActionListener(actionListenerCrud);

        btnCancelar.addActionListener(e -> {
            JTabbedPane jtPane = ((JTabbedPane) this.getParent());
            jtPane.remove(this);
        });

        JPanel matriculaFuncio = criarItemPanel("Matrícula funcionário", matriculaFuncionarioField, defaultFieldDimension);
        JPanel codChamadoPanel = criarItemPanel("Código do chamado:", codChamadoField, defaultFieldDimension);
        JPanel horarioPanel = criarItemPanel("Data e horário:", horarioField, defaultFieldDimension);
        JPanel nomePanel = criarItemPanel("Nome receptor:", nomeField, defaultFieldDimension);
        JPanel telefonePanel = criarItemPanel("Telefone receptor:", telefoneField, defaultFieldDimension);
        //JPanel situacaoPanel = criarItemPanel("Situação chamado:", situacao, defaultFieldDimension);

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        JPanel btnsPanel = new JPanel();
        GridLayout layoutBtnPane = new GridLayout(1, 3);
        layoutBtnPane.setHgap(5);

        btnsPanel.setLayout(layoutBtnPane);
        btnsPanel.add(btnAgendar);
        btnsPanel.add(btnAtualizar);
        btnsPanel.add(btnCancelar);

        this.setLayout(new GridLayout());

        contentPanel.add(codChamadoPanel);
        contentPanel.add(nomePanel);
        contentPanel.add(telefonePanel);
        contentPanel.add(matriculaFuncio);
        contentPanel.add(horarioPanel);
        //contentPanel.add(situacaoPanel); //TODO: não apresentar em Agendamento!
        contentPanel.add(new JSeparator());

        contentPanel.add(btnsPanel);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(contentPanel);
        this.setVisible(true);
    }
}
