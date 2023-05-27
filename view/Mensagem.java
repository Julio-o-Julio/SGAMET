package view;

import javax.swing.*;

public class Mensagem {
    public static void showError(String mensagemErr){
        JOptionPane.showMessageDialog(null, mensagemErr, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    public static void showSucces(String mensagemErr){
        JOptionPane.showMessageDialog(null, mensagemErr, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
