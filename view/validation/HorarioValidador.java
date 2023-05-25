package view.validation;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.time.LocalDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class HorarioValidador{
    private static boolean dataEhValida(String data){
        data = data.replaceAll("[^\\d]","");
        try{
            new SimpleDateFormat("dd/MM/yyyy").parse(data.trim());
            return true;
        }catch (ParseException err){
            return false;
        }
    }
    private static boolean horaEhValida(String hora) {
        hora = hora.replaceAll("[^\\d]","");
        try {
            DateTimeFormatter.ofPattern("HH:mm").parse(hora.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static boolean ehValido(JFormattedTextField horarioField) {
        String horarioText = horarioField.getText();
        if(horarioText.length() < ((MaskFormatter)horarioField.getFormatter()).getMask().length()) return false;

        String[] horario = horarioText.split("-");
        if(horario.length < 2) return false;

        return  dataEhValida(horario[0]) && horaEhValida(horario[1]);
    }
    public static LocalDateTime getValor(JFormattedTextField horarioField) {
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern(
                    ((MaskFormatter) horarioField.getFormatter()).getMask()
            );
            return LocalDateTime.parse(horarioField.getText(), formatador);
        }catch (Exception err){
            err.printStackTrace();
            return null;
        }
    }

}
