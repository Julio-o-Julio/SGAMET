package persistence;

import java.sql.*;
import java.util.ArrayList;

import model.Cliente;
import model.Equipamento;
import view.Mensagem;


public class EquipamentoDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
create table if not exists Equipamento (
	numserie VARCHAR(255) PRIMARY KEY,
	descricao VARCHAR(255),
    marca VARCHAR(255),
    modelo VARCHAR(255),
    cpfcnpjcli = VARCHAR(255),
    FOREIGN KEY (cpfcnpjcli) REFERENCES cliente(cpfcnpj)
); 
	 */

	public static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Equipamento ("
            		+ "	numserie VARCHAR(255) PRIMARY KEY,"
            		+ "	descricao VARCHAR(255),"
            		+ " marca VARCHAR(255),"
            		+ " modelo VARCHAR(255),"
            		+ " cpfcnpjcli VARCHAR(255),"
            		+ " FOREIGN KEY (cpfcnpjcli) REFERENCES cliente(cpfcnpj))");
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
	
    private static int insert(Equipamento Equipamento) throws SQLException {
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO Equipamento (numserie, descricao, marca, modelo, cpfcnpjcli) VALUES (?,?,?,?,?)"
            );

            statementInsercao.setString(1, Equipamento.getNumSerie());
            statementInsercao.setString(2, Equipamento.getDescricao());
            statementInsercao.setString(3, Equipamento.getMarca());
            statementInsercao.setString(4, Equipamento.getModelo());
            statementInsercao.setString(5, Equipamento.getCliente().getCpfCnpj());

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


    private static void update(Equipamento Equipamento) throws SQLException {
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Equipamento SET numserie = ?, descricao = ?, marca = ?, modelo = ?, cpfcnpjcli = ? " +
                    "WHERE numserie = ?"
            );

            statementInsercao.setString(1, Equipamento.getNumSerie());
            statementInsercao.setString(2, Equipamento.getDescricao());
            statementInsercao.setString(3, Equipamento.getMarca());
            statementInsercao.setString(4, Equipamento.getModelo());
            statementInsercao.setString(5, Equipamento.getCliente().getCpfCnpj());
            statementInsercao.setString(6, Equipamento.getNumSerie());

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
    public static ArrayList<Equipamento> selectAll() throws SQLException{
		ArrayList<Equipamento> arrayRes = new ArrayList<>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Equipamento");
			ResultSet tuplasRes = prepSt.executeQuery(); 			
			while (tuplasRes.next()) {
				Cliente cli = ClienteDAO.searchQuery(tuplasRes.getString("cpfcnpj"));
                arrayRes.add(new Equipamento(   tuplasRes.getString("numserie"),
                							tuplasRes.getString("descricao"),
					                		tuplasRes.getString("marca"),
                                            tuplasRes.getString("modelo"),
                                            cli
                							));
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
	public static Equipamento searchQuery(String numserie) throws SQLException{
		Equipamento res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Equipamento WHERE numserie LIKE ?");
			prepSt.setString(1, numserie);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
				Cliente cli = ClienteDAO.searchQuery(tuplasRes.getString("cpfcnpj"));
                res = new Equipamento(   tuplasRes.getString("numserie"),
                							tuplasRes.getString("descricao"),
					                		tuplasRes.getString("marca"),
                                            tuplasRes.getString("modelo"),
                                            cli
                					);
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
	private static boolean delete(String numSerie) throws SQLException {
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Equipamento WHERE numserie = ?");
			prepSt.setString(1, numSerie);
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



	public static Equipamento pesquisarEquipamento(String numSerie){
		Equipamento equipamento = null;
		try {
			equipamento = EquipamentoDAO.searchQuery(numSerie);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipamento;
	}
	public static ArrayList<Equipamento> pesquisarEquipamento(){
		ArrayList<Equipamento> equipamentos = null;
		try {
			equipamentos = EquipamentoDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipamentos;
	}

	public static void inserirEquipamento(Equipamento equipamento){
		try {
			if(EquipamentoDAO.searchQuery(equipamento.getNumSerie()) != null){
				EquipamentoDAO.update(equipamento);
			} else {
				EquipamentoDAO.insert(equipamento);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}