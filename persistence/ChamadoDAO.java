package persistence;

import java.sql.*;
import java.util.ArrayList;

import model.Chamado;
import view.Mensagem;


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

	 private static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Chamado ("
            		+ "	codchamado INTEGER PRIMARY KEY, codurgencia VARCHAR(255), codsituacao VARCHAR(255), telefonesuporte VARCHAR(255))");
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

	

    private static int insert(Chamado chamado) throws SQLException {
		ChamadoDAO.checkTable();
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


    private static void update(Chamado chamado) throws SQLException {
		ChamadoDAO.checkTable();
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
    public static ArrayList<Chamado> selectAll() throws SQLException{
		ChamadoDAO.checkTable();
		ArrayList<Chamado> arrayRes = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Chamado");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                arrayRes.add(new Chamado(   tuplasRes.getInt("codchamado"),
                							tuplasRes.getString("codurgencia"),
					                		tuplasRes.getString("codsituacao"),
                                            tuplasRes.getString("telefonesuporte")));
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
	public static Chamado searchQuery(int codChamado) throws SQLException{
		ChamadoDAO.checkTable();
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
					                        tuplasRes.getString("telefonesuporte"));
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
	private static boolean delete(int codChamado) throws SQLException {
		ChamadoDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Chamado WHERE codchamado = ?");
			prepSt.setInt(1, codChamado);
			ret = prepSt.execute();
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na conexao com o banco de dados MySQL:\n" + e.getMessage() + "\n Exclusão não concluída.");
			return false;
		}  finally {
			try {
				conexaoPadrao.close();
			} catch (SQLException e) {
				System.out.println("Ocorreu uma exceção ao fechar a conexão:\n" + e.getMessage());
			}
		}
		return ret;
	}



	public static Chamado pesquisarChamado(int cod){
		Chamado chamado = null;
		try {
			chamado = ChamadoDAO.searchQuery(cod);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chamado;
	}
	public static ArrayList<Chamado> pesquisarChamado(){
		ArrayList<Chamado> chamados = null;
		try {
			chamados = ChamadoDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chamados;
	}

	public static void inserirChamado(Chamado chamado){
		try {
			if(ChamadoDAO.searchQuery(chamado.getCodChamado()) != null){
				ChamadoDAO.update(chamado);
			} else {
				ChamadoDAO.insert(chamado);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}