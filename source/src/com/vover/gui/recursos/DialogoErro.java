package com.vover.gui.recursos;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.vover.gui.Dialogo;

public class DialogoErro extends Dialogo {

	private static final long serialVersionUID = 1L;
	private JPanel painelNorte, painelCentro;
	private JLabel rotuloMensagem, rotuloImagem;
	private JButton botaoOK;
	private String mensagem;
	
	public DialogoErro(JFrame janelaPai, String titulo, String mensagem) {
		super();
		this.mensagem = mensagem;
		criarElementos();
		customizarElementos();
		adicionarElementos();
		configurarEventos();
		definirPropriedades(janelaPai, titulo, null);
	}

	@Override
	protected void adicionarElementos() {
		
		painelNorte.add(rotuloImagem);
		painelNorte.add(rotuloMensagem);
		
		painelCentro.add(botaoOK);
		
		add(painelNorte, BorderLayout.NORTH);
		add(painelCentro, BorderLayout.CENTER);
	}

	@Override
	protected void configurarEventos() {
		botaoOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				getThis().dispose();
			}
		});
	}

	@Override
	protected void criarElementos() {
		painelNorte = new JPanel();
		painelCentro = new JPanel();
		
		botaoOK = new JButton("OK");
		
		rotuloMensagem = new JLabel(this.mensagem);
		rotuloImagem = new JLabel(new ImageIcon(getResource("cancelar.png")));
	}

	@Override
	protected void customizarElementos() {
		botaoOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botaoOK.setToolTipText("Fechar Mensagem.");
	}
	
	public DialogoErro getThis() {
		return this;
	}
}
