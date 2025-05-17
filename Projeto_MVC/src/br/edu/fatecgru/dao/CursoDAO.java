package br.edu.fatecgru.dao;

import java.sql.Connection;

import com.mysql.jdbc.PreparedStatement;

import br.edu.fatecgru.model.Curso;
import br.edu.fatecgru.util.ConnectionFactory;

public class CursoDAO {
	
private Connection conn;
	
	public CursoDAO() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro ao conectar: " + e.getMessage());
        }
    }
	
	public void alterar(Curso curso) throws Exception {
	    String sql = "UPDATE curso SET nome_curso = ?, campus = ?, periodo = ? WHERE id_curso = ?";
	    PreparedStatement ps = null;

	    try {
	        ps = (PreparedStatement) conn.prepareStatement(sql);
	        ps.setString(1, curso.getNome());
	        ps.setString(2, curso.getCampus());
	        ps.setString(3, curso.getPeriodo());
	        ps.setInt(4, curso.getId());
	        ps.executeUpdate();
	    } finally {
	        if (ps != null) ps.close();
	    }
	}


}
