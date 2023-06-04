package view;

import model.AgendamentoVisita;
import persistence.AgendamentoVisitaDAO;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewUtils {
    public static final Color COR_PENDENTE = Color.lightGray;
    public static final Color COR_EM_ATENDIMENTO = new Color(201, 188, 139);
    public static final Color COR_REALIZADA = new Color(130, 188, 144);
    public static final Color COR_CANCELADA = new Color(204, 153, 153);
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

    public static MaskFormatter criarMascara(String formato, char placeholder) {
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

    public static JPanel genAgdtoItemPane(AgendamentoVisita agdto) {
        JPanel itemContainer = new JPanel(new FlowLayout());
        itemContainer.setPreferredSize(new Dimension(300, 50));

        JPanel agdtoItemPane = new JPanel();
        agdtoItemPane.setPreferredSize(new Dimension(300, 50));
        agdtoItemPane.setLayout(new BoxLayout(agdtoItemPane, BoxLayout.LINE_AXIS));
        agdtoItemPane.setBackground(Color.LIGHT_GRAY);
        agdtoItemPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        LocalDateTime horaAgendamento = agdto.getHorario();
        String horaFormatada = horaAgendamento.format(DateTimeFormatter.ofPattern("hh:mm"));
        String dataFormatada = horaAgendamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel hora = new JLabel(horaFormatada);
        JLabel data = new JLabel(dataFormatada);
        String situacaoAgendamento = agdto.getSituacao();
        String acaoTitle = "";
        if (situacaoAgendamento.equals(AgendamentoVisita.SITUACAO.PENDENTE)){
            acaoTitle = "Realizar";
            agdtoItemPane.setBackground(COR_PENDENTE);
        }
        else if (situacaoAgendamento.equals(AgendamentoVisita.SITUACAO.EM_ATENDIMENTO)){
            acaoTitle = "Finalizar";
            agdtoItemPane.setBackground(COR_EM_ATENDIMENTO);
        }else if(situacaoAgendamento.equals(AgendamentoVisita.SITUACAO.CANCELADA)){
            agdtoItemPane.setBackground(COR_CANCELADA);
        }else if(situacaoAgendamento.equals(AgendamentoVisita.SITUACAO.REALIZADA)){
            agdtoItemPane.setBackground(COR_REALIZADA);
        }
        JButton actionBtn = new JButton(acaoTitle);
        agdtoItemPane.add(Box.createRigidArea(new Dimension(10, 50)));
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(data);
        verticalBox.add(hora);
        agdtoItemPane.add(verticalBox);
        agdtoItemPane.add(Box.createHorizontalGlue());
        if (!situacaoAgendamento.equals(AgendamentoVisita.SITUACAO.REALIZADA)
                && !situacaoAgendamento.equals(AgendamentoVisita.SITUACAO.CANCELADA)) {

            actionBtn.addActionListener(e -> {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                System.out.println(agdto.getSituacao());
                if(actionBtn.getText().equals("Realizar")){
                    LocalDateTime limiteInferior = agdto.getHorario().minusHours(1);
                    LocalDateTime limiteSuperior = agdto.getHorario().plusHours(1);
                    if(limiteInferior.isAfter(LocalDateTime.now())){
                        Mensagem.showError(
                                String.format(
                                        "A realização da visita não pode ser iniciada antes de %s às %sh%sm.",
                                        limiteInferior.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                        limiteInferior.format(DateTimeFormatter.ofPattern("hh")),
                                        limiteInferior.format(DateTimeFormatter.ofPattern("mm"))
                                )
                        );
                        return;
                    }
                    if(limiteSuperior.isBefore(LocalDateTime.now())){
                        Mensagem.showError("A visita selecionada já não pode ser realizada.\nPor favor, realize novo agendamento.");
                        agdto.setSituacao(AgendamentoVisita.SITUACAO.CANCELADA);
                        if(AgendamentoVisitaDAO.inserirChamado(agdto)){
                            actionBtn.setVisible(false);
                            agdtoItemPane.remove(actionBtn);
                            agdtoItemPane.setBackground(COR_CANCELADA);
                            agdtoItemPane.revalidate();
                            agdtoItemPane.repaint();
                        }else{
                            Mensagem.showError("Erro ao atualizar status do chamado.");
                            actionBtn.setEnabled(false);
                        }
                        return;
                    }
                    agdto.setSituacao(AgendamentoVisita.SITUACAO.EM_ATENDIMENTO);
                    if(AgendamentoVisitaDAO.inserirChamado(agdto)){
                        actionBtn.setText("Finalizar");
                        agdtoItemPane.setBackground(COR_EM_ATENDIMENTO);
                        itemContainer.revalidate();
                        itemContainer.repaint();
                    }
                }else{
                    new DescricaoVisitaView(new Dimension(500, 500), actionBtn, agdtoItemPane, agdto);
                }
            });

            agdtoItemPane.add(actionBtn);
        }
        agdtoItemPane.add(Box.createRigidArea(new Dimension(10, 50)));


        itemContainer.add(agdtoItemPane);
        return itemContainer;
    }
}
