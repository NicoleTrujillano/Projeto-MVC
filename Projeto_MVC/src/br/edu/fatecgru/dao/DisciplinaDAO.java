package br.edu.fatecgru.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatecgru.model.Desempenho;
import br.edu.fatecgru.model.Disciplina;
import br.edu.fatecgru.util.ConnectionFactory;

public class DisciplinaDAO {
	
	private Connection conn;
	
	public DisciplinaDAO() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro ao conectar: " + e.getMessage());
        }
    }
	
	public List<Disciplina> listarTodas() throws Exception {
        List<Disciplina> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id_disciplina, nome_disciplina FROM disciplina";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id_disciplina"));
                d.setDisciplina(rs.getString("nome_disciplina"));
                lista.add(d);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return lista;
    }

	

}
