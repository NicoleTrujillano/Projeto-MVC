package br.edu.fatecgru.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatecgru.model.Desempenho;
import br.edu.fatecgru.model.Disciplina;
import br.edu.fatecgru.util.ConnectionFactory;

public class DesempenhoDAO {

    private Connection conn;

    public DesempenhoDAO() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception("Erro ao conectar: " + e.getMessage());
        }
    }

    public void salvar(Desempenho desempenho) throws Exception {
        if (desempenho == null || desempenho.getAluno() == null || desempenho.getDisciplina() == null) {
            throw new Exception("Dados incompletos para salvar desempenho.");
        }

        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO desempenho (id_aluno, id_disciplina, semestre, nota, faltas) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, desempenho.getAluno().getRgm());
            ps.setInt(2, desempenho.getDisciplina().getId());
            ps.setString(3, desempenho.getSemestre());
            ps.setDouble(4, desempenho.getNota());
            ps.setInt(5, desempenho.getFaltas());

            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("Erro ao salvar desempenho: " + sqle.getMessage());
        } finally {
            if (ps != null) ps.close();
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    public boolean desempenhoExiste(int idAluno, int idDisciplina) throws Exception {
    	String sql = "SELECT id_desempenho FROM desempenho WHERE id_aluno = ? AND id_disciplina = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idAluno);
            ps.setInt(2, idDisciplina);
            rs = ps.executeQuery();
            return rs.next(); // true se existir
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public void alterar(Desempenho desempenho) throws Exception {
        String sql = "UPDATE desempenho SET nota = ?, faltas = ?, semestre = ? " +
                     "WHERE id_aluno = ? AND id_disciplina = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, desempenho.getNota());
            ps.setInt(2, desempenho.getFaltas());
            ps.setString(3, desempenho.getSemestre()); // agora o semestre pode ser atualizado
            ps.setInt(4, desempenho.getAluno().getRgm());
            ps.setInt(5, desempenho.getDisciplina().getId());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
    
    public void excluir(Desempenho desempenho) throws Exception {
        String sql = "DELETE FROM desempenho WHERE id_aluno = ? AND id_disciplina = ? AND semestre = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, desempenho.getAluno().getRgm());
            ps.setInt(2, desempenho.getDisciplina().getId());
            ps.setString(3, desempenho.getSemestre());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
    
    public List<Desempenho> listarPorAluno(int rgm) throws Exception {
	    List<Desempenho> lista = new ArrayList<>();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT d.nome_disciplina, dp.semestre, dp.nota, dp.faltas " +
	                     "FROM desempenho dp " +
	                     "JOIN disciplina d ON dp.id_disciplina = d.id_disciplina " +
	                     "WHERE dp.id_aluno = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, rgm);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Disciplina disciplina = new Disciplina();
	            disciplina.setDisciplina(rs.getString("nome_disciplina"));

	            Desempenho d = new Desempenho();
	            d.setDisciplina(disciplina);
	            d.setSemestre(rs.getString("semestre"));
	            d.setNota(rs.getDouble("nota"));
	            d.setFaltas(rs.getInt("faltas"));

	            lista.add(d);
	        }
	    } finally {
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	    }

	    return lista;
	}
    
    public List<Desempenho> listarBoletim(int rgm) throws Exception {
        List<Desempenho> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT d.nome_disciplina, dp.semestre, dp.nota, dp.faltas " +
                         "FROM desempenho dp " +
                         "JOIN disciplina d ON dp.id_disciplina = d.id_disciplina " +
                         "WHERE dp.id_aluno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, rgm);
            rs = ps.executeQuery();

            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setDisciplina(rs.getString("nome_disciplina"));

                Desempenho d = new Desempenho();
                d.setDisciplina(disciplina);
                d.setSemestre(rs.getString("semestre"));
                d.setNota(rs.getDouble("nota"));
                d.setFaltas(rs.getInt("faltas"));

                lista.add(d);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return lista;
    }
    
    public Desempenho buscarDesempenho(int idAluno, int idDisciplina) throws Exception {
        String sql = "SELECT semestre, nota, faltas FROM desempenho WHERE id_aluno = ? AND id_disciplina = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idAluno);
            ps.setInt(2, idDisciplina);
            rs = ps.executeQuery();

            if (rs.next()) {
                Desempenho d = new Desempenho();
                d.setSemestre(rs.getString("semestre"));
                d.setNota(rs.getDouble("nota"));
                d.setFaltas(rs.getInt("faltas"));
                return d;
            } else {
                return null;
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public void excluirPorAluno(int rgm) throws Exception {
        String sql = "DELETE FROM desempenho WHERE id_aluno = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, rgm);
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
    
}
