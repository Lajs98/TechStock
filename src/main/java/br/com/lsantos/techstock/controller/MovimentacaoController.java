package br.com.lsantos.techstock.controller;

import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.entity.Movimentacao;
import br.com.lsantos.techstock.enums.TipoMovimentacao;
import br.com.lsantos.techstock.service.EquipamentoService;
import br.com.lsantos.techstock.service.MovimentacaoService;
import br.com.lsantos.techstock.util.SessaoUsuario;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MovimentacaoController implements Serializable {

    private Long equipamentoId;
    private TipoMovimentacao tipoMovimentacao;
    private String observacao;

    private final MovimentacaoService movimentacaoService = new MovimentacaoService();
    private final EquipamentoService equipamentoService = new EquipamentoService();

    public void registrar() {
        Equipamento equipamento = equipamentoService.buscarPorId(equipamentoId);

        switch (tipoMovimentacao) {
            case ENTRADA:
                movimentacaoService.registrarEntrada(equipamento, SessaoUsuario.getUsuarioLogado(), observacao);
                break;
            case SAIDA:
                movimentacaoService.registrarSaida(equipamento, SessaoUsuario.getUsuarioLogado(), observacao);
                break;
            case DEVOLUCAO:
                movimentacaoService.registrarDevolucao(equipamento, SessaoUsuario.getUsuarioLogado(), observacao);
                break;
            case MANUTENCAO:
                movimentacaoService.registrarManutencao(equipamento, SessaoUsuario.getUsuarioLogado(), observacao);
                break;
            default:
                throw new RuntimeException("Tipo de movimentação inválido.");
        }

        equipamentoId = null;
        tipoMovimentacao = null;
        observacao = null;
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacaoService.listarTodos();
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentoService.listarTodos();
    }

    public TipoMovimentacao[] getTiposMovimentacao() {
        return TipoMovimentacao.values();
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(Long equipamentoId) {
        this.equipamentoId = equipamentoId;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}