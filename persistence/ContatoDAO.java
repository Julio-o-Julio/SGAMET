package persistence;

import java.sql.*;
import java.util.ArrayList;

import model.Contato;
import view.Mensagem;


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
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Contato ("
            		+ "	nome VARCHAR(255),"
            		+ " email VARCHAR(255),"
            		+ " telefone VARCHAR(255),"
            		+ " primary key (nome, email, telefone))");
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

    private static int insert(Contato Contato) throws SQLException {
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


    private static void update(Contato Contato) throws SQLException {
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
    public static ArrayList<Contato> selectAll() throws SQLException{
    	ContatoDAO.checkTable();
		ArrayList<Contato> arrayRes = new ArrayList<>();
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
	public static Contato searchQuery(String nome) throws SQLException{
		ContatoDAO.checkTable();
		Contato res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Contato WHERE nome = ?");
			prepSt.setString(1, nome);
            
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res = new Contato(   tuplasRes.getString("nome"),
									tuplasRes.getString("email"),
			                		tuplasRes.getString("telefone"));
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
	private static boolean delete(String nome, String email, String telefone) throws SQLException {
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
			Mensagem.showError("Ocorreu um erro na conexao com o banco de dados MySQL:\n" + e.getMessage() + "\n Exclusão não concluída.");
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




	public static Contato pesquisarContato(String nome){
		Contato contato = null;
		try {
			contato = ContatoDAO.searchQuery(nome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contato;
	}
	public static ArrayList<Contato> pesquisarContato(){
		ArrayList<Contato> contatos = null;
		try {
			contatos = ContatoDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contatos;
	}

	public static void inserirContato(Contato Contato){
		try {
			if(ContatoDAO.searchQuery(Contato.getNome()) != null){
				ContatoDAO.update(Contato);
			} else {
				ContatoDAO.insert(Contato);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}