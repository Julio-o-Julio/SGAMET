import model.Funcionario;
import persistence.FuncionarioDAO;
import view.MainView;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new MainView("SGAMET", new Dimension(500, 500));
    }
}