package br.edu.fatecgru.util;

import java.sql.*;

public class ConnectionFactory {
	
	public static Connection getConnection() throws Exception {
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/projeto_mvc?useTimezone=true&serverTimezone=UTC";
	        String user = "root";
	        String senha = "";
	        return DriverManager.getConnection(url, user, senha);
	    } catch (ClassNotFoundException | SQLException e) {
	        throw new Exception("Erro ao conectar ao banco: " + e.getMessage(), e);
	    }
	}


	// fecha uma conexão de três formas: conn, stmt, rs
		public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws Exception {
			close(conn, stmt, rs);
		}

		public static void closeConnection(Connection conn, Statement stmt) throws Exception {
			close(conn, stmt, null);
		}

		public static void closeConnection(Connection conn) throws Exception {
			close(conn, null, null);
		}

		private static void close(Connection conn, Statement stmt, ResultSet rs) throws Exception {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
}

