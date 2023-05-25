package view;

import view.actions.AgendamentoViewCRUDactions;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;

import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AgendamentoView extends JFrame{
    private static void restringirParaInteiro(JTextField textField) {
        textField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str != null) {
                    if(str.matches("[\\d]"))
                        super.insertString(offset, str, attr);
                }
            }
        });
    }

    private MaskFormatter criarMascara(String formato, char placeholder){
        MaskFormatter mascara = new MaskFormatter();
        try {
            mascara.setMask(formato);
            mascara.setPlaceholderCharacter(placeholder);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return mascara;
    }
    private static JPanel criarItemPanel(String title, JComponent child, Dimension tam) {

        JPanel formItemPanel = new JPanel();
        GridLayout gl = new GridLayout(0, 2);
        formItemPanel.setLayout(gl);
        formItemPanel.setPreferredSize(tam);
        JLabel titleLabel = new JLabel(title);

        formItemPanel.add(titleLabel);
        formItemPanel.add(child);
        return formItemPanel;
    }
    public AgendamentoView(String titulo, Dimension tamanho) {
        super();
        Dimension defaultFieldDimension = new Dimension(400, 40);
        Dimension halfFieldDimension = new Dimension(200, 40);

        String[] situacoes = {"Pendente", "Atendido"};
        JTextField nomeField = new JTextField();
        JTextField codChamadoField = new JTextField();
        JTextField matriculaFuncionario = new JTextField();
        restringirParaInteiro(codChamadoField);
        restringirParaInteiro(matriculaFuncionario);
        JFormattedTextField telefoneField = new JFormattedTextField(criarMascara("(##) # ####-####", 'X'));
        JFormattedTextField horarioField = new JFormattedTextField(criarMascara("##/##/## - ##h:##m", 'X'));
        JComboBox<String> situacao = new JComboBox<>(situacoes);

        JButton btnAgendar = new JButton("Agendar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnCancelar = new JButton("Cancelar");
        AgendamentoViewCRUDactions actionListenerCrud = new AgendamentoViewCRUDactions(nomeField,
                codChamadoField,
                telefoneField,
                horarioField,
                situacao,
                btnAgendar,
                btnAtualizar);
        btnAgendar.addActionListener(actionListenerCrud);
        btnAtualizar.addActionListener(actionListenerCrud);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel matriculaFuncio = criarItemPanel("Matrícula funcionário", matriculaFuncionario, defaultFieldDimension);
        JPanel codChamadoPanel = criarItemPanel("Código do chamado:", codChamadoField, defaultFieldDimension);
        JPanel horarioPanel = criarItemPanel("Horário:", horarioField, defaultFieldDimension);
        JPanel nomePanel = criarItemPanel("Nome receptor:", nomeField, defaultFieldDimension);
        JPanel telefonePanel = criarItemPanel("Telefone receptor:", telefoneField, defaultFieldDimension);
        JPanel situacaoPanel = criarItemPanel("Situação chamado:", situacao, defaultFieldDimension);

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        JPanel btnsPanel = new JPanel();
        GridLayout layoutBtnPane = new GridLayout(1, 3);
        layoutBtnPane.setHgap(5);

        btnsPanel.setLayout(layoutBtnPane);
        btnsPanel.add(btnAgendar);
        btnsPanel.add(btnAtualizar);
        btnsPanel.add(btnCancelar);

        this.setTitle(titulo);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(tamanho);

        contentPanel.add(codChamadoPanel);
        contentPanel.add(matriculaFuncio);
        contentPanel.add(horarioPanel);
        contentPanel.add(nomePanel);
        contentPanel.add(telefonePanel);
        contentPanel.add(situacaoPanel);
        contentPanel.add(new JSeparator());



        contentPanel.add(btnsPanel);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(contentPanel);
        this.setVisible(true);
    }
    public static void main(String[] args) {

        new AgendamentoView("SGAMET", new Dimension(500,500));

    }
}
