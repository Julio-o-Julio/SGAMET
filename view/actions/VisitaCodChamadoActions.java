package view.actions;
import model.AgendamentoVisita;
import model.Chamado;
import persistence.AgendamentoVisitaDAO;
import persistence.ChamadoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Comparator;

import static view.ViewUtils.genAgdtoItemPane;

public class VisitaCodChamadoActions implements FocusListener {
        private final JTextField codChamadoField;
        private final JPanel listPanel;

        public VisitaCodChamadoActions(JTextField codChamadoField, JPanel list) {
            this.codChamadoField = codChamadoField;
            this.listPanel = list;
        }

        @Override
        public void focusGained(FocusEvent e) {
            return;
        }
        @Override
        public void focusLost(FocusEvent e) {
            String codChamadoInserido = codChamadoField.getText();
            if(codChamadoInserido.length()>0){
                Chamado chamado = ChamadoDAO.pesquisarChamado(Integer.parseInt(codChamadoInserido));
                if(chamado==null){
                    codChamadoField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    this.listPanel.removeAll();
                }else{
                    codChamadoField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                    this.listPanel.removeAll();
                    ArrayList<AgendamentoVisita> listaAgendamentos = AgendamentoVisitaDAO.pesquisarAgtVisita(chamado.getCodChamado());
                    listaAgendamentos.sort(Comparator
                            .comparing((AgendamentoVisita o) -> o.getSituacao().equals(AgendamentoVisita.STATUS.CANCELADA))
                            .thenComparing(AgendamentoVisita::getSituacao));
                    listaAgendamentos.forEach(agendamentoVisita ->{
                        this.listPanel.add(genAgdtoItemPane(agendamentoVisita));
                    });
                }
                this.listPanel.repaint();
                this.listPanel.revalidate();
                return;
            }
            codChamadoField.setBorder(UIManager.getBorder("TextField.border"));
            this.listPanel.removeAll();
            this.listPanel.repaint();
            this.listPanel.revalidate();
        }
}
