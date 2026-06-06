package br.com.lsantos.techstock.util;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.enums.PerfilUsuario;

public class PermissaoUtil {

    public static boolean isAdmin() {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        return usuario != null &&
                usuario.getPerfil() == PerfilUsuario.ADMIN;
    }

    public static boolean isSupervisorOuAdmin() {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        return usuario != null &&
                (usuario.getPerfil() == PerfilUsuario.ADMIN
                        || usuario.getPerfil() == PerfilUsuario.SUPERVISOR);
    }
}