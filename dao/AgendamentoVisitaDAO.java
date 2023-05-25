package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AgendamentoVisita;
import model.Chamado;
import model.Funcionario;


public class AgendamentoVisitaDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
    private LocalDateTime horario;
    private String nomeReceptor;
    private String telefoneReceptor;
    private String situacao;

    private Chamado chamado;
    private Funcionario funcionario;
	
create table if not exists AgendamentoVisita (
	codChamado INTEGER,
	nomere VARCHAR(255),
    telefonere VARCHAR(225),
    situacao VARCHAR(225),
    codfunc INTEGER,
    horaAgendamento TIMESTAMP,
    PRIMARY KEY (codchamado, horaagendamento)
); 
	 */
	
	private static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists AgendamentoVisita ("
            		+ "	codChamado INTEGER,"
            		+ "	nomere VARCHAR(255),"
            		+ " telefonere VARCHAR(225),"
            		+ " situacao VARCHAR(225),"
            		+ " codfunc INTEGER,"
            		+ " horaAgendamento TIMESTAMP,"
            		+ " PRIMARY KEY (codchamado, horaagendamento))");
            statementInsercao.execute();
		} catch(SQLException e) {
            System.out.println("Erro ao tentar criar tabela usuario !");
            e.printStackTrace();
        } finally {
            try {
                conexaoPadrao.close();
            } catch (SQLException e) {
                System.out.println("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
            }
        }
	}

    public static int insert(AgendamentoVisita agendamentoVisita) throws SQLException {
    	AgendamentoVisitaDAO.checkTable();
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO AgendamentoVisita (codChamado, nomere, telefonere, situacao, codfunc, horaAgendamento) VALUES (?,?,?,?,?,?)"
            );

            statementInsercao.setInt(1, agendamentoVisita.getChamado().getCodChamado());
            statementInsercao.setString(2, agendamentoVisita.getNomeReceptor());
            statementInsercao.setString(3, agendamentoVisita.getTelefoneReceptor());
            statementInsercao.setString(4, (agendamentoVisita.getSituacao() == null)? "" : agendamentoVisita.getSituacao());
            statementInsercao.setInt(5, agendamentoVisita.getFuncionario().getNroMatricula());
            statementInsercao.setTimestamp(6, Timestamp.valueOf(agendamentoVisita.getHorario()));

            qtdLinhasAfetadas = statementInsercao.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao tentar criar usuário!");
            e.printStackTrace();
        } finally {
            try {
                conexaoPadrao.close();
            } catch (SQLException e) {
                System.out.println("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
            }
        }

        return qtdLinhasAfetadas;
    }


    public static void update(AgendamentoVisita agendamentoVisita) throws SQLException {
    	AgendamentoVisitaDAO.checkTable();
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE AgendamentoVisita SET codChamado = ?, nomere = ?, telefonere = ?, situacao = ?, codfunc = ?, horaAgendamento = ? " +
                    "WHERE horaAgendamento = ? and codChamado = ?"
            );

            statementInsercao.setInt(1, agendamentoVisita.getChamado().getCodChamado());
            statementInsercao.setString(2, agendamentoVisita.getNomeReceptor());
            statementInsercao.setString(3, agendamentoVisita.getTelefoneReceptor());
            statementInsercao.setString(4, (agendamentoVisita.getSituacao() == null)? "" : agendamentoVisita.getSituacao());
            statementInsercao.setInt(5, agendamentoVisita.getFuncionario().getNroMatricula());
            statementInsercao.setTimestamp(6, Timestamp.valueOf(agendamentoVisita.getHorario()));
            statementInsercao.setTimestamp(7, Timestamp.valueOf(agendamentoVisita.getHorario()));
            statementInsercao.setInt(8, agendamentoVisita.getChamado().getCodChamado());

            statementInsercao.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao tentar atualizar usuário!");
            e.printStackTrace();
        } finally {
            try {
                conexaoPadrao.close();
            } catch (SQLException e) {
                System.out.println("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
            }
        }
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static ArrayList<AgendamentoVisita> selectAll() throws SQLException{
    	AgendamentoVisitaDAO.checkTable();
		ArrayList<AgendamentoVisita> arrayRes = new ArrayList<AgendamentoVisita>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM AgendamentoVisita");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("codChamado"));
				Funcionario func = FuncionarioDAO.searchQuery(tuplasRes.getInt("codfunc"));
                arrayRes.add(new AgendamentoVisita(tuplasRes.getTimestamp("horaAgendamento").toLocalDateTime(), 
                		tuplasRes.getString("nomere"), 
                		tuplasRes.getString("telefonere"), 
                		tuplasRes.getString("situacao"),
						ch, func
                		));
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro na execução da query de consulta: " + e.getMessage());
		} finally {			
			try {
				conexaoPadrao.close();
			    return arrayRes;
			} catch (SQLException e) {
				System.out.println("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
			}
		}
		return arrayRes;
	}

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<AgendamentoVisita> searchQuery(int codCh) throws SQLException{
		AgendamentoVisitaDAO.checkTable();
		ArrayList<AgendamentoVisita> arrayRes = new ArrayList<AgendamentoVisita>();
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM AgendamentoVisita WHERE codchamado = ?");
			prepSt.setInt(1, codCh);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("codChamado"));
				Funcionario func = FuncionarioDAO.searchQuery(tuplasRes.getInt("codfunc"));
				arrayRes.add(new AgendamentoVisita(tuplasRes.getTimestamp("horaAgendamento").toLocalDateTime(), 
                		tuplasRes.getString("nomere"), 
                		tuplasRes.getString("telefonere"), 
                		tuplasRes.getString("situacao"),
						ch, func
                		));
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro na execução da query de consulta: " + e.getMessage());
		} finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				System.out.println("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
			}
		}
		return arrayRes;
	}
	
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
	public static boolean delete(int codCh) throws SQLException {
		AgendamentoVisitaDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM AgendamentoVisita WHERE codCh = ?");
			prepSt.setInt(1, codCh);
			ret = prepSt.execute();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro na conexao com o banco de dados MySQL: " + e.getMessage() + "\n Exclusão não concluída.");
			return false;
		}  finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				System.out.println("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
			}
		}
		return ret;
	}

	public static ArrayList<AgendamentoVisita> pesquisarAgtVisita(int numChamado){
		ArrayList<AgendamentoVisita> agend = null;
		try {
			agend = AgendamentoVisitaDAO.searchQuery(numChamado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agend;
	}
	public static AgendamentoVisita pesquisarAgtVisita(int numChamado, LocalDateTime hora){
		ArrayList<AgendamentoVisita> agendLista;
		AgendamentoVisita agend = null;
		try {
			agendLista = AgendamentoVisitaDAO.searchQuery(numChamado);
			for(AgendamentoVisita a : agendLista){
				if(a.getHorario().toString() == hora.toString()){
					agend = a;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agend;
	}

	public static void inserirChamado(AgendamentoVisita agend){
		try {
			if(AgendamentoVisitaDAO.searchQuery(agend.getChamado().getCodChamado()) != null){
				AgendamentoVisitaDAO.update(agend);
			} else {
				AgendamentoVisitaDAO.insert(agend);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}