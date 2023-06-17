package persistence;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AgendamentoVisita;
import model.Chamado;
import model.Funcionario;
import view.Mensagem;


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
	id INT(11) AUTO_INCREMENT,
	codChamado INTEGER,
	nomere VARCHAR(255),
    telefonere VARCHAR(225),
    situacao VARCHAR(225),
    codfunc INTEGER,
    horaAgendamento TIMESTAMP,
    PRIMARY KEY (id)
); 
	 */
	
	public static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists AgendamentoVisita ("
            		+ " id INT(11) AUTO_INCREMENT,"
					+ "	codChamado INTEGER,"
            		+ "	nomere VARCHAR(255),"
            		+ " telefonere VARCHAR(225),"
            		+ " situacao VARCHAR(225),"
            		+ " codfunc INTEGER,"
            		+ " horaAgendamento TIMESTAMP,"
					+ " PRIMARY KEY (id))");
            statementInsercao.execute();
		} catch(SQLException e) {
			Mensagem.showError("Erro ao tentar criar tabela usuario !");
            e.printStackTrace();
        } finally {
            try {
				if(conexaoPadrao != null)
                	conexaoPadrao.close();
            } catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
            }
        }
	}

    private static int insert(AgendamentoVisita agendamentoVisita) throws SQLException {
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
			Mensagem.showError("Erro ao tentar criar usuário!");
            e.printStackTrace();
        } finally {
            try {
                conexaoPadrao.close();
            } catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
            }
        }

        return qtdLinhasAfetadas;
    }


    private static void update(AgendamentoVisita agendamentoVisita) throws SQLException {
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
            Mensagem.showError("Erro ao tentar atualizar usuário!");
            e.printStackTrace();
        } finally {
            try {
                conexaoPadrao.close();
            } catch (SQLException e) {
                Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
            }
        }
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static ArrayList<AgendamentoVisita> selectAll() throws SQLException{
		ArrayList<AgendamentoVisita> arrayRes = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM AgendamentoVisita");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("codChamado"));
				Funcionario func = FuncionarioDAO.searchQuery(tuplasRes.getInt("codfunc"));
				AgendamentoVisita agend = new AgendamentoVisita(tuplasRes.getTimestamp("horaAgendamento").toLocalDateTime(), tuplasRes.getString("nomere"), tuplasRes.getString("telefonere"), tuplasRes.getString("situacao"),ch, func);
				agend.setId(tuplasRes.getInt("id"));
                arrayRes.add(agend);
			}
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na execução da query de consulta:\n" + e.getMessage());
		} finally {			
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
			}
		}
		return arrayRes;
	}

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static ArrayList<AgendamentoVisita> searchQuery(int codCh) throws SQLException{
		ArrayList<AgendamentoVisita> arrayRes = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM AgendamentoVisita WHERE codchamado = ?");
			prepSt.setInt(1, codCh);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("codChamado"));
				Funcionario func = FuncionarioDAO.searchQuery(tuplasRes.getInt("codfunc"));
				AgendamentoVisita agend = new AgendamentoVisita(tuplasRes.getTimestamp("horaAgendamento").toLocalDateTime(), tuplasRes.getString("nomere"), tuplasRes.getString("telefonere"), tuplasRes.getString("situacao"),ch, func);
				agend.setId(tuplasRes.getInt("id"));
                arrayRes.add(agend);
			}
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na execução da query de consulta:\n" + e.getMessage());
		} finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
			}
		}
		return arrayRes;
	}

	private static AgendamentoVisita searchQuery(int codCh, LocalDateTime hor) throws SQLException{
		AgendamentoVisita agend = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM AgendamentoVisita WHERE codchamado = ? and horaagendamento = ?");
			prepSt.setInt(1, codCh);
			prepSt.setTimestamp(2, Timestamp.valueOf(hor));
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("codChamado"));
				Funcionario func = FuncionarioDAO.searchQuery(tuplasRes.getInt("codfunc"));
				agend = new AgendamentoVisita(tuplasRes.getTimestamp("horaAgendamento").toLocalDateTime(), 
                		tuplasRes.getString("nomere"), 
                		tuplasRes.getString("telefonere"), 
                		tuplasRes.getString("situacao"),
						ch, func
                		);
				agend.setId(tuplasRes.getInt("id"));
			}
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na execução da query de consulta:\n" + e.getMessage());
		} finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
			}
		}
		return agend;
	}
	

	private static AgendamentoVisita searchQueryById(int id) throws SQLException{
		AgendamentoVisita agend = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM AgendamentoVisita WHERE id = ?");
			prepSt.setInt(1, id);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("codChamado"));
				Funcionario func = FuncionarioDAO.searchQuery(tuplasRes.getInt("codfunc"));
				agend = new AgendamentoVisita(tuplasRes.getTimestamp("horaAgendamento").toLocalDateTime(), 
                		tuplasRes.getString("nomere"), 
                		tuplasRes.getString("telefonere"), 
                		tuplasRes.getString("situacao"),
						ch, func
                		);
				agend.setId(tuplasRes.getInt("id"));
			}
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na execução da query de consulta:\n" + e.getMessage());
		} finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
			}
		}
		return agend;
	}

	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
	private static boolean delete(int codCh) throws SQLException {
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM AgendamentoVisita WHERE codCh = ?");
			prepSt.setInt(1, codCh);
			ret = prepSt.execute();
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na conexao com o banco de dados MySQL:\n" + e.getMessage() + "\nExclusão não concluída.");
			return false;
		}  finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
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
		AgendamentoVisita agend = null;
		try {
			agend = AgendamentoVisitaDAO.searchQuery(numChamado, hora);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agend;
	}

	public static AgendamentoVisita pesquisarAgtVisitaPorId(int id){
		AgendamentoVisita agend = null;
		try {
			agend = AgendamentoVisitaDAO.searchQueryById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agend;
	}

	public static boolean inserirAgendamentoChamado(AgendamentoVisita agend){
		try {
			if(AgendamentoVisitaDAO.searchQuery(agend.getChamado().getCodChamado(), agend.getHorario()) != null){
				AgendamentoVisitaDAO.update(agend);
			} else {
				if(AgendamentoVisitaDAO.insert(agend) >= 1) Mensagem.showSucces("Agendamento realizado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	public static void main(String[] args) {
		//AgendamentoVisita agend = new AgendamentoVisita(LocalDateTime.now(), "gerso", "255", "top", new Chamado(4, "oi", "oi2", "oi3"), new Funcionario(9, "nome", "carg", "pais", "estad", "cidad", "telef", new ArrayList<LocalDateTime>()));

		//FuncionarioDAO.inserirFuncionario(new Funcionario(9, "nome", "carg", "pais", "estad", "cidad", "telef", new ArrayList<LocalDateTime>()));
		//ChamadoDAO.inserirChamado(new Chamado(4, "oi", "oi2", "oi3"));

		System.out.println(ChamadoDAO.pesquisarChamado(4));

		//AgendamentoVisitaDAO.inserirChamado(agend);
	}


}