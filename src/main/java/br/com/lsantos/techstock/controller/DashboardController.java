package br.com.lsantos.techstock.controller;

import br.com.lsantos.techstock.enums.StatusEquipamento;
import br.com.lsantos.techstock.service.EquipamentoService;
import br.com.lsantos.techstock.service.MovimentacaoService;
import br.com.lsantos.techstock.service.UsuarioService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class DashboardController implements Serializable {

    private final UsuarioService usuarioService = new UsuarioService();
    private final EquipamentoService equipamentoService = new EquipamentoService();
    private final MovimentacaoService movimentacaoService = new MovimentacaoService();

    public Long getTotalUsuarios() {
        return usuarioService.contarTodos();
    }

    public Long getTotalEquipamentos() {
        return equipamentoService.contarTodos();
    }

    public Long getTotalMovimentacoes() {
        return movimentacaoService.contarTodos();
    }

    public Long getTotalDisponiveis() {
        return equipamentoService.contarPorStatus(StatusEquipamento.DISPONIVEL);
    }

    public Long getTotalEmUso() {
        return equipamentoService.contarPorStatus(StatusEquipamento.EM_USO);
    }

    public Long getTotalEmManutencao() {
        return equipamentoService.contarPorStatus(StatusEquipamento.EM_MANUTENCAO);
    }

    public Long getTotalDescartados() {
        return equipamentoService.contarPorStatus(StatusEquipamento.DESCARTADO);
    }
}