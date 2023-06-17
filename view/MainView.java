package view;

import javax.swing.*;
import java.awt.*;


class ManagerPane extends JPanel{

    public ManagerPane(JTabbedPane tabPanel){
        super();
        this.setLayout(new GridBagLayout());
        JLabel titleLabel = new JLabel("SGAMET");
        JButton realizarAgdto = new JButton("Realizar agendamento");
        JButton regVisita = new JButton("Registrar visita");

        realizarAgdto.addActionListener(e->{
            int idxPaneDesejado = tabPanel.indexOfTab("Realizar agendamento");
            if(idxPaneDesejado != -1){
                tabPanel.setSelectedIndex(idxPaneDesejado);
                return;
            }
            tabPanel.addTab("Realizar agendamento", new AgendamentoPane());
            tabPanel.setSelectedIndex(tabPanel.getTabCount()-1);
        });

        regVisita.addActionListener(e->{
            int idxPaneDesejado = tabPanel.indexOfTab("Registrar visita");
            if(idxPaneDesejado != -1){
                tabPanel.setSelectedIndex(idxPaneDesejado);
                return;
            }
            tabPanel.addTab("Registrar visita", new VisitaPane());
            tabPanel.setSelectedIndex(tabPanel.getTabCount()-1);
        });
        JPanel btnsPanel = new JPanel();
        btnsPanel.add(realizarAgdto);
        btnsPanel.add(regVisita);

        titleLabel.setFont(new Font(titleLabel.getName(), Font.BOLD, 40));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        this.add(titleLabel, constraints);

        constraints.gridy=1;

        this.add(btnsPanel, constraints);

    }
}
public class MainView extends JFrame {
    public MainView(String title, Dimension size){
        this.setTitle(title);
        this.setPreferredSize(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        JTabbedPane mainPane = new JTabbedPane();
        ManagerPane welcomeView = new ManagerPane(mainPane);
        mainPane.addTab("Home",welcomeView);
//        this.add(new JLabel("AAAAAA"));
//
//        mainPane.addTab("Realizar agendamento", new AgendamentoView(title,size));
        this.pack();
        this.add(mainPane);
        this.setVisible(true);

    }
}