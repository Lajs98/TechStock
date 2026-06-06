package br.com.lsantos.techstock.util;

import br.com.lsantos.techstock.entity.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogado;

    public static void iniciarSessao(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static boolean estaLogado() {
        return usuarioLogado != null;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }
}