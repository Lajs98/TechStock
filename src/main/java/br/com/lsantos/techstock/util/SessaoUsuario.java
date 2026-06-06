package br.com.lsantos.techstock.util;

import br.com.lsantos.techstock.entity.Usuario;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

public class SessaoUsuario {

    private static final String USUARIO_LOGADO = "usuarioLogado";

    public static void iniciarSessao(Usuario usuario) {
        getSession().setAttribute(USUARIO_LOGADO, usuario);
    }

    public static Usuario getUsuarioLogado() {
        Object usuario = getSession().getAttribute(USUARIO_LOGADO);

        if (usuario instanceof Usuario) {
            return (Usuario) usuario;
        }

        return null;
    }

    public static boolean estaLogado() {
        return getUsuarioLogado() != null;
    }

    public static void encerrarSessao() {
        getSession().invalidate();
    }

    private static HttpSession getSession() {
        return (HttpSession) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSession(true);
    }
}