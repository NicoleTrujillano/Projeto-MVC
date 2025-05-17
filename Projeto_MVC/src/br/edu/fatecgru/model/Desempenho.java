package br.edu.fatecgru.model;

public class Desempenho {
	
	private Integer id;
    private Aluno aluno;
    private Disciplina disciplina;
    private String semestre;
    private double nota;
    private int faltas;

    public Desempenho() {
	}

	public Desempenho(Aluno aluno, Disciplina disciplina, String semestre, double nota, int faltas) {
		super();
		this.aluno = aluno;
		this.disciplina = disciplina;
		this.semestre = semestre;
		this.nota = nota;
		this.faltas = faltas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public int getFaltas() {
		return faltas;
	}

	public void setFaltas(int faltas) {
		this.faltas = faltas;
	}
    
	
    
}
