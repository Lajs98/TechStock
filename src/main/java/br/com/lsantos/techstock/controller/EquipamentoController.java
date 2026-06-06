package br.com.lsantos.techstock.controller;

import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.enums.StatusEquipamento;
import br.com.lsantos.techstock.enums.TipoEquipamento;
import br.com.lsantos.techstock.service.EquipamentoService;
import br.com.lsantos.techstock.service.MovimentacaoService;
import br.com.lsantos.techstock.util.SessaoUsuario;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class EquipamentoController implements Serializable {

    private Equipamento equipamento = new Equipamento();

    private StatusEquipamento statusFiltro;
    private String termoBusca;

    private final EquipamentoService equipamentoService = new EquipamentoService();
    private final MovimentacaoService movimentacaoService = new MovimentacaoService();

    public void salvar() {

        if (equipamento.getId() == null) {
            equipamentoService.cadastrar(equipamento, SessaoUsuario.getUsuarioLogado());
            System.out.println("Equipamento cadastrado com sucesso!");
        } else {
            equipamentoService.atualizar(equipamento, SessaoUsuario.getUsuarioLogado());
            System.out.println("Equipamento atualizado com sucesso!");
        }

        equipamento = new Equipamento();
    }

    public void editar(Equipamento equipamentoSelecionado) {
        this.equipamento = equipamentoSelecionado;
    }

    public void excluir(Long id) {
        equipamentoService.excluir(id);
        System.out.println("Equipamento excluído com sucesso!");
    }

    public void descartar(Long id) {
        Equipamento equipamento = equipamentoService.buscarPorId(id);

        movimentacaoService.registrarDescarte(
                equipamento,
                SessaoUsuario.getUsuarioLogado(),
                "Equipamento descartado pelo sistema."
        );
    }

    public void limparFiltros() {
        statusFiltro = null;
        termoBusca = null;
    }

    public List<Equipamento> getEquipamentos() {
        List<Equipamento> equipamentos = equipamentoService.listarTodos();

        if (statusFiltro != null) {
            equipamentos = equipamentos.stream()
                    .filter(equipamento -> equipamento.getStatus() == statusFiltro)
                    .collect(Collectors.toList());
        }

        if (termoBusca != null && !termoBusca.isBlank()) {
            String termo = termoBusca.toLowerCase();

            equipamentos = equipamentos.stream()
                    .filter(equipamento ->
                            contem(equipamento.getPatrimonio(), termo) ||
                                    contem(equipamento.getMarca(), termo) ||
                                    contem(equipamento.getModelo(), termo) ||
                                    contem(equipamento.getSerial(), termo))
                    .collect(Collectors.toList());
        }

        return equipamentos;
    }

    private boolean contem(String valor, String termo) {
        return valor != null && valor.toLowerCase().contains(termo);
    }

    public TipoEquipamento[] getTiposEquipamento() {
        return TipoEquipamento.values();
    }

    public StatusEquipamento[] getStatusEquipamento() {
        return StatusEquipamento.values();
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public StatusEquipamento getStatusFiltro() {
        return statusFiltro;
    }

    public void setStatusFiltro(StatusEquipamento statusFiltro) {
        this.statusFiltro = statusFiltro;
    }

    public String getTermoBusca() {
        return termoBusca;
    }

    public void setTermoBusca(String termoBusca) {
        this.termoBusca = termoBusca;
    }
}