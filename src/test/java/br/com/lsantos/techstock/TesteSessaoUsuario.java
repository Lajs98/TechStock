package br.com.lsantos.techstock;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.service.UsuarioService;
import br.com.lsantos.techstock.util.SessaoUsuario;
import org.junit.jupiter.api.Test;

public class TesteSessaoUsuario {

    @Test
    public void iniciarEEncerrarSessao() {
        UsuarioService usuarioService = new UsuarioService();

        Usuario usuario = usuarioService.autenticar(
                "admin@techstock.com",
                "BANKAI"
        );

        SessaoUsuario.iniciarSessao(usuario);

        System.out.println("Usuário logado: " + SessaoUsuario.getUsuarioLogado().getNome());
        System.out.println("Perfil: " + SessaoUsuario.getUsuarioLogado().getPerfil());
        System.out.println("Está logado? " + SessaoUsuario.estaLogado());

        SessaoUsuario.encerrarSessao();

        System.out.println("Está logado após sair? " + SessaoUsuario.estaLogado());
    }
}