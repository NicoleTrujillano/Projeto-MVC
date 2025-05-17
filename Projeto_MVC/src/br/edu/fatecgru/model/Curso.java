package br.edu.fatecgru.model;

public class Curso {
	
	private Integer id;
	private String nome;
	private String campus;
	private String periodo;
	
	public Curso() {
	}

	public Curso(Integer id, String nome, String campus, String periodo) {
		this.id = id;
		this.nome = nome;
		this.campus = campus;
		this.periodo = periodo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	

	
}
