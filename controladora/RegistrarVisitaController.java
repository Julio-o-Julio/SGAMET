package controladora;

import model.AgendamentoVisita;
import persistence.AgendamentoVisitaDAO;
import persistence.ChamadoDAO;

import java.util.ArrayList;

public class RegistrarVisitaController{
    public static ArrayList<AgendamentoVisita> getAgendamentosChamado(int codChamado) throws Exception{
        if(ChamadoDAO.pesquisarChamado(codChamado)!=null)
            return AgendamentoVisitaDAO.pesquisarAgtVisita(codChamado);
        else
            throw new Exception(String.format("Chamado nao encontrado a partir do codigo %d", codChamado));
    }
}