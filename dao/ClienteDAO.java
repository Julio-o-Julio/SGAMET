package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Cliente;


public class ClienteDAO {
    //TODO: Lidar com erros de forma mais apropriada
	
	/*
   	use sgamet;
	
	    this.cpfCnpj = cpfCnpj;
        this.companhia = companhia;
        this.cep = cep;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.idiomaPreferencia = idiomaPreferencia;
	
create table if not exists Cliente (
	cpfcnpj VARCHAR(255) PRIMARY KEY,
	companhia VARCHAR(255),
    cep VARCHAR(20),
    pais VARCHAR(20),
    estado VARCHAR(20),
    cidade VARCHAR(20),
    idioma VARCHAR(20)
); 
	 */
	
	private static void checkTable() {
		Connection conexaoPadrao = null;
		try {
			conexaoPadrao = new Conexao().getConexao();
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement("create table if not exists Cliente ("
            		+ "	cpfcnpj VARCHAR(255) PRIMARY KEY,"
            		+ "	companhia VARCHAR(255),"
            		+ " cep VARCHAR(20),"
            		+ " pais VARCHAR(20),"
            		+ " estado VARCHAR(20),"
            		+ " cidade VARCHAR(20),"
            		+ " idioma VARCHAR(20))");
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

    public static int insert(Cliente cliente) throws SQLException {
    	ClienteDAO.checkTable();
        int qtdLinhasAfetadas = 0;
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "INSERT INTO Cliente (cpfcnpj, companhia, cep, pais, estado, cidade, idioma) VALUES (?,?,?,?,?,?,?)"
            );

            statementInsercao.setString(1, cliente.getCpfCnpj());
            statementInsercao.setString(2, cliente.getCompanhia());
            statementInsercao.setString(3, cliente.getCep());
            statementInsercao.setString(4, cliente.getPais());
            statementInsercao.setString(5, cliente.getEstado());
            statementInsercao.setString(6, cliente.getCidade());
            statementInsercao.setString(7, cliente.getIdiomaPreferencia());

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


    public static void update(Cliente cliente) throws SQLException {
    	ClienteDAO.checkTable();
        Connection conexaoPadrao = new Conexao().getConexao();
        try {
            PreparedStatement statementInsercao = conexaoPadrao.prepareStatement(
                    "UPDATE Cliente SET cpfcnpj = ?, companhia = ?, cep = ?, pais = ?, estado = ?, cidade = ?, idioma = ? " +
                    "WHERE cpfcnpj = ?"
            );

            statementInsercao.setString(1, cliente.getCpfCnpj());
            statementInsercao.setString(2, cliente.getCompanhia());
            statementInsercao.setString(3, cliente.getCep());
            statementInsercao.setString(4, cliente.getPais());
            statementInsercao.setString(5, cliente.getEstado());
            statementInsercao.setString(6, cliente.getCidade());
            statementInsercao.setString(7, cliente.getIdiomaPreferencia());
            statementInsercao.setString(8, cliente.getCpfCnpj());

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
    public static ArrayList<Cliente> selectAll() throws SQLException{
    	ClienteDAO.checkTable();
		ArrayList<Cliente> arrayRes = new ArrayList<Cliente>();
		Connection conexaoPadrao = new Conexao().getConexao(); 
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Cliente");
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                arrayRes.add(new Cliente(	tuplasRes.getString("cpfcnpj"),
					                		tuplasRes.getString("companhia"),
					                	    tuplasRes.getString("cep"),
					                	    tuplasRes.getString("pais"),
					                	    tuplasRes.getString("estado"),
					                	    tuplasRes.getString("cidade"),
					                	    tuplasRes.getString("idioma")));
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
	public static Cliente searchQuery(String cpfcnpj) throws SQLException{
		ClienteDAO.checkTable();
		Cliente res = null;
		Connection conexaoPadrao = new Conexao().getConexao();
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("SELECT * FROM Cliente WHERE cpfcnpj LIKE ?");
			prepSt.setString(1, cpfcnpj);
			ResultSet tuplasRes = prepSt.executeQuery(); 
			while (tuplasRes.next()) {
                res = new Cliente(	tuplasRes.getString("cpfcnpj"),
					                		tuplasRes.getString("companhia"),
					                	    tuplasRes.getString("cep"),
					                	    tuplasRes.getString("pais"),
					                	    tuplasRes.getString("estado"),
					                	    tuplasRes.getString("cidade"),
					                	    tuplasRes.getString("idioma"));
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
	public static boolean delete(String cpfCnpj) throws SQLException {
		ClienteDAO.checkTable();
		Connection conexaoPadrao = new Conexao().getConexao();
		boolean ret = false;
		try {
			PreparedStatement prepSt = conexaoPadrao.prepareStatement("DELETE FROM Cliente WHERE cpfcnpj = ?");
			prepSt.setString(1, cpfCnpj);
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

	public static Cliente pesquisarCliente(String cpf){
		Cliente cliente = null;
		try {
			cliente = ClienteDAO.searchQuery(cpf);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}
	public static ArrayList<Cliente> pesquisarCliente(){
		ArrayList<Cliente> clientes = null;
		try {
			clientes = ClienteDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	public static void inserirCliente(Cliente Cliente){
		try {
			if(ClienteDAO.searchQuery(Cliente.getCpfCnpj()) != null){
				ClienteDAO.update(Cliente);
			} else {
				ClienteDAO.insert(Cliente);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 


}