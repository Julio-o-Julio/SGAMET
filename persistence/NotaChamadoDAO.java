package persistence;

import java.sql.*;
import java.util.ArrayList;

import model.Chamado;
import model.NotaChamado;


public class NotaChamadoDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
	create table if not exists notachamado (
		numNota INTEGER PRIMARY KEY,
		numChamado INTEGER,
	    descricao VARCHAR(255),
	    FOREIGN KEY (numChamado) REFERENCES chamado(codchamado)
	); 
	 */
	
	private static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists notachamado ("
            		+ "		numNota INTEGER PRIMARY KEY,"
            		+ "		numChamado INTEGER,"
            		+ "	    descricao VARCHAR(255),"
            		+ "	    FOREIGN KEY (numChamado) REFERENCES chamado(codchamado))");
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

    public static int insert(NotaChamado notaChamado) throws SQLException {
    	NotaChamadoDAO.checkTable();
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO notachamado (numnota, descricao) VALUES (?,?,?)"
            );

            statementInsercao.setInt(1, notaChamado.getNumNota());
            statementInsercao.setInt(2, notaChamado.getNumChamado());
            statementInsercao.setString(3, notaChamado.getDescricao());

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


    public static void update(NotaChamado notaChamado) throws SQLException {
    	NotaChamadoDAO.checkTable();
    	Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE notaChamado SET numNota = ?, numChamado = ?, descricao = ?" +
                    "WHERE numNota = ?"
            );

            statementInsercao.setInt(1, notaChamado.getNumNota());
            statementInsercao.setInt(2, notaChamado.getNumChamado());
            statementInsercao.setString(3, notaChamado.getDescricao());
            statementInsercao.setInt(4, notaChamado.getNumNota());

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
    public static ArrayList<NotaChamado> selectAll() throws SQLException{
    	NotaChamadoDAO.checkTable();
		ArrayList<NotaChamado> arrayRes = new ArrayList<NotaChamado>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM notachamado");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("numchamado"));
                arrayRes.add(new NotaChamado(ch,
                							tuplasRes.getInt("numnota"),
                                            tuplasRes.getString("descricao")));
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
	public static NotaChamado searchQuery(int numNota) throws SQLException{
		NotaChamadoDAO.checkTable();
		NotaChamado res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM notachamado WHERE numnota LIKE ?");
			prepSt.setInt(1, numNota); 
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Chamado ch = ChamadoDAO.searchQuery(tuplasRes.getInt("numchamado"));
                res = new NotaChamado(ch,
                							tuplasRes.getInt("numnota"),
                                            tuplasRes.getString("descricao"));
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
	public static boolean delete(int numNota) throws SQLException {
		NotaChamadoDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM notachamado WHERE numnota = ?");
			prepSt.setInt(1, numNota);
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

	public static NotaChamado pesquisarNtChamado(int numNota){
		NotaChamado nota = null;
		try {
			nota = NotaChamadoDAO.searchQuery(numNota);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nota;
	}
	public static ArrayList<NotaChamado> pesquisarNtChamado(){
		ArrayList<NotaChamado> notas = null;
		try {
			notas = NotaChamadoDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notas;
	}

	public static void inserirNtChamado(NotaChamado nota){
		try {
			if(NotaChamadoDAO.searchQuery(nota.getNumNota()) != null){
				NotaChamadoDAO.update(nota);
			} else {
				NotaChamadoDAO.insert(nota);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}