import persistence.*;
import view.MainView;

import java.awt.*;


public class Main {
    private static void migrarTabelas(){
        FilialDAO.checkTable();
        ContatoDAO.checkTable();
        UsuarioDAO.checkTable();
        FuncionarioDAO.checkTable();
        ClienteDAO.checkTable();
        EquipamentoDAO.checkTable();
        ChamadoDAO.checkTable();
        AgendamentoVisitaDAO.checkTable();
        NotaChamadoDAO.checkTable();
    }
    public static void main(String[] args) {
        migrarTabelas();
        new MainView("SGAMET", new Dimension(500, 500));
    }
}