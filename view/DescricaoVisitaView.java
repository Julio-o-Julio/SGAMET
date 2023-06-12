package view;

import model.AgendamentoVisita;
import persistence.AgendamentoVisitaDAO;
import view.ViewUtils;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DescricaoVisitaView extends JFrame {
    final JButton btnOrigem;
    final JPanel panelOrigem;
    final AgendamentoVisita agdtoOrigem;
    public DescricaoVisitaView(Dimension tamanho, JButton btnOrigem, JPanel panelOrigem, AgendamentoVisita agdtoOrigem){
        super();
        this.panelOrigem = panelOrigem;
        this.btnOrigem = btnOrigem;
        this.agdtoOrigem = agdtoOrigem;

        this.setPreferredSize(tamanho);
        this.setLayout(new FlowLayout());

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnRegistrar = new JButton("Registrar");


        btnCancelar.addActionListener(e -> {
            this.dispose();
        });

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));

        JPanel btnsPanel = new JPanel();
        btnsPanel.setLayout(new FlowLayout());
        btnsPanel.setPreferredSize(new Dimension(500, 40));
        btnsPanel.add(btnRegistrar);
        btnsPanel.add(btnCancelar);

        JTextArea descField = new JTextArea();
        descField.setLineWrap(true);

        JScrollPane descPane = new JScrollPane(descField);
        descPane.setPreferredSize(new Dimension(350, 300));

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String horario = this.agdtoOrigem.getHorario().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        JLabel horarioLabel = new JLabel(horario);
        horarioLabel.setHorizontalAlignment(JLabel.CENTER);

        String codChamado = String.format("Chamado Cód %d", this.agdtoOrigem.getChamado().getCodChamado());
        JLabel codLabel = new JLabel(codChamado);
        codLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel titleCont = new JPanel(new GridLayout(2,1));
        titleCont.add(codLabel);
        titleCont.add(horarioLabel);

        contentPanel.add(titleCont);

        this.add(contentPanel);
        this.add(descPane);
        this.add(btnsPanel);

        btnRegistrar.addActionListener(e -> {
            String descChamado = descField.getText();
            if(descChamado.length()>0){
                this.agdtoOrigem.setSituacao(AgendamentoVisita.SITUACAO.REALIZADA);
                if (AgendamentoVisitaDAO.inserirAgendamentoChamado(this.agdtoOrigem)) {
                    this.btnOrigem.setVisible(false);
                    this.panelOrigem.remove(this.btnOrigem);
                    this.panelOrigem.setBackground(ViewUtils.COR_REALIZADA);
                    this.panelOrigem.revalidate();
                    this.panelOrigem.repaint();
                    //TODO: Append nota de chamado com descricao ao chamado!!

                    Mensagem.showSucces("Conclusão de visita registrada com sucesso.");
                    this.dispose();
                } else {
                    Mensagem.showError("Erro ao atualizar status do chamado.");
                    this.dispose();
                }
            }else{
                Mensagem.showError("Uma descrição válida deve ser informada.");
            }
        });

        this.pack();
        this.setVisible(true);
    }

}
