package br.edu.fatecgru.model;

public class Disciplina {
	
	private Integer id;
	private String disciplina;

	public Disciplina() {
	}

	public Disciplina(Integer id, String disciplina) {
		this.id = id;
		this.disciplina = disciplina;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	
	
}
