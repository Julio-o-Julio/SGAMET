package persistence;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Funcionario;
import view.Mensagem;


public class FuncionarioDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
    private int nroMatricula;
    private String nome;
    private String cargo;
    private String pais;
    private String estado;
    private String cidade;
    private String telefone;
    public ArrayList horAgendados;
	
	create table if not exists Funcionario (
		nroMatricula INTEGER,
		nome VARCHAR(255),
		cargo VARCHAR(255),
		pais VARCHAR(255),
		estado VARCHAR(255),
		cidade VARCHAR(255),
		telefone VARCHAR(255),
		horaAgendamento TIMESTAMP,
	    PRIMARY KEY (nroMatricula, horaAgendamento)
	); 
	
	
	2018-09-01 09:01:15
	 */
	private static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Funcionario ("
            		+ "	nroMatricula INTEGER, nome VARCHAR(255), cargo VARCHAR(255), pais VARCHAR(255), estado VARCHAR(255), cidade VARCHAR(255), telefone VARCHAR(255), horaAgendamento TIMESTAMP, PRIMARY KEY (nroMatricula, horaAgendamento))");
            statementInsercao.execute();
		} catch(SQLException e) {
            Mensagem.showError("Erro ao tentar criar tabela usuario !");
            e.printStackTrace();
        } finally {
            try {
				if(conexaoPadrao!=null)
                	conexaoPadrao.close();
            } catch (SQLException e) {
                Mensagem.showError("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
            }
        }
	}
	
	
    private static int insert(Funcionario funcionario) throws SQLException {
    	FuncionarioDAO.checkTable();
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
        	if(funcionario.getCargo().equals("TECNICO")) {
	        	for(LocalDateTime item : funcionario.getHorAgendados()) {
		            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
		                    "INSERT INTO Funcionario (nroMatricula, nome, cargo, pais, estado, cidade, telefone, horaagendamento) VALUES (?,?,?,?,?,?,?,?)"
		            );
		     
		            statementInsercao.setInt(1, funcionario.getNroMatricula());
		            statementInsercao.setString(2, funcionario.getNome());
		            statementInsercao.setString(3, funcionario.getCargo());
		            statementInsercao.setString(4, funcionario.getPais());
		            statementInsercao.setString(5, funcionario.getEstado());
		            statementInsercao.setString(6, funcionario.getCidade());
		            statementInsercao.setString(7, funcionario.getTelefone());
		            statementInsercao.setTimestamp(8, Timestamp.valueOf(item));
		            
		            qtdLinhasAfetadas = statementInsercao.executeUpdate();
		        }
        	} else {
	            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
					"INSERT INTO Funcionario (nroMatricula, nome, cargo, pais, estado, cidade, telefone, horaagendamento) VALUES (?,?,?,?,?,?,?,?)"
		            );
	     
	            statementInsercao.setInt(1, funcionario.getNroMatricula());
	            statementInsercao.setString(2, funcionario.getNome());
	            statementInsercao.setString(3, funcionario.getCargo());
	            statementInsercao.setString(4, funcionario.getPais());
	            statementInsercao.setString(5, funcionario.getEstado());
	            statementInsercao.setString(6, funcionario.getCidade());
	            statementInsercao.setString(7, funcionario.getTelefone());
	           	statementInsercao.setTimestamp(8, Timestamp.valueOf("1999-01-01 01:01:01"));
	            
	            qtdLinhasAfetadas = statementInsercao.executeUpdate();
        	}
        	
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


    private static void update(Funcionario funcionario) throws SQLException {
    	FuncionarioDAO.checkTable();
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Funcionario SET nroMatricula = ?, nome = ?, cargo = ?, pais = ?, estado = ?, cidade = ?, telefone = ? " +
                    "WHERE nroMatricula = ?"
            );

            statementInsercao.setInt(1, funcionario.getNroMatricula());
            statementInsercao.setString(2, funcionario.getNome());
            statementInsercao.setString(3, funcionario.getCargo());
            statementInsercao.setString(4, funcionario.getPais());
            statementInsercao.setString(5, funcionario.getEstado());
            statementInsercao.setString(6, funcionario.getCidade());
            statementInsercao.setString(7, funcionario.getTelefone());
            statementInsercao.setInt(8, funcionario.getNroMatricula());

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
    public static ArrayList<Funcionario> selectAll() throws SQLException{
    	FuncionarioDAO.checkTable();
		ArrayList<Funcionario> arrayRes = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Funcionario");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			
			while (tuplasRes.next()) {
				int nroMatricula = tuplasRes.getInt("nromatricula");
				ArrayList<LocalDateTime> arrayDeHorarios = FuncionarioDAO.arrayDeHorarios(nroMatricula);
                arrayRes.add(new Funcionario(	nroMatricula,
                		tuplasRes.getString("nome"),
                		tuplasRes.getString("cargo"),
                		tuplasRes.getString("pais"),
                		tuplasRes.getString("estado"),
                		tuplasRes.getString("cidade"),
                		tuplasRes.getString("telefone"), arrayDeHorarios));
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

    private static ArrayList<LocalDateTime> arrayDeHorarios(int nroMatricula) throws SQLException{
    	FuncionarioDAO.checkTable();
    	ArrayList<LocalDateTime> res = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Funcionario WHERE nroMatricula = ?");
			
			prepSt.setInt(1, nroMatricula);
			
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res.add(tuplasRes.getTimestamp("horaagendamento").toLocalDateTime());
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
		return res;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Funcionario searchQuery(int nroMatricula) throws SQLException{
		FuncionarioDAO.checkTable();
		Funcionario res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Funcionario WHERE nroMatricula = ? LIMIT 1");
			prepSt.setInt(1, nroMatricula);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			
			ArrayList<LocalDateTime> arrayDeHorarios = FuncionarioDAO.arrayDeHorarios(nroMatricula);
			
			while (tuplasRes.next()) {
                res = new Funcionario(	tuplasRes.getInt("nromatricula"),
                		tuplasRes.getString("nome"),
                		tuplasRes.getString("cargo"),
                		tuplasRes.getString("pais"),
                		tuplasRes.getString("estado"),
                		tuplasRes.getString("cidade"),
                		tuplasRes.getString("telefone"), arrayDeHorarios);
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
		return res;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
	private static boolean delete(int nroMatricula) throws SQLException {
		FuncionarioDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Funcionario WHERE nroMatricula = ?");
			prepSt.setInt(1, nroMatricula);
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



	
	
	public static Funcionario pesquisarFuncionario(int nroMatricula){
		Funcionario Funcionario = null;
		try {
			Funcionario = FuncionarioDAO.searchQuery(nroMatricula);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Funcionario;
	}
	public static ArrayList<Funcionario> pesquisarFuncionario(){
		ArrayList<Funcionario> funcionarios = null;
		try {
			funcionarios = FuncionarioDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return funcionarios;
	}

	public static void inserirFuncionario(Funcionario funcionario){
		try {
			if(FuncionarioDAO.searchQuery(funcionario.getNroMatricula()) != null){
				FuncionarioDAO.update(funcionario);
			} else {
				FuncionarioDAO.insert(funcionario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}