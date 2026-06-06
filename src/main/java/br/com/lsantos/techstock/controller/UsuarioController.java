package br.com.lsantos.techstock.controller;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.enums.PerfilUsuario;
import br.com.lsantos.techstock.service.UsuarioService;
import br.com.lsantos.techstock.util.FacesUtil;
import br.com.lsantos.techstock.util.PermissaoUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

    private Usuario usuario = new Usuario();

    private final UsuarioService usuarioService = new UsuarioService();

    @PostConstruct
    public void verificarPermissao() {
        if (!PermissaoUtil.isAdmin()) {
            try {
                jakarta.faces.context.FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .redirect("index.xhtml");
            } catch (Exception e) {
                throw new RuntimeException("Erro ao redirecionar usuário sem permissão.", e);
            }
        }
    }

    public void salvar() {
        if (usuario.getId() == null) {
            usuarioService.cadastrar(usuario);
            FacesUtil.sucesso("Usuário cadastrado com sucesso!");
        } else {
            usuarioService.atualizar(usuario);
            FacesUtil.sucesso("Usuário atualizado com sucesso!");
        }

        usuario = new Usuario();
    }

    public void editar(Usuario usuarioSelecionado) {
        this.usuario = usuarioSelecionado;
    }

    public void alterarStatus(Long id) {
        usuarioService.alterarStatus(id);
    }

    public List<Usuario> getUsuarios() {
        return usuarioService.listarTodos();
    }

    public PerfilUsuario[] getPerfisUsuario() {
        return PerfilUsuario.values();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}