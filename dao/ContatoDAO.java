package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Contato;


public class ContatoDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
create table if not exists Contato (
	nome VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(255),
    primary key (nome, email, telefone)
); 
	 */
	
	private static void checkTable() {
		try {
			Connection conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Contato ("
            		+ "	nome VARCHAR(255),"
            		+ " email VARCHAR(255),"
            		+ " telefone VARCHAR(255),"
            		+ " primary key (nome, email, telefone))");
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

    public static int insert(Contato Contato) throws SQLException {
    	ContatoDAO.checkTable();
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO Contato (nome, email, telefone) VALUES (?,?,?)"
            );

            statementInsercao.setString(1, Contato.getNome());
            statementInsercao.setString(2, Contato.getEmail());
            statementInsercao.setString(3, Contato.getTelefone());

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


    public static void update(Contato Contato) throws SQLException {
    	ContatoDAO.checkTable();
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Contato SET nome = ?, email = ?, telefone = ? " +
                    "WHERE nome = ? and email = ? and telefone = ?"
            );

            statementInsercao.setString(1, Contato.getNome());
            statementInsercao.setString(2, Contato.getEmail());
            statementInsercao.setString(3, Contato.getTelefone());
            statementInsercao.setString(4, Contato.getNome());
            statementInsercao.setString(5, Contato.getEmail());
            statementInsercao.setString(6, Contato.getTelefone());

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
    public static ArrayList<Contato> selectAll() throws SQLException{
    	ContatoDAO.checkTable();
		ArrayList<Contato> arrayRes = new ArrayList<Contato>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Contato");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                arrayRes.add(new Contato(   tuplasRes.getString("nome"),
                							tuplasRes.getString("email"),
					                		tuplasRes.getString("telefone")));
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
	public static Contato searchQuery(String nome, String email, String telefone) throws SQLException{
		ContatoDAO.checkTable();
		Contato res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Contato WHERE nome = ? and email = ? and telefone = ?");
			prepSt.setString(1, nome);
			prepSt.setString(2, email);
			prepSt.setString(3, telefone);
            
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res = new Contato(   tuplasRes.getString("nome"),
									tuplasRes.getString("email"),
			                		tuplasRes.getString("telefone"));
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
	public static boolean delete(String nome, String email, String telefone) throws SQLException {
		ContatoDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Contato WHERE nome = ? and email = ? and telefone = ?");
			prepSt.setString(1, nome);
			prepSt.setString(2, email);
			prepSt.setString(3, telefone);
            
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

}