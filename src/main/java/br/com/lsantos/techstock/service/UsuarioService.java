package br.com.lsantos.techstock.service;

import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.exception.RegraNegocioException;
import br.com.lsantos.techstock.repository.UsuarioRepository;
import br.com.lsantos.techstock.util.PasswordUtil;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void cadastrar(Usuario usuario) {
        validarUsuario(usuario);

        Usuario usuarioExistente = usuarioRepository.buscarPorEmail(usuario.getEmail());

        if (usuarioExistente != null) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
        }

        usuario.setSenha(PasswordUtil.criptografar(usuario.getSenha()));
        usuarioRepository.salvar(usuario);
    }

    public void atualizar(Usuario usuario) {
        validarUsuarioEdicao(usuario);

        Usuario usuarioExistente = usuarioRepository.buscarPorEmail(usuario.getEmail());

        if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
            throw new RegraNegocioException("Já existe outro usuário cadastrado com este e-mail.");
        }

        usuarioRepository.atualizar(usuario);
    }

    public void alterarStatus(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);

        if (usuario == null) {
            throw new RegraNegocioException("Usuário não encontrado.");
        }

        usuario.setAtivo(!usuario.getAtivo());
        usuarioRepository.atualizar(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || email.isBlank()) {
            throw new RegraNegocioException("E-mail é obrigatório.");
        }

        if (senha == null || senha.isBlank()) {
            throw new RegraNegocioException("Senha é obrigatória.");
        }

        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new RegraNegocioException("Usuário não encontrado.");
        }

        if (!usuario.getAtivo()) {
            throw new RegraNegocioException("Usuário inativo.");
        }

        if (!PasswordUtil.verificar(senha, usuario.getSenha())) {
            throw new RegraNegocioException("Senha inválida.");
        }

        return usuario;
    }

    private void validarUsuario(Usuario usuario) {
        validarCamposBasicos(usuario);

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new RegraNegocioException("Senha do usuário é obrigatória.");
        }
    }

    private void validarUsuarioEdicao(Usuario usuario) {
        validarCamposBasicos(usuario);

        if (usuario.getId() == null) {
            throw new RegraNegocioException("Usuário precisa estar cadastrado para ser editado.");
        }
    }

    private void validarCamposBasicos(Usuario usuario) {
        if (usuario == null) {
            throw new RegraNegocioException("Usuário não pode ser nulo.");
        }

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new RegraNegocioException("Nome do usuário é obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RegraNegocioException("E-mail do usuário é obrigatório.");
        }

        if (usuario.getPerfil() == null) {
            throw new RegraNegocioException("Perfil do usuário é obrigatório.");
        }
    }

    public Long contarTodos() {
        return usuarioRepository.contarTodos();
    }
}