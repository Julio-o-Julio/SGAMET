package view.validation;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.time.LocalDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class HorarioValidador extends Validador<JFormattedTextField, LocalDateTime>{
    private boolean dataEhValida(String data){
        try{
            new SimpleDateFormat("dd/MM/yyyy").parse(data);
            return true;
        }catch (ParseException err){
            return false;
        }
    }
    public static boolean horaEhValida(String hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            formatter.parse(hora);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    @Override
    public boolean ehValido(JFormattedTextField horarioField) {
        String horarioText = horarioField.getText();
        if(horarioText.length() < ((MaskFormatter)horarioField.getFormatter()).getMask().length()) return false;
        String[] horario = horarioText.split("-");
        if(horario.length < 2) return false;
        return  dataEhValida(horario[0]) && horaEhValida(horario[1]);
    }
    @Override
    public LocalDateTime getValor() {
        return null;
    }

}
