package com.vover.gui.usuario.eventos;

import static javax.swing.JOptionPane.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.vover.gui.cliente.JanelaCliente;
import com.vover.gui.usuario.DialogoCadastro;
import com.vover.gui.usuario.DialogoLogar;
import com.vover.recursos.ConexaoCliente;

public class TratadorEventosLogar implements ActionListener {

	private DialogoLogar dialogoLogar;
	private ConexaoCliente conexaoCliente;

	public TratadorEventosLogar(DialogoLogar dialogoLogar) {
		super();
		this.dialogoLogar = dialogoLogar;
		this.conexaoCliente = new ConexaoCliente();
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent evento) {

		// Caso o evento tenha ocorrido no botao cancelar.
		if (evento.getSource() == dialogoLogar.getbotaoCadastrar()) {
			dialogoLogar.setVisible(false);
			try {
				new DialogoCadastro(dialogoLogar);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Caso o evento tenha ocorrido no botao logar.
		else if (evento.getSource() == dialogoLogar.getBotaoLogar()) {
			String ERROS = "Os Seguintes Erros Foram Encontrados:";

			String usuario = dialogoLogar.getFieldLogar().getText();
			String senha = dialogoLogar.getFieldSenha().getText();

			if (usuario.length() < 6)
				ERROS += "\nO Nick do Usuario deve Conter no Minimo 6 Caracteres.";
			if (senha.length() < 6)
				ERROS += "\nA Senha do Usuario deve Conter no Minimo 6 Caracteres.";		

			// Verifica se nao existe erros.
			if (!ERROS.equalsIgnoreCase("Os Seguintes Erros Foram Encontrados:")) 
				showMessageDialog(dialogoLogar, ERROS, "Erro", ERROR_MESSAGE);
			else {
				try {
					String resposta = logarCliente(usuario, senha);
					System.out.println(resposta);
					if (resposta.equalsIgnoreCase("NAO_AUTORIZADO")) {
						showMessageDialog(dialogoLogar, "Usuario Nao Cadastrado.", "Atencao", INFORMATION_MESSAGE);
					}
					else if (resposta.equalsIgnoreCase("OK_USUARIO_NAO_SENHA")) {
						showMessageDialog(dialogoLogar, "Usuario Correto, Senha Invalida.", "Atencao", INFORMATION_MESSAGE);
					}
					else if (resposta.equalsIgnoreCase("NAO_USUARIO_ONLINE")) {
						showMessageDialog(dialogoLogar, "Esta Conta Ja Foi Conectada Em Outro Computador.", "Atencao", INFORMATION_MESSAGE);
					}
					else if (resposta.equalsIgnoreCase("OK_AUTORIZADO")) {
						dialogoLogar.getJanelaPai().dispose();
						new JanelaCliente(dialogoLogar, usuario);
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
	}

	private String logarCliente(String usuario, String senha) throws IOException, ClassNotFoundException {

		String mensagem[] = new String[] {usuario, senha};
		String resposta;

		conexaoCliente.conectaServidor();

		conexaoCliente.getEnviaDados().writeObject("LOGAR");
		conexaoCliente.getEnviaDados().flush();

		conexaoCliente.getEnviaDados().writeObject(mensagem);
		conexaoCliente.getEnviaDados().flush();

		// Lendo a mensagem enviada pelo servidor.
		resposta = (String)conexaoCliente.getRecebeDados().readObject();
		conexaoCliente.desconectaServidor();

		return resposta;
	}
}