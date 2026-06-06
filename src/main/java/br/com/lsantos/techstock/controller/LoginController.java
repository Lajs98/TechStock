package br.com.lsantos.techstock.controller;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.service.UsuarioService;
import br.com.lsantos.techstock.util.SessaoUsuario;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class LoginController implements Serializable {

    private String email;
    private String senha;

    private final UsuarioService usuarioService = new UsuarioService();

    public String login() {

        Usuario usuario = usuarioService.autenticar(
                email,
                senha
        );

        SessaoUsuario.iniciarSessao(usuario);

        System.out.println("Usuário logado: " + usuario.getNome());

        return "index?faces-redirect=true";
    }

    public String logout() {
        SessaoUsuario.encerrarSessao();
        return "login?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        return SessaoUsuario.getUsuarioLogado();
    }
}