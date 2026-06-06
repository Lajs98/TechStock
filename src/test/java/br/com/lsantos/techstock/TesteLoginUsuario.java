package br.com.lsantos.techstock;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.service.UsuarioService;
import org.junit.jupiter.api.Test;

public class TesteLoginUsuario {

    @Test
    public void autenticarUsuario() {
        UsuarioService service = new UsuarioService();

        Usuario usuario = service.autenticar(
                "admin@techstock.com",
                "BANKAI"
        );

        System.out.println("Login realizado com sucesso!");
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Perfil: " + usuario.getPerfil());
    }
}