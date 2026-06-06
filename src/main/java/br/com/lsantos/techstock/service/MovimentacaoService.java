package br.com.lsantos.techstock.service;

import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.entity.Movimentacao;
import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.enums.StatusEquipamento;
import br.com.lsantos.techstock.enums.TipoMovimentacao;
import br.com.lsantos.techstock.exception.RegraNegocioException;
import br.com.lsantos.techstock.repository.EquipamentoRepository;
import br.com.lsantos.techstock.repository.MovimentacaoRepository;

import java.util.List;

public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository = new MovimentacaoRepository();
    private final EquipamentoRepository equipamentoRepository = new EquipamentoRepository();

    public void registrarEntrada(Equipamento equipamento, Usuario usuario, String observacao) {
        validarDadosMovimentacao(equipamento, usuario);

        equipamento.setStatus(StatusEquipamento.DISPONIVEL);
        equipamentoRepository.atualizar(equipamento);

        Movimentacao movimentacao = new Movimentacao(
                equipamento,
                usuario,
                TipoMovimentacao.ENTRADA,
                observacao
        );

        movimentacaoRepository.salvar(movimentacao);
    }

    public void registrarSaida(Equipamento equipamento, Usuario usuario, String observacao) {
        validarDadosMovimentacao(equipamento, usuario);

        if (equipamento.getStatus() != StatusEquipamento.DISPONIVEL) {
            throw new RegraNegocioException("Só é possível dar saída em equipamentos disponíveis.");
        }

        equipamento.setStatus(StatusEquipamento.EM_USO);
        equipamentoRepository.atualizar(equipamento);

        Movimentacao movimentacao = new Movimentacao(
                equipamento,
                usuario,
                TipoMovimentacao.SAIDA,
                observacao
        );

        movimentacaoRepository.salvar(movimentacao);
    }

    public void registrarDevolucao(Equipamento equipamento, Usuario usuario, String observacao) {
        validarDadosMovimentacao(equipamento, usuario);

        if (equipamento.getStatus() != StatusEquipamento.EM_USO) {
            throw new RegraNegocioException("Só é possível devolver equipamentos que estão em uso.");
        }

        equipamento.setStatus(StatusEquipamento.DISPONIVEL);
        equipamentoRepository.atualizar(equipamento);

        Movimentacao movimentacao = new Movimentacao(
                equipamento,
                usuario,
                TipoMovimentacao.DEVOLUCAO,
                observacao
        );

        movimentacaoRepository.salvar(movimentacao);
    }

    public void registrarManutencao(Equipamento equipamento, Usuario usuario, String observacao) {
        validarDadosMovimentacao(equipamento, usuario);

        equipamento.setStatus(StatusEquipamento.EM_MANUTENCAO);
        equipamentoRepository.atualizar(equipamento);

        Movimentacao movimentacao = new Movimentacao(
                equipamento,
                usuario,
                TipoMovimentacao.MANUTENCAO,
                observacao
        );

        movimentacaoRepository.salvar(movimentacao);
    }

    public void registrarDescarte(Equipamento equipamento, Usuario usuario, String observacao) {
        validarDadosMovimentacao(equipamento, usuario);

        equipamento.setStatus(StatusEquipamento.DESCARTADO);
        equipamentoRepository.atualizar(equipamento);

        Movimentacao movimentacao = new Movimentacao(
                equipamento,
                usuario,
                TipoMovimentacao.DESCARTE,
                observacao
        );

        movimentacaoRepository.salvar(movimentacao);
    }

    public List<Movimentacao> listarTodos() {
        return movimentacaoRepository.listarTodos();
    }

    public List<Movimentacao> listarPorEquipamento(Equipamento equipamento) {
        return movimentacaoRepository.listarPorEquipamento(equipamento);
    }

    public Long contarTodos() {
        return movimentacaoRepository.contarTodos();
    }

    private void validarDadosMovimentacao(Equipamento equipamento, Usuario usuario) {
        if (equipamento == null) {
            throw new RegraNegocioException("Equipamento é obrigatório.");
        }

        if (usuario == null) {
            throw new RegraNegocioException("Usuário é obrigatório.");
        }

        if (equipamento.getId() == null) {
            throw new RegraNegocioException("Equipamento precisa estar cadastrado antes da movimentação.");
        }

        if (usuario.getId() == null) {
            throw new RegraNegocioException("Usuário precisa estar cadastrado antes da movimentação.");
        }
    }
}