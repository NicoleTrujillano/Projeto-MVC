package br.edu.fatecgru.view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.text.MaskFormatter;

import com.mysql.jdbc.PreparedStatement;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.awt.event.InputEvent;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Label;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.SystemColor;

import br.edu.fatecgru.dao.AlunoDAO;
import br.edu.fatecgru.dao.CursoDAO;
import br.edu.fatecgru.dao.DesempenhoDAO;
import br.edu.fatecgru.dao.DisciplinaDAO;
import br.edu.fatecgru.model.Aluno;
import br.edu.fatecgru.model.Curso;
import br.edu.fatecgru.model.Disciplina;
import br.edu.fatecgru.model.Desempenho;
import br.edu.fatecgru.util.ConnectionFactory;

public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox txtCurso;
	private JComboBox txtCampus;
	private JTextField txtPeriodo;
	private JTextField txtRgm;
	private JTextField txtNome;
	private JTextField txtData;
	private JTextField txtCpf;
	private JTextField txtEmail;
	private JTextField txtEndereco;
	private JTextField txtMunicipio;
	private JComboBox txtUF;
	private JTextField txtCelular;
	private JLabel lblMensagem;
	private JTextField txtDesempenhoRgm;
	private JTextField txtFaltas;
	private JRadioButton rdbMatutino;
	private JRadioButton rdbVespertino;
	private JRadioButton rdbNoturno;
	private JComboBox cmbDisciplina;
	private JComboBox cmbSemestre;
	private JComboBox cmbNotas;
	private JTextField txtBoletimRgm;
	private JMenuItem menuAlterar;
	private JMenuItem menuExcluir;
	private JMenuItem mntmNewMenuItem_5;
	private JMenuItem mntmNewMenuItem_6;
	private JMenuItem mntmNewMenuItem_7;
	
	public int buscarIdDisciplinaPorNome(String nomeDisciplina) throws Exception {
	    Connection conn = ConnectionFactory.getConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT id_disciplina FROM disciplina WHERE nome_disciplina = ?";
	        ps = (PreparedStatement) conn.prepareStatement(sql);
	        ps.setString(1, nomeDisciplina);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("id_disciplina");
	        } else {
	            throw new Exception("Disciplina não encontrada: " + nomeDisciplina);
	        }
	    } finally {
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	        ConnectionFactory.closeConnection(conn);
	    }
	}
	
	public String[] getDisciplinasPorCurso(String nomeCurso) {
        if (nomeCurso.equalsIgnoreCase("Análise e Desenvolvimento de Sistemas")) {
            return new String[] {
                "Programação Orientada a Objetos",
                "Banco de Dados",
                "Engenharia de Software III"
            };
        } else if (nomeCurso.equalsIgnoreCase("Logística")) {
            return new String[] {
                "Gestão de Estoques",
                "Transporte e Distribuição",
                "Logística Internacional"
            };
        } else if (nomeCurso.equalsIgnoreCase("Comércio Exterior")) {
            return new String[] {
                "Economia Internacional",
                "Legislação Aduaneira",
                "Negócios Internacionais"
            };
        } else {
            return new String[] {};
        }
    }


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() throws Exception {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 467);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Aluno");
		mnNewMenu.setForeground(Color.WHITE);
		mnNewMenu.setBackground(Color.GRAY);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Salvar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));
				//======================================
				try {
		            // Criar o curso com os dados dos campos
		            Curso curso = new Curso();
		            curso.setNome((String) txtCurso.getSelectedItem());
		            curso.setCampus((String) txtCampus.getSelectedItem());
		            if (rdbMatutino.isSelected()) {
		                curso.setPeriodo("Matutino");
		            } else if (rdbVespertino.isSelected()) {
		                curso.setPeriodo("Vespertino");
		            } else if (rdbNoturno.isSelected()) {
		                curso.setPeriodo("Noturno");
		            } else {
		                curso.setPeriodo("Não informado");
		            }
		            
		            // Criar o aluno com os dados dos campos
		            Aluno aluno = new Aluno();
		            aluno.setRgm(Integer.parseInt(txtRgm.getText()));
		            aluno.setNome(txtNome.getText());
		            aluno.setDataNascimento(txtData.getText());
		            aluno.setCpf(txtCpf.getText());
		            aluno.setEmail(txtEmail.getText());
		            aluno.setEndereco(txtEndereco.getText());
		            aluno.setMunicipio(txtMunicipio.getText());
		            aluno.setUf((String) txtUF.getSelectedItem());
		            aluno.setCelular(txtCelular.getText());
		            aluno.setCurso(curso); // vincula o curso criado

		            // Salvar no banco
		            AlunoDAO dao = new AlunoDAO();
		            dao.salvar(aluno);

		            JOptionPane.showMessageDialog(null, "Aluno salvo com sucesso!");

		        } catch (Exception ex) {
		            ex.printStackTrace(); // útil para debug
		            JOptionPane.showMessageDialog(null, "Erro ao salvar aluno.\n"+
		            		"Verifique se todos os campos obrigatórios estão preenchidos. Use o botão 'Verificar Dados' para ajudar.");
		        }
		    }
				//======================================
		});
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem menuConsultar = new JMenuItem("Consultar");
		menuConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int rgm = Integer.parseInt(txtRgm.getText());
		            AlunoDAO dao = new AlunoDAO();
		            Aluno aluno = dao.buscarPorRgm(rgm);

		            if (aluno != null) {
		                txtNome.setText(aluno.getNome());
		                txtData.setText(aluno.getDataNascimento());
		                txtCpf.setText(aluno.getCpf());
		                txtEmail.setText(aluno.getEmail());
		                txtEndereco.setText(aluno.getEndereco());
		                txtMunicipio.setText(aluno.getMunicipio());
		                txtUF.setSelectedItem(aluno.getUf());
		                txtCelular.setText(aluno.getCelular());

		                txtCurso.setSelectedItem(aluno.getCurso().getNome());
		                txtCampus.setSelectedItem(aluno.getCurso().getCampus());
		                String periodo = aluno.getCurso().getPeriodo();
		                rdbMatutino.setSelected("Matutino".equals(periodo));
		                rdbVespertino.setSelected("Vespertino".equals(periodo));
		                rdbNoturno.setSelected("Noturno".equals(periodo));

		                txtRgm.setEnabled(false); // desabilita o RGM após consulta
		                menuAlterar.setEnabled(true);
		                menuExcluir.setEnabled(true);
		                mntmNewMenuItem.setEnabled(false);
		            } else {
		                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao consultar aluno.");
		        }
		    }
		});
		menuConsultar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(menuConsultar);
		
		menuExcluir = new JMenuItem("Excluir");
		menuExcluir.setEnabled(false);
		menuExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int rgm = Integer.parseInt(txtRgm.getText());
		            int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);

		            if (confirm == JOptionPane.YES_OPTION) {
		                AlunoDAO dao = new AlunoDAO();
		                dao.excluir(rgm);
		                JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!");

		                // Limpa os campos
		                txtRgm.setText("");
		                txtNome.setText("");
		                txtData.setText("");
		                txtCpf.setText("");
		                txtEmail.setText("");
		                txtEndereco.setText("");
		                txtMunicipio.setText("");
		                txtUF.setSelectedIndex(0);
		                txtCelular.setText("");
		                txtCurso.setSelectedIndex(0);
		                txtCampus.setSelectedIndex(0);
		                rdbMatutino.setSelected(false);
		                rdbVespertino.setSelected(false);
		                rdbNoturno.setSelected(false);
		                txtRgm.setEnabled(true); // reativa o campo RGM
		                mntmNewMenuItem.setEnabled(true);
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao excluir aluno.");
		        }
			}
		});
		
		menuAlterar = new JMenuItem("Atualizar");
		menuAlterar.setEnabled(false);
		menuAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				    int rgm = Integer.parseInt(txtRgm.getText());

				    // Buscar aluno atual
				    AlunoDAO alunoDAO = new AlunoDAO();
				    Aluno alunoAntigo = alunoDAO.buscarPorRgm(rgm);
				    Curso cursoAntigo = alunoAntigo.getCurso();

				    // Novo curso com os dados da tela, mas mantendo o ID antigo
				    Curso cursoNovo = new Curso();
				    cursoNovo.setId(cursoAntigo.getId()); // ❗️REUTILIZA O ID ANTIGO
				    cursoNovo.setNome((String) txtCurso.getSelectedItem());
				    cursoNovo.setCampus((String) txtCampus.getSelectedItem());

				    String periodo = "";
				    if (rdbMatutino.isSelected()) periodo = "Matutino";
				    if (rdbVespertino.isSelected()) periodo = "Vespertino";
				    if (rdbNoturno.isSelected()) periodo = "Noturno";
				    cursoNovo.setPeriodo(periodo);

				    // Verifica se o curso foi alterado
				    boolean cursoMudou = !cursoNovo.getNome().equalsIgnoreCase(cursoAntigo.getNome()) ||
				                         !cursoNovo.getCampus().equalsIgnoreCase(cursoAntigo.getCampus()) ||
				                         !cursoNovo.getPeriodo().equalsIgnoreCase(cursoAntigo.getPeriodo());

				    // Atualizar o curso diretamente no banco
				    if (cursoMudou) {
				        CursoDAO cursoDAO = new CursoDAO();
				        cursoDAO.alterar(cursoNovo); // Atualiza o curso existente
				    }

				    // Montar novo aluno
				    Aluno alunoNovo = new Aluno();
				    alunoNovo.setRgm(rgm);
				    alunoNovo.setNome(txtNome.getText());
				    alunoNovo.setDataNascimento(txtData.getText());
				    alunoNovo.setCpf(txtCpf.getText());
				    alunoNovo.setEmail(txtEmail.getText());
				    alunoNovo.setEndereco(txtEndereco.getText());
				    alunoNovo.setMunicipio(txtMunicipio.getText());
				    alunoNovo.setUf((String) txtUF.getSelectedItem());
				    alunoNovo.setCelular(txtCelular.getText());
				    alunoNovo.setCurso(cursoNovo); // mantém o mesmo id_curso

				    // Atualiza o aluno
				    alunoDAO.alterar(alunoNovo);

				    if (cursoMudou) {
				        DesempenhoDAO desempenhoDAO = new DesempenhoDAO();
				        desempenhoDAO.excluirPorAluno(rgm);
				        JOptionPane.showMessageDialog(null,
				            "Dados do aluno e do curso atualizados.\nAs disciplinas anteriores foram removidas devido à mudança de curso.");
				    } else {
				        JOptionPane.showMessageDialog(null, "Dados do aluno atualizados com sucesso.");
				    }

				} catch (Exception ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, "Erro ao alterar aluno: " + ex.getMessage());
				}
			}
		});
		menuAlterar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu.add(menuAlterar);
		menuExcluir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		mnNewMenu.add(menuExcluir);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Sair");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmNewMenuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		mntmNewMenuItem_4.setBorderPainted(true);
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_1 = new JMenu("Notas e Faltas");
		mnNewMenu_1.setForeground(Color.WHITE);
		mnNewMenu_1.setBackground(Color.GRAY);
		menuBar.add(mnNewMenu_1);
		
		mntmNewMenuItem_5 = new JMenuItem("Salvar");
		mntmNewMenuItem_5.setEnabled(false);
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				    int rgm = Integer.parseInt(txtDesempenhoRgm.getText());
				    String nomeDisciplina = (String) cmbDisciplina.getSelectedItem();
				    String semestre = (String) cmbSemestre.getSelectedItem();
				    double nota = Double.parseDouble(((String) cmbNotas.getSelectedItem()).replace(",", "."));
				    int faltas = Integer.parseInt(txtFaltas.getText());

				    // Buscar ID da disciplina
				    int idDisciplina = buscarIdDisciplinaPorNome(nomeDisciplina);

				    // Criar objetos
				    Desempenho desempenho = new Desempenho();

		            DesempenhoDAO dao = new DesempenhoDAO();
				    
		            if (dao.desempenhoExiste(rgm, idDisciplina)) {
		                JOptionPane.showMessageDialog(null,
		                    "Já existe um desempenho cadastrado para este aluno nesta disciplina.\n\n" +
		                    "➡ Primeiro clique no botão 'Consultar Disciplina' para carregar os dados.\n" +
		                    "➡ Depois utilize o botão 'Alterar' para modificar as informações.",
		                    "Salvar não permitido", JOptionPane.WARNING_MESSAGE);
		                return;
		            }
				    
				    Aluno aluno = new Aluno();
				    aluno.setRgm(rgm);
				    desempenho.setAluno(aluno);

				    Disciplina disciplina = new Disciplina();
				    disciplina.setId(idDisciplina);
				    disciplina.setDisciplina(nomeDisciplina);
				    desempenho.setDisciplina(disciplina);

				    desempenho.setSemestre(semestre);
				    desempenho.setNota(nota);
				    desempenho.setFaltas(faltas);

				    // Se já existe esse aluno + disciplina, vamos ALTERAR, não salvar
				    if (dao.desempenhoExiste(rgm, idDisciplina)) {
				        dao.alterar(desempenho);
				        JOptionPane.showMessageDialog(null, "Desempenho atualizado com sucesso!");
				    } else {
				        dao.salvar(desempenho);
				        JOptionPane.showMessageDialog(null, "Nota e Faltas salvas com sucesso!");
				    }

				} catch (Exception ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, "Erro ao salvar desempenho: " + ex.getMessage());
				}
			}
		});
		mntmNewMenuItem_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		mntmNewMenuItem_6 = new JMenuItem("Atualizar");
		mntmNewMenuItem_6.setEnabled(false);
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int rgm = Integer.parseInt(txtDesempenhoRgm.getText());
		            String nomeDisciplina = (String) cmbDisciplina.getSelectedItem();
		            String semestre = (String) cmbSemestre.getSelectedItem();
		            double nota = Double.parseDouble(((String) cmbNotas.getSelectedItem()).replace(",", "."));
		            int faltas = Integer.parseInt(txtFaltas.getText());

		            int idDisciplina = buscarIdDisciplinaPorNome(nomeDisciplina);

		            DesempenhoDAO dao = new DesempenhoDAO();

		            // ⚠ Verifica se existe o desempenho antes de permitir alteração
		            if (!dao.desempenhoExiste(rgm, idDisciplina)) {
		                JOptionPane.showMessageDialog(null, 
		                    "Nenhum registro encontrado para este aluno nesta disciplina.\n" +
		                    "Cadastre primeiro para depois alterar.",
		                    "Alteração não permitida",
		                    JOptionPane.WARNING_MESSAGE
		                );
		                return;
		            }

		            Aluno aluno = new Aluno();
		            aluno.setRgm(rgm);

		            Disciplina disciplina = new Disciplina();
		            disciplina.setId(idDisciplina);

		            Desempenho desempenho = new Desempenho(aluno, disciplina, semestre, nota, faltas);

		            dao.alterar(desempenho);

		            JOptionPane.showMessageDialog(null, "Desempenho alterado com sucesso!");

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao alterar desempenho: " + ex.getMessage());
		        }
		    }
		});
		mntmNewMenuItem_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		mntmNewMenuItem_7 = new JMenuItem("Excluir");
		mntmNewMenuItem_7.setEnabled(false);
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int rgm = Integer.parseInt(txtDesempenhoRgm.getText());
		            String nomeDisciplina = (String) cmbDisciplina.getSelectedItem();
		            String semestre = (String) cmbSemestre.getSelectedItem();

		            int idDisciplina = buscarIdDisciplinaPorNome(nomeDisciplina);

		            int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir este desempenho?", "Confirmação", JOptionPane.YES_NO_OPTION);

		            if (confirm == JOptionPane.YES_OPTION) {
		                Aluno aluno = new Aluno();
		                aluno.setRgm(rgm);

		                Disciplina disciplina = new Disciplina();
		                disciplina.setId(idDisciplina);

		                Desempenho desempenho = new Desempenho();
		                desempenho.setAluno(aluno);
		                desempenho.setDisciplina(disciplina);
		                desempenho.setSemestre(semestre);

		                DesempenhoDAO dao = new DesempenhoDAO();
		                dao.excluir(desempenho);

		                JOptionPane.showMessageDialog(null, "Desempenho excluído com sucesso!");

		                // Limpa os campos
		                cmbNotas.setSelectedIndex(0);
		                txtFaltas.setText("");
		                cmbDisciplina.setSelectedIndex(0);
		                cmbSemestre.setSelectedIndex(0);

		                // Ajusta os botões
		                mntmNewMenuItem_5.setEnabled(true);  // "Salvar"
		                mntmNewMenuItem_6.setEnabled(false); // "Alterar"
		                mntmNewMenuItem_7.setEnabled(false); // "Excluir"
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao excluir desempenho: " + ex.getMessage());
		        }

			}
		});
		mntmNewMenuItem_7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnNewMenu_1.add(mntmNewMenuItem_7);
		
		JMenu mnNewMenu_2 = new JMenu("Ajuda");
		mnNewMenu_2.setForeground(Color.WHITE);
		mnNewMenu_2.setBackground(Color.GRAY);
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Sobre");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//=========================
				String mensagem = """
		                SISTEMA DE GESTÃO ACADÊMICA - FATEC
		                
		                Este sistema permite o gerenciamento de alunos, cursos, disciplinas e desempenhos acadêmicos.

		                FUNCIONALIDADES DISPONÍVEIS:

		                ▶ Aba "Dados Pessoais":
		                - Cadastrar novo aluno com dados pessoais e curso.
		                - Consultar os dados de um aluno já cadastrado informando o RGM.
		                - Alterar ou excluir dados do aluno existente.

		                ▶ Aba "Curso":
		                - Associar o aluno a um curso específico (nome, campus e período).

		                ▶ Aba "Notas e Faltas":
		                - Cadastrar desempenho do aluno em uma disciplina (nota, faltas e semestre).
		                - Alterar ou excluir dados de desempenho existentes.
		                - Consultar uma disciplina específica para carregar seus dados.
		                - Visualizar todas as disciplinas em que o aluno possui registro.

		                ▶ Aba "Boletim":
		                - Gerar um boletim completo do aluno com suas disciplinas, notas e faltas.

		                ▶ Menu "Ajuda > Sobre":
		                - Exibe esta explicação do sistema.

		                DICA: Sempre insira um RGM válido antes de executar qualquer ação relacionada ao aluno.

		                Desenvolvido por Nicole e companhia
		                """;

		        JTextArea area = new JTextArea(mensagem);
		        area.setEditable(false);
		        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

		        JScrollPane scroll = new JScrollPane(area);
		        scroll.setPreferredSize(new Dimension(600, 400));

		        JOptionPane.showMessageDialog(null, scroll, 
		            "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
				//=========================
			}
		});
		mntmNewMenuItem_9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mnNewMenu_2.add(mntmNewMenuItem_9);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(SystemColor.text);
		tabbedPane.setBackground(SystemColor.windowText);
		tabbedPane.setBounds(10, 40, 670, 355);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Dados Pessoais", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(281, 14, 46, 22);
		panel.add(lblNewLabel);
		
		txtNome = new JTextField();
		txtNome.setBackground(new Color(255, 255, 255));
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNome.setBounds(337, 11, 318, 28);
		panel.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("RGM");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 14, 37, 22);
		panel.add(lblNewLabel_1);
		
		txtRgm = new JTextField();
		txtRgm.setBackground(new Color(255, 255, 255));
		txtRgm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRgm.setBounds(57, 11, 195, 28);
		panel.add(txtRgm);
		txtRgm.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Data de Nascimento");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 66, 158, 22);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("CPF");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(364, 66, 37, 22);
		panel.add(lblNewLabel_3);
		
		txtData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		txtData.setBackground(new Color(255, 255, 255));
		txtData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtData.setBounds(178, 63, 158, 28);
		panel.add(txtData);
		
		txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		txtCpf.setBackground(new Color(255, 255, 255));
		txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCpf.setBounds(411, 63, 244, 28);
		panel.add(txtCpf);
		
		JLabel lblNewLabel_4 = new JLabel("Celular");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(394, 216, 76, 22);
		panel.add(lblNewLabel_4);
		
		txtCelular = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		txtCelular.setBackground(new Color(255, 255, 255));
		txtCelular.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCelular.setBounds(464, 213, 191, 28);
		panel.add(txtCelular);
		
		JLabel lblNewLabel_5 = new JLabel("UF");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(281, 216, 21, 22);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Município");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_6.setBounds(12, 216, 74, 22);
		panel.add(lblNewLabel_6);
		
		txtMunicipio = new JTextField();
		txtMunicipio.setBackground(new Color(255, 255, 255));
		txtMunicipio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMunicipio.setBounds(96, 213, 156, 28);
		panel.add(txtMunicipio);
		txtMunicipio.setColumns(10);
		
		txtUF = new JComboBox();
		txtUF.setBackground(new Color(255, 255, 255));
		txtUF.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtUF.setModel(new DefaultComboBoxModel(new String[] {"SP", "SC", "RN", "RJ", "RS", "MG", "MS"}));
		txtUF.setBounds(312, 213, 60, 28);
		panel.add(txtUF);
		
		JLabel lblNewLabel_7 = new JLabel("Email");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_7.setBounds(11, 115, 42, 22);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Endereço");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_8.setBounds(11, 165, 72, 22);
		panel.add(lblNewLabel_8);
		
		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(255, 255, 255));
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEmail.setBounds(63, 112, 592, 28);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setBackground(new Color(255, 255, 255));
		txtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEndereco.setBounds(95, 162, 560, 28);
		panel.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		JLabel lblNewLabel_17 = new JLabel("Após preencher tudo com os dados pessoas do aluno, seguir para a aba curso.");
		lblNewLabel_17.setForeground(Color.RED);
		lblNewLabel_17.setBounds(10, 302, 474, 14);
		panel.add(lblNewLabel_17);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Curso", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_9 = new JLabel("Curso");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_9.setBounds(10, 15, 76, 22);
		panel_1.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Campus");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_10.setBounds(10, 64, 76, 22);
		panel_1.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Período");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_11.setBounds(10, 116, 76, 22);
		panel_1.add(lblNewLabel_11);
		
		txtCurso = new JComboBox();
		txtCurso.setBackground(new Color(255, 255, 255));
		txtCurso.setModel(new DefaultComboBoxModel(new String[] {"Análise e Desenvolvimento de Sistemas", "Comércio Exterior", "Logística"}));
		txtCurso.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCurso.setBounds(96, 11, 344, 28);
		panel_1.add(txtCurso);
		
		txtCampus = new JComboBox();
		txtCampus.setBackground(new Color(255, 255, 255));
		txtCampus.setModel(new DefaultComboBoxModel(new String[] {"Guarulhos", "Tatuapé", "Carapicuíba", "Mauá", "São Caetano do Sul", "Mogi das Cruzes", "São Paulo"}));
		txtCampus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCampus.setBounds(96, 60, 189, 28);
		panel_1.add(txtCampus);
		
		/*
		 * RADIO BUTTON MATUTINO VESPERTINO NOTURNO
		 */
		
		rdbMatutino = new JRadioButton("Matutino");
		rdbMatutino.setBackground(Color.LIGHT_GRAY);
		rdbMatutino.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbMatutino.setBounds(96, 112, 95, 31);
		panel_1.add(rdbMatutino);
		
		rdbVespertino = new JRadioButton("Vespertino");
		rdbVespertino.setBackground(Color.LIGHT_GRAY);
		rdbVespertino.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbVespertino.setBounds(214, 112, 109, 31);
		panel_1.add(rdbVespertino);
		
		rdbNoturno = new JRadioButton("Noturno");
		rdbNoturno.setBackground(Color.LIGHT_GRAY);
		rdbNoturno.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbNoturno.setBounds(346, 112, 89, 31);
		panel_1.add(rdbNoturno);
		
		ButtonGroup g1 = new ButtonGroup();
        g1.add(rdbMatutino); g1.add(rdbVespertino); g1.add(rdbNoturno);
		
		//=========================================
		
		JButton btnNewButton = new JButton("Verificar Informações");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 16));
				UIManager.put("OptionPane.buttonFont", new Font("Tahoma", Font.BOLD, 14));

				String periodo = "";
				if (rdbMatutino.isSelected()) periodo = "Matutino";
				if (rdbVespertino.isSelected()) periodo = "Vespertino";
				if (rdbNoturno.isSelected()) periodo = "Noturno";

				StringBuilder mensagem = new StringBuilder();
				mensagem.append("DADOS PESSOAIS DO ALUNO\n\n");
				mensagem.append("RGM: ").append(txtRgm.getText()).append("\n");
				mensagem.append("Nome: ").append(txtNome.getText()).append("\n");
				mensagem.append("Data de Nascimento: ").append(txtData.getText()).append("\n");
				mensagem.append("CPF: ").append(txtCpf.getText()).append("\n");
				mensagem.append("Endereço: ").append(txtEndereco.getText()).append("\n");
				mensagem.append("Município: ").append(txtMunicipio.getText()).append("\n");
				mensagem.append("UF: ").append(txtUF.getSelectedItem()).append("\n");
				mensagem.append("Celular: ").append(txtCelular.getText()).append("\n\n");
				mensagem.append("------------------------------------------------------------\n\n");
				mensagem.append("CURSO\n\n");
				mensagem.append("Curso: ").append(txtCurso.getSelectedItem()).append("\n");
				mensagem.append("Campus: ").append(txtCampus.getSelectedItem()).append("\n");
				mensagem.append("Período: ").append(periodo).append("\n");

				JTextArea area = new JTextArea(mensagem.toString());
				area.setEditable(false);
				area.setFont(new Font("Monospaced", Font.PLAIN, 14));
				area.setBackground(UIManager.getColor("Panel.background")); // mesma cor do fundo
				area.setMargin(new Insets(10, 10, 10, 10));

				JScrollPane scroll = new JScrollPane(area);
				scroll.setPreferredSize(new Dimension(500, 350));

				JOptionPane.showMessageDialog(null, scroll, 
				    "Verificação dos Dados", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton.setBounds(10, 263, 158, 53);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Limpar Campos");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRgm.setText("");
			    txtNome.setText("");
			    txtData.setText("");
			    txtCpf.setText("");
			    txtEmail.setText("");
			    txtEndereco.setText("");
			    txtMunicipio.setText("");
			    txtUF.setSelectedIndex(0); // volta ao primeiro item da lista
			    txtCelular.setText("");
			    txtCurso.setSelectedIndex(0); // volta ao primeiro curso
			    txtCampus.setSelectedIndex(0); // volta ao primeiro campus

			    rdbMatutino.setSelected(false);
			    rdbVespertino.setSelected(false);
			    rdbNoturno.setSelected(false);
			    mntmNewMenuItem.setEnabled(true);
			    txtRgm.setEnabled(true);
			    menuAlterar.setEnabled(false);
			    menuExcluir.setEnabled(false);
			}
		});
		btnNewButton_1.setBounds(178, 263, 158, 53);
		panel_1.add(btnNewButton_1);
		
		JLabel lblNewLabel_19 = new JLabel("Para alterar algum dado pessoal, volte para aba dados pessoais.");
		lblNewLabel_19.setForeground(Color.RED);
		lblNewLabel_19.setBounds(10, 238, 430, 14);
		panel_1.add(lblNewLabel_19);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Notas e Faltas", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_12 = new JLabel("RGM");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_12.setBounds(10, 14, 37, 22);
		panel_2.add(lblNewLabel_12);
		
		txtDesempenhoRgm = new JTextField();
		txtDesempenhoRgm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDesempenhoRgm.setBounds(57, 11, 173, 28);
		panel_2.add(txtDesempenhoRgm);
		txtDesempenhoRgm.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("Disciplina");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_13.setBounds(10, 146, 76, 22);
		panel_2.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("Semestre");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_14.setBounds(10, 190, 72, 22);
		panel_2.add(lblNewLabel_14);
		
		cmbDisciplina = new JComboBox();
		cmbDisciplina.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cmbDisciplina.setBounds(96, 142, 559, 30);
		panel_2.add(cmbDisciplina);
		
		cmbSemestre = new JComboBox();
		cmbSemestre.setModel(new DefaultComboBoxModel(new String[] {"2020-1", "2020-2", "2021-1", "2021-2", "2022-2", "2023-1", "2023-2", "2024-1", "2024-2", "2025-1"}));
		cmbSemestre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cmbSemestre.setBounds(92, 186, 91, 28);
		panel_2.add(cmbSemestre);
		
		JLabel lblNewLabel_15 = new JLabel("Nota");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_15.setBounds(193, 187, 37, 22);
		panel_2.add(lblNewLabel_15);
		
		cmbNotas = new JComboBox();
		cmbNotas.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cmbNotas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cmbNotas.setBounds(240, 184, 59, 28);
		panel_2.add(cmbNotas);
		
		JLabel lblNewLabel_16 = new JLabel("Faltas");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_16.setBounds(309, 187, 45, 22);
		panel_2.add(lblNewLabel_16);
		
		txtFaltas = new JTextField();
		txtFaltas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtFaltas.setBounds(364, 184, 59, 28);
		panel_2.add(txtFaltas);
		txtFaltas.setColumns(10);
		
		JLabel lblMostraNome = new JLabel("");
		lblMostraNome.setForeground(Color.BLACK);
		lblMostraNome.setBackground(new Color(255, 255, 255));
		lblMostraNome.setEnabled(false);
		lblMostraNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMostraNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, new Color(0, 0, 0), new Color(0, 0, 0)));
		lblMostraNome.setBounds(10, 58, 645, 28);
		panel_2.add(lblMostraNome);
		
		JLabel lblMostraCurso = new JLabel("");
		lblMostraCurso.setForeground(Color.BLACK);
		lblMostraCurso.setEnabled(false);
		lblMostraCurso.setBackground(new Color(255, 255, 255));
		lblMostraCurso.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMostraCurso.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, new Color(0, 0, 0), new Color(0, 0, 0)));
		lblMostraCurso.setBounds(10, 91, 645, 28);
		panel_2.add(lblMostraCurso);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDesempenhoRgm.setText("");
			    lblMostraNome.setText("");
			    lblMostraCurso.setText("");
			    cmbDisciplina.setSelectedIndex(0);
			    cmbSemestre.setSelectedIndex(0);
			    cmbNotas.setSelectedIndex(0);
			    txtFaltas.setText("");

			    
			    mntmNewMenuItem_5.setEnabled(false);
			    mntmNewMenuItem_6.setEnabled(false);
			    mntmNewMenuItem_7.setEnabled(false);
			    txtDesempenhoRgm.setEnabled(true);
				
			}
		});
		btnLimpar.setBounds(342, 263, 156, 53);
		panel_2.add(btnLimpar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int rgm = Integer.parseInt(txtDesempenhoRgm.getText());
		            AlunoDAO dao = new AlunoDAO();
		            Aluno aluno = dao.buscarPorRgm(rgm);

		            if (aluno != null) {
		                // Mostra nome e curso nos labels
		                lblMostraNome.setText(aluno.getNome());
		                lblMostraCurso.setText(aluno.getCurso().getNome());

		                // Preenche comboBox_3 com disciplinas conforme o curso
		                String[] disciplinas = getDisciplinasPorCurso(aluno.getCurso().getNome());
		                cmbDisciplina.removeAllItems();
		                for (String disciplina : disciplinas) {
		                	cmbDisciplina.addItem(disciplina);
		                }
		                
		                txtDesempenhoRgm.setEnabled(false);
		                mntmNewMenuItem_5.setEnabled(true);
		            } else {
		                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		                lblMostraNome.setText("");
		                lblMostraCurso.setText("");
		                cmbDisciplina.removeAllItems();
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao buscar aluno. Insira um RGM válido.");
		        }
		    }
		});
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBuscar.setBounds(247, 10, 131, 31);
		panel_2.add(btnBuscar);
		
		JButton btnVerDisciplinas = new JButton("Ver Disciplinas");
		btnVerDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int rgm = Integer.parseInt(txtDesempenhoRgm.getText());

					AlunoDAO alunoDAO = new AlunoDAO();
					Aluno aluno = alunoDAO.buscarPorRgm(rgm); // 🔍 busca o aluno

					if (aluno == null) {
					    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
					    return;
					}

					DesempenhoDAO dao = new DesempenhoDAO();
					List<Desempenho> lista = dao.listarPorAluno(rgm);

					if (lista.isEmpty()) {
					    JOptionPane.showMessageDialog(null,
					        "Nenhuma disciplina cadastrada para este aluno.",
					        "Desempenhos do Aluno", JOptionPane.INFORMATION_MESSAGE);
					} else {
					    StringBuilder sb = new StringBuilder("Desempenhos de " + aluno.getNome() + ":\n\n"); // 🧠 aqui usa o nome
					    for (Desempenho d : lista) {
					        sb.append("Disciplina: ").append(d.getDisciplina().getDisciplina()).append("\n");
					        sb.append("Semestre: ").append(d.getSemestre()).append("\n");
					        sb.append("Nota: ").append(d.getNota()).append("\n");
					        sb.append("Faltas: ").append(d.getFaltas()).append("\n\n");
					    }

					    JTextArea area = new JTextArea(sb.toString());
					    area.setEditable(false);
					    area.setFont(new Font("Monospaced", Font.PLAIN, 14));

					    JScrollPane scroll = new JScrollPane(area);
					    scroll.setPreferredSize(new Dimension(450, 250));

					    JOptionPane.showMessageDialog(null, scroll, 
					        "Desempenhos do Aluno", JOptionPane.INFORMATION_MESSAGE);
					}


		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Informe um RGM válido.");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao buscar desempenhos: " + ex.getMessage());
		        }
		    }
		});
		btnVerDisciplinas.setBounds(176, 263, 156, 53);
		panel_2.add(btnVerDisciplinas);
		
		JButton btnNewButton_2 = new JButton("Consultar Disciplina");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rgmTexto = txtDesempenhoRgm.getText().trim();

		        if (rgmTexto.isEmpty()) {
		            JOptionPane.showMessageDialog(null,
		                "Por favor, insira um RGM válido.",
		                "RGM não informado", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        int rgm;
		        try {
		            rgm = Integer.parseInt(rgmTexto);
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null,
		                "RGM deve ser um número válido.",
		                "RGM inválido", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try {
		            String nomeDisciplina = (String) cmbDisciplina.getSelectedItem();
		            int idDisciplina = buscarIdDisciplinaPorNome(nomeDisciplina);

		            DesempenhoDAO dao = new DesempenhoDAO();
		            Desempenho desempenho = dao.buscarDesempenho(rgm, idDisciplina);

		            if (desempenho != null) {
		                cmbSemestre.setSelectedItem(desempenho.getSemestre());
		                cmbNotas.setSelectedItem(String.valueOf((int) desempenho.getNota()));
		                txtFaltas.setText(String.valueOf(desempenho.getFaltas()));

		                mntmNewMenuItem_6.setEnabled(true); // Alterar
		                mntmNewMenuItem_7.setEnabled(true); // Excluir
		                //mntmNewMenuItem_5.setEnabled(false); // Salvar

		                JOptionPane.showMessageDialog(null,
		                    "Desempenho carregado. Agora você pode alterar ou excluir.",
		                    "Consulta concluída", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                JOptionPane.showMessageDialog(null,
		                    "Nenhum desempenho encontrado para esta disciplina.",
		                    "Disciplina não encontrada", JOptionPane.WARNING_MESSAGE);

		                cmbSemestre.setSelectedIndex(0);
		                cmbNotas.setSelectedIndex(0);
		                txtFaltas.setText("");

		                mntmNewMenuItem_6.setEnabled(false);
		                mntmNewMenuItem_7.setEnabled(false);
		                mntmNewMenuItem_5.setEnabled(true);
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null,
		                "Erro ao consultar disciplina: " + ex.getMessage());
		        }
		    }
		});
		btnNewButton_2.setBounds(10, 263, 156, 53);
		panel_2.add(btnNewButton_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Boletim", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_18 = new JLabel("RGM");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_18.setBounds(10, 14, 37, 22);
		panel_3.add(lblNewLabel_18);
		
		txtBoletimRgm = new JTextField();
		txtBoletimRgm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtBoletimRgm.setBounds(57, 11, 156, 28);
		panel_3.add(txtBoletimRgm);
		txtBoletimRgm.setColumns(10);
		
		JButton btnBoletim = new JButton("Gerar Boletim");
		btnBoletim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int rgm = Integer.parseInt(txtBoletimRgm.getText());

		            AlunoDAO alunoDAO = new AlunoDAO();
		            Aluno aluno = alunoDAO.buscarPorRgm(rgm);

		            if (aluno == null) {
		                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		                return;
		            }

		            DesempenhoDAO desempenhoDAO = new DesempenhoDAO();
		            List<Desempenho> desempenhos = desempenhoDAO.listarBoletim(rgm);

		            StringBuilder sb = new StringBuilder();
		            sb.append("BOLETIM DO ALUNO\n\n");
		            sb.append("RGM: ").append(aluno.getRgm()).append("\n");
		            sb.append("Nome: ").append(aluno.getNome()).append("\n");
		            sb.append("Curso: ").append(aluno.getCurso().getNome()).append("\n\n");

		            if (desempenhos.isEmpty()) {
		                sb.append("Nenhuma disciplina cadastrada para este aluno.");
		            } else {
		                sb.append("Desempenho:\n");
		                for (Desempenho d : desempenhos) {
		                    sb.append("Disciplina: ").append(d.getDisciplina().getDisciplina()).append("\n");
		                    sb.append("Semestre: ").append(d.getSemestre()).append("\n");
		                    sb.append("Nota: ").append(d.getNota()).append("\n");
		                    sb.append("Faltas: ").append(d.getFaltas()).append("\n\n");
		                }
		            }

		            JTextArea area = new JTextArea(sb.toString());
		            area.setEditable(false);
		            area.setFont(new Font("Monospaced", Font.PLAIN, 14));
		            JScrollPane scroll = new JScrollPane(area);
		            scroll.setPreferredSize(new Dimension(500, 300));

		            JOptionPane.showMessageDialog(null, scroll, "Boletim", JOptionPane.INFORMATION_MESSAGE);

		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Informe um RGM válido.");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Erro ao gerar boletim: " + ex.getMessage());
		        }
		    }
		});
		btnBoletim.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBoletim.setBounds(10, 62, 190, 31);
		panel_3.add(btnBoletim);
	}
}
