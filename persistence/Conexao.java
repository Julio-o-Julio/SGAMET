package persistence;

import java.sql.*;

public class Conexao {
	private String database;
	private String url;
	private final String user;
	private final String password;
	//private String args = "?useTimezone=true&serverTimezone=UTC";

	private Conexao(String url, String user, String password) {
		//this.database = database;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public Conexao() {
		this("jdbc:mysql://localhost:3306/", "root", "123456789");
		this.checkDb();
		this.setDb();
	}

	public Connection getConexao() throws SQLException {
		return DriverManager.getConnection(this.url+"?useTimezone=true&serverTimezone=UTC", this.user, this.password);
	}
	
	private void setDb(){
		this.database = "sgamet";
		this.url = this.url + this.database;
	}
	
	private void checkDb() {
		try {
			Connection conex = this.getConexao();
			PreparedStatement prepSt = conex.prepareStatement("create database if not exists sgamet");
			prepSt.execute();
			conex.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//    private void migrate(){
//        this.runQuery("")
//    }
    // public ResultSet runQuery(String _sql) throws SQLException{
    //     Statement st = this.conn.createStatement();
    //     ResultSet rs = st.executeQuery(_sql);
    //     st.close();
    //     return rs;
    // }
    // public int runUpdate(String _sql) throws SQLException{
    //     Statement st = this.conn.createStatement();
    //     final int linhasAfetadas = st.executeUpdate(_sql);
    //     st.close();
    //     return linhasAfetadas;
    // }
    // public PreparedStatement createStatement(String sqlStatement) throws SQLException{
    //     return this.conn.prepareStatement(sqlStatement);
    // }
}