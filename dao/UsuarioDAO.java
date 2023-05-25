package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Usuario;


public class UsuarioDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
        this.email = email;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
	
	create table if not exists Usuario (
		nomeusuario VARCHAR(255) PRIMARY KEY,
		email VARCHAR(255),
	    senha VARCHAR(20)
	); 
	 */
	private static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Usuario ("
            		+ "		nomeusuario VARCHAR(255) PRIMARY KEY,"
            		+ "		email VARCHAR(255),"
            		+ "	    senha VARCHAR(20))");
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
	
    public static int insert(Usuario usuario) throws SQLException {
    	UsuarioDAO.checkTable();
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO Usuario (nomeusuario, email, senha) VALUES (?,?,?)"
            );

            statementInsercao.setString(1, usuario.getNomeUsuario());
            statementInsercao.setString(2, usuario.getEmail());
            statementInsercao.setString(3, usuario.getSenha());
            
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


    public static void update(Usuario usuario) throws SQLException {
    	UsuarioDAO.checkTable();
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Usuario SET nomeusuario = ?, email = ?, senha = ? " +
                    "WHERE nomeusuario = ?"
            );

            statementInsercao.setString(1, usuario.getNomeUsuario());
            statementInsercao.setString(2, usuario.getEmail());
            statementInsercao.setString(3, usuario.getSenha());
            statementInsercao.setString(4, usuario.getNomeUsuario());

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
    public static ArrayList<Usuario> selectAll() throws SQLException{
    	UsuarioDAO.checkTable();
		ArrayList<Usuario> arrayRes = new ArrayList<Usuario>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Usuario");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                arrayRes.add(new Usuario(	tuplasRes.getString("email"),
                							tuplasRes.getString("nomeusuario"),
					                	    tuplasRes.getString("senha")));
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
	public static Usuario searchQuery(String nomeUsuario) throws SQLException{
		UsuarioDAO.checkTable();
		Usuario res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Usuario WHERE cpfcnpj LIKE ?");
			prepSt.setString(1, nomeUsuario);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res = new Usuario(	tuplasRes.getString("email"),
						tuplasRes.getString("nomeusuario"),
                	    tuplasRes.getString("senha"));
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
	public static boolean delete(String nomeUsuario) throws SQLException {
		UsuarioDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Usuario WHERE nomeusuario = ?");
			prepSt.setString(1, nomeUsuario);
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




	public static Usuario pesquisarUsuario(String nomeUsuario){
		Usuario Usuario = null;
		try {
			Usuario = UsuarioDAO.searchQuery(nomeUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Usuario;
	}
	public static ArrayList<Usuario> pesquisarUsuario(){
		ArrayList<Usuario> filiais = null;
		try {
			filiais = UsuarioDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filiais;
	}

	public void inserirUsuario(Usuario Usuario){
		try {
			if(UsuarioDAO.searchQuery(Usuario.getNomeUsuario()) != null){
				UsuarioDAO.update(Usuario);
			} else {
				UsuarioDAO.insert(Usuario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}