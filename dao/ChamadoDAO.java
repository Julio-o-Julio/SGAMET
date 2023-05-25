package SgametDAOS;

import java.sql.*;
import java.util.ArrayList;

import Negocio.Chamado;
import database.Conexao;

public class ChamadoDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
	create table if not exists Chamado (
		codchamado INTEGER PRIMARY KEY,
		codurgencia VARCHAR(255),
	    codsituacao VARCHAR(255),
	    telefonesuporte VARCHAR(255)
	); 
	 */

    public static int insert(Chamado chamado) throws SQLException {
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO Chamado (codchamado, codurgencia, codsituacao, telefonesuporte) VALUES (?,?,?,?)"
            );

            statementInsercao.setInt(1, chamado.getCodChamado());
            statementInsercao.setString(2, chamado.getCodUrgencia());
            statementInsercao.setString(3, chamado.getCodSituacao());
            statementInsercao.setString(4, chamado.getTelefoneSuporte());

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


    public static void update(Chamado chamado) throws SQLException {
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Chamado SET codchamado = ?, codurgencia = ?, codsituacao = ?, telefonesuporte = ? " +
                    "WHERE codchamado = ?"
            );

            statementInsercao.setInt(1, chamado.getCodChamado());
            statementInsercao.setString(2, chamado.getCodUrgencia());
            statementInsercao.setString(3, chamado.getCodSituacao());
            statementInsercao.setString(4, chamado.getTelefoneSuporte());
            statementInsercao.setInt(5, chamado.getCodChamado());

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
    public static ArrayList<Chamado> selectAll() throws SQLException{
		ArrayList<Chamado> arrayRes = new ArrayList<Chamado>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Chamado");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                arrayRes.add(new Chamado(   tuplasRes.getInt("codchamado"),
                							tuplasRes.getString("codurgencia"),
					                		tuplasRes.getString("codsituacao"),
                                            tuplasRes.getString("telefoneresponsavel")));
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
	public static Chamado searchQuery(int codChamado) throws SQLException{
		Chamado res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Chamado WHERE codchamado LIKE ?");
			prepSt.setInt(1, codChamado);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res = new Chamado(   tuplasRes.getInt("codchamado"),
											tuplasRes.getString("codurgencia"),
					                		tuplasRes.getString("codsituacao"),
					                        tuplasRes.getString("telefoneresponsavel"));
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
	public static boolean delete(int codChamado) throws SQLException {
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Chamado WHERE codchamado = ?");
			prepSt.setInt(1, codChamado);
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