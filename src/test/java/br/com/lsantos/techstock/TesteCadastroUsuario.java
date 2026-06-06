package br.com.lsantos.techstock;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.enums.PerfilUsuario;
import br.com.lsantos.techstock.service.UsuarioService;
import org.junit.jupiter.api.Test;

public class TesteCadastroUsuario {

    @Test
    public void cadastrarUsuario() {

        Usuario usuario = new Usuario(
                "Levi Santos",
                "admin@techstock.com",
                "BANKAI",
                PerfilUsuario.ADMIN
        );

        UsuarioService service = new UsuarioService();

        service.cadastrar(usuario);

        System.out.println("Usuário cadastrado com sucesso!");
    }
}