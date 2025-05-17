package br.edu.fatecgru.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.edu.fatecgru.model.Aluno;
import br.edu.fatecgru.model.Curso;
import br.edu.fatecgru.model.Disciplina;
import br.edu.fatecgru.model.Desempenho;
import br.edu.fatecgru.util.ConnectionFactory;

public class AlunoDAO {

	private Connection conn;
	PreparedStatement ps = null;
	private ResultSet rs;
	private Aluno aluno;
	private Curso curso;
	private Disciplina disciplina;
	private Desempenho desempenho;

	public AlunoDAO() throws Exception {
		/*
		 * chama a classe ConnectionFactory e estabele uma conexão
		 */
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}

	// método de salvar
	public void salvar(Aluno aluno) throws Exception {
	    if (aluno == null || aluno.getCurso() == null)
	        throw new Exception("O aluno ou o curso não podem ser nulos");

	    PreparedStatement psCurso = null;
	    PreparedStatement psBuscaCurso = null;
	    ResultSet rsCurso = null;
	    ResultSet rsBuscaCurso = null;

	    try {
	        // 1. Verificar se o curso já existe
	        String sqlBuscaCurso = "SELECT id_curso FROM curso WHERE nome_curso = ? AND campus = ? AND periodo = ?";
	        psBuscaCurso = (PreparedStatement) conn.prepareStatement(sqlBuscaCurso);
	        psBuscaCurso.setString(1, aluno.getCurso().getNome());
	        psBuscaCurso.setString(2, aluno.getCurso().getCampus());
	        psBuscaCurso.setString(3, aluno.getCurso().getPeriodo());
	        rsBuscaCurso = psBuscaCurso.executeQuery();

	        int idCurso;

	        if (rsBuscaCurso.next()) {
	            idCurso = rsBuscaCurso.getInt("id_curso");
	        } else {
	            // Curso não existe, então inserir
	            String sqlCurso = "INSERT INTO curso (nome_curso, campus, periodo) VALUES (?, ?, ?)";
	            psCurso = (PreparedStatement) conn.prepareStatement(sqlCurso, Statement.RETURN_GENERATED_KEYS);
	            psCurso.setString(1, aluno.getCurso().getNome());
	            psCurso.setString(2, aluno.getCurso().getCampus());
	            psCurso.setString(3, aluno.getCurso().getPeriodo());
	            psCurso.executeUpdate();

	            rsCurso = psCurso.getGeneratedKeys();
	            if (rsCurso.next()) {
	                idCurso = rsCurso.getInt(1);
	            } else {
	                throw new Exception("Erro ao obter o ID do curso.");
	            }
	        }

	        aluno.getCurso().setId(idCurso); // atualiza objeto

	        // 2. Inserir o aluno
	        String sqlAluno = "INSERT INTO aluno (rgm, nome_aluno, data_nascimento, cpf, email, endereco, municipio, UF, celular, id_curso) "
	                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        ps = (PreparedStatement) conn.prepareStatement(sqlAluno);
	        ps.setInt(1, aluno.getRgm());
	        ps.setString(2, aluno.getNome());
	        ps.setString(3, aluno.getDataNascimento());
	        ps.setString(4, aluno.getCpf());
	        ps.setString(5, aluno.getEmail());
	        ps.setString(6, aluno.getEndereco());
	        ps.setString(7, aluno.getMunicipio());
	        ps.setString(8, aluno.getUf());
	        ps.setString(9, aluno.getCelular());
	        ps.setInt(10, idCurso);

	        ps.executeUpdate();

	    } catch (SQLException sqle) {
	        throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
	    } finally {
	        if (ps != null) ps.close();
	        if (psCurso != null) psCurso.close();
	        if (rsCurso != null) rsCurso.close();
	        if (psBuscaCurso != null) psBuscaCurso.close();
	        if (rsBuscaCurso != null) rsBuscaCurso.close();
	        ConnectionFactory.closeConnection(conn);
	    }
	}
	
	public Aluno buscarPorRgm(int rgm) throws Exception {
	    Aluno aluno = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT a.*, c.id_curso, c.nome_curso, c.campus, c.periodo " +
	                     "FROM aluno a INNER JOIN curso c ON a.id_curso = c.id_curso " +
	                     "WHERE a.rgm = ?";
	        ps = (PreparedStatement) conn.prepareStatement(sql);
	        ps.setInt(1, rgm);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            aluno = new Aluno();
	            aluno.setRgm(rs.getInt("rgm"));
	            aluno.setNome(rs.getString("nome_aluno"));
	            aluno.setDataNascimento(rs.getString("data_nascimento"));
	            aluno.setCpf(rs.getString("cpf"));
	            aluno.setEmail(rs.getString("email"));
	            aluno.setEndereco(rs.getString("endereco"));
	            aluno.setMunicipio(rs.getString("municipio"));
	            aluno.setUf(rs.getString("UF"));
	            aluno.setCelular(rs.getString("celular"));

	            Curso curso = new Curso();
	            curso.setId(rs.getInt("id_curso"));
	            curso.setNome(rs.getString("nome_curso"));
	            curso.setCampus(rs.getString("campus"));
	            curso.setPeriodo(rs.getString("periodo"));

	            aluno.setCurso(curso);
	        }

	    } catch (SQLException e) {
	        throw new Exception("Erro ao buscar aluno por RGM: " + e.getMessage());
	    } finally {
	        if (ps != null) ps.close();
	        if (rs != null) rs.close();
	    }

	    return aluno;
	}
	
	public void alterar(Aluno aluno) throws Exception {
	    PreparedStatement ps = null;

	    try {
	        // 1. Atualiza os dados do curso já vinculado
	        String sqlCurso = "UPDATE curso SET nome_curso = ?, campus = ?, periodo = ? WHERE id_curso = ?";
	        ps = (PreparedStatement) conn.prepareStatement(sqlCurso);
	        ps.setString(1, aluno.getCurso().getNome());
	        ps.setString(2, aluno.getCurso().getCampus());
	        ps.setString(3, aluno.getCurso().getPeriodo());
	        ps.setInt(4, aluno.getCurso().getId()); // <- usa o ID que já está vinculado ao aluno
	        ps.executeUpdate();
	        ps.close();

	        // 2. Atualiza os dados do aluno (sem mudar o id_curso)
	        String sqlAluno = "UPDATE aluno SET nome_aluno = ?, data_nascimento = ?, cpf = ?, email = ?, endereco = ?, municipio = ?, UF = ?, celular = ? WHERE rgm = ?";
	        ps = (PreparedStatement) conn.prepareStatement(sqlAluno);
	        ps.setString(1, aluno.getNome());
	        ps.setString(2, aluno.getDataNascimento());
	        ps.setString(3, aluno.getCpf());
	        ps.setString(4, aluno.getEmail());
	        ps.setString(5, aluno.getEndereco());
	        ps.setString(6, aluno.getMunicipio());
	        ps.setString(7, aluno.getUf());
	        ps.setString(8, aluno.getCelular());
	        ps.setInt(9, aluno.getRgm());

	        ps.executeUpdate();

	    } finally {
	        if (ps != null) ps.close();
	    }
	}

	public void excluir(int rgm) throws Exception {
	    PreparedStatement ps1 = null;
	    PreparedStatement ps2 = null;

	    try {
	        // 1. Excluir desempenhos vinculados
	        String sqlDesempenho = "DELETE FROM desempenho WHERE id_aluno = ?";
	        ps1 = (PreparedStatement) conn.prepareStatement(sqlDesempenho);
	        ps1.setInt(1, rgm);
	        ps1.executeUpdate();

	        // 2. Excluir aluno
	        String sqlAluno = "DELETE FROM aluno WHERE rgm = ?";
	        ps2 = (PreparedStatement) conn.prepareStatement(sqlAluno);
	        ps2.setInt(1, rgm);
	        ps2.executeUpdate();

	    } finally {
	        if (ps1 != null) ps1.close();
	        if (ps2 != null) ps2.close();
	    }
	}
	
	public int getCursoIdOuCadastrar(Curso curso) throws Exception {
		String sqlVerifica = "SELECT id_curso FROM curso WHERE nome_curso = ? AND campus = ? AND periodo = ?";
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        ps = (PreparedStatement) conn.prepareStatement(sqlVerifica);
	        ps.setString(1, curso.getNome());
	        ps.setString(2, curso.getCampus());
	        ps.setString(3, curso.getPeriodo());
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("id_curso");
	        }

	        // Curso não existe, insere
	        ps.close();
	        rs.close();

	        String sqlInsere = "INSERT INTO curso (nome_curso, campus, periodo) VALUES (?, ?, ?)";
	        ps = (PreparedStatement) conn.prepareStatement(sqlInsere, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, curso.getNome());
	        ps.setString(2, curso.getCampus());
	        ps.setString(3, curso.getPeriodo());
	        ps.executeUpdate();

	        rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            return rs.getInt(1);
	        } else {
	            throw new Exception("Erro ao obter ID do curso.");
	        }

	    } finally {
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	    }
	}


}
