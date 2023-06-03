package view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

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
}
