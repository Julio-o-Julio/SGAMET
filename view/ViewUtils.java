package view;

import model.AgendamentoVisita;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewUtils {
    public static void restringirParaInteiro(JTextField textField) {
        PlainDocument pd = (PlainDocument) textField.getDocument();
        pd.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str != null) {
                    if (str.matches("[\\d]"))
                        super.insertString(fb, offset, str, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
                if (str != null) {
                    if (str.matches("[\\d]"))
                        super.replace(fb, offset, length, str, attrs);
                }
            }
        });
        textField.setDocument(pd);
    }

    public static MaskFormatter criarMascara(String formato, char placeholder){
        MaskFormatter mascara = new MaskFormatter();
        try {
            mascara.setMask(formato);
            mascara.setPlaceholderCharacter(placeholder);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return mascara;
    }
    public static JPanel criarItemPanel(String title, JComponent child, Dimension tam) {

        JPanel formItemPanel = new JPanel();
        GridLayout gl = new GridLayout(0, 2);
        formItemPanel.setLayout(gl);
        formItemPanel.setPreferredSize(tam);
        JLabel titleLabel = new JLabel(title);

        formItemPanel.add(titleLabel);
        formItemPanel.add(child);
        return formItemPanel;
    }
    public static JPanel genAgdtoItemPane(AgendamentoVisita agdto){
        JPanel itemContainer = new JPanel(new FlowLayout());
        itemContainer.setPreferredSize(new Dimension(300, 50));

        JPanel agdtoItemPane = new JPanel();
        agdtoItemPane.setPreferredSize(new Dimension(300, 50));
        agdtoItemPane.setLayout(new BoxLayout(agdtoItemPane, BoxLayout.LINE_AXIS));
        agdtoItemPane.setBackground(Color.LIGHT_GRAY);

        LocalDateTime horaAgendamento = agdto.getHorario();
        String horaFormatada = horaAgendamento.format(DateTimeFormatter.ofPattern("hh:mm"));
        String dataFormatada = horaAgendamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel hora = new JLabel(horaFormatada);
        JLabel data = new JLabel(dataFormatada);
        JLabel atendente = new JLabel(agdto.getFuncionario().getNome());
        agdtoItemPane.add(Box.createRigidArea(new Dimension(10,50)));
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(data);
        verticalBox.add(hora);
        agdtoItemPane.add(verticalBox);
        agdtoItemPane.add(Box.createHorizontalGlue());
        agdtoItemPane.add(atendente);
        agdtoItemPane.add(Box.createRigidArea(new Dimension(10,50)));


        itemContainer.add(agdtoItemPane);
        return itemContainer;
    }
}
