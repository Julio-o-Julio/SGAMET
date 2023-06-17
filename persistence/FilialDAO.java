package persistence;

import java.sql.*;
import java.util.ArrayList;

import model.Filial;
import view.Mensagem;


public class FilialDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
    private int numIdentificacao;
    private int nroTecnicos;
    private String nome;
    private String endereco;
    private String cidade;
    private String cep;
    private String estado;
    private String pais;
	
create table if not exists Filial (
	numIdentificacao INTEGER PRIMARY KEY,
	nroTecnicos INTEGER,
	nome VARCHAR(255),
    endereco VARCHAR(255),
    cidade VARCHAR(255),
    cep VARCHAR(255),
    estado VARCHAR(255),
    pais VARCHAR(255)
); 
	 */
	
	public static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Filial ("
            		+ "	numIdentificacao INTEGER PRIMARY KEY,"
            		+ "	nroTecnicos INTEGER,"
            		+ "	nome VARCHAR(255),"
            		+ " endereco VARCHAR(255),"
            		+ " cidade VARCHAR(255),"
            		+ " cep VARCHAR(255),"
            		+ " estado VARCHAR(255),"
            		+ " pais VARCHAR(255))");
            statementInsercao.execute();
		} catch(SQLException e) {
            Mensagem.showError("Erro ao tentar criar tabela usuario !");
            e.printStackTrace();
        } finally {
            try {
				if(conexaoPadrao!=null)
                	conexaoPadrao.close();
            } catch (SQLException e) {
                Mensagem.showError("Ocorreu uma exceção ao fechar a conexão: " + e.getMessage());
            }
        }
	}

    private static int insert(Filial filial) throws SQLException {
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO Filial (numIdentificacao, nroTecnicos, nome, endereco, cidade, cep, estado, pais) VALUES (?,?,?,?,?,?,?,?)"
            );

            statementInsercao.setInt(1, filial.getNumIdentificacao());
            statementInsercao.setInt(2, filial.getNroTecnicos());
            statementInsercao.setString(3, filial.getNome());
            statementInsercao.setString(4, filial.getEndereco());
            statementInsercao.setString(5, filial.getCidade());
            statementInsercao.setString(6, filial.getCep());
            statementInsercao.setString(7, filial.getEstado());
            statementInsercao.setString(8, filial.getPais());

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


    private static void update(Filial filial) throws SQLException {
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Filial SET numIdentificacao = ?, nroTecnicos = ?, nome = ?, endereco = ?, cidade = ?, cep = ?, estado = ?, pais = ? " +
                    "WHERE codFilial = ?"
            );

            statementInsercao.setInt(1, filial.getNumIdentificacao());
            statementInsercao.setInt(2, filial.getNroTecnicos());
            statementInsercao.setString(3, filial.getNome());
            statementInsercao.setString(4, filial.getEndereco());
            statementInsercao.setString(5, filial.getCidade());
            statementInsercao.setString(6, filial.getCep());
            statementInsercao.setString(7, filial.getEstado());
            statementInsercao.setString(8, filial.getPais());
            statementInsercao.setInt(9, filial.getNumIdentificacao());

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
    public static ArrayList<Filial> selectAll() throws SQLException{
		ArrayList<Filial> arrayRes = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Filial");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                arrayRes.add(new Filial(   tuplasRes.getInt("numIdentificacao"),
				                		tuplasRes.getInt("nroTecnicos"),
				                		tuplasRes.getString("nome"),
				                		tuplasRes.getString("endereco"),
				                		tuplasRes.getString("cidade"),
				                		tuplasRes.getString("cep"),
				                		tuplasRes.getString("estado"),
				                		tuplasRes.getString("pais")));
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
	public static Filial searchQuery(int numIdentificacao) throws SQLException{
		Filial res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Filial WHERE codFilial LIKE ?");
			prepSt.setInt(1, numIdentificacao);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res = new Filial(   tuplasRes.getInt("numIdentificacao"),
                		tuplasRes.getInt("nroTecnicos"),
                		tuplasRes.getString("nome"),
                		tuplasRes.getString("endereco"),
                		tuplasRes.getString("cidade"),
                		tuplasRes.getString("cep"),
                		tuplasRes.getString("estado"),
                		tuplasRes.getString("pais"));
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
	private static boolean delete(int numIdentificacao) throws SQLException {
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Filial WHERE numIdentificacao = ?");
			prepSt.setInt(1, numIdentificacao);
			ret = prepSt.execute();
		} catch (SQLException e) {
			Mensagem.showError("Ocorreu um erro na conexao com o banco de dados MySQL:\n" + e.getMessage() + "\nExclusão não concluída!");
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



	public static Filial pesquisarFilial(int numIdentificacao){
		Filial filial = null;
		try {
			filial = FilialDAO.searchQuery(numIdentificacao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filial;
	}
	public static ArrayList<Filial> pesquisarFilial(){
		ArrayList<Filial> filiais = null;
		try {
			filiais = FilialDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filiais;
	}

	public static void inserirFilial(Filial filial){
		try {
			if(FilialDAO.searchQuery(filial.getNumIdentificacao()) != null){
				FilialDAO.update(filial);
			} else {
				FilialDAO.insert(filial);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}