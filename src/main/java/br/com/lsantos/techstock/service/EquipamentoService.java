package br.com.lsantos.techstock.service;

import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.enums.StatusEquipamento;
import br.com.lsantos.techstock.exception.RegraNegocioException;
import br.com.lsantos.techstock.repository.EquipamentoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository = new EquipamentoRepository();

    public void cadastrar(Equipamento equipamento, Usuario usuarioLogado) {
        validarEquipamento(equipamento);

        Equipamento equipamentoPorPatrimonio = equipamentoRepository.buscarPorPatrimonio(equipamento.getPatrimonio());

        if (equipamentoPorPatrimonio != null) {
            throw new RegraNegocioException("Já existe um equipamento com este patrimônio.");
        }

        Equipamento equipamentoPorSerial = equipamentoRepository.buscarPorSerial(equipamento.getSerial());

        if (equipamentoPorSerial != null) {
            throw new RegraNegocioException("Já existe um equipamento com este serial.");
        }

        equipamento.setUsuarioCadastro(usuarioLogado);
        equipamento.setDataCadastro(LocalDateTime.now());

        equipamentoRepository.salvar(equipamento);
    }

    public void atualizar(Equipamento equipamento, Usuario usuarioLogado) {
        validarEquipamento(equipamento);

        equipamento.setUsuarioUltimaAlteracao(usuarioLogado);
        equipamento.setDataUltimaAlteracao(LocalDateTime.now());

        equipamentoRepository.atualizar(equipamento);
    }

    public void cadastrar(Equipamento equipamento) {
        cadastrar(equipamento, null);
    }

    public void atualizar(Equipamento equipamento) {
        atualizar(equipamento, null);
    }

    public void excluir(Long id) {
        equipamentoRepository.excluir(id);
    }

    public Equipamento buscarPorId(Long id) {
        return equipamentoRepository.buscarPorId(id);
    }

    public List<Equipamento> listarTodos() {
        return equipamentoRepository.listarTodos();
    }

    public Long contarTodos() {
        return equipamentoRepository.contarTodos();
    }

    public void descartar(Long id) {
        Equipamento equipamento = buscarPorId(id);

        if (equipamento == null) {
            throw new RegraNegocioException("Equipamento não encontrado.");
        }

        equipamento.setStatus(StatusEquipamento.DESCARTADO);

        equipamentoRepository.atualizar(equipamento);
    }

    public Long contarPorStatus(StatusEquipamento status) {
        return equipamentoRepository.contarPorStatus(status);
    }

    private void validarEquipamento(Equipamento equipamento) {
        if (equipamento == null) {
            throw new RegraNegocioException("Equipamento não pode ser nulo.");
        }

        if (equipamento.getPatrimonio() == null || equipamento.getPatrimonio().isBlank()) {
            throw new RegraNegocioException("Patrimônio é obrigatório.");
        }

        if (equipamento.getTipo() == null) {
            throw new RegraNegocioException("Tipo do equipamento é obrigatório.");
        }

        if (equipamento.getMarca() == null || equipamento.getMarca().isBlank()) {
            throw new RegraNegocioException("Marca é obrigatória.");
        }

        if (equipamento.getModelo() == null || equipamento.getModelo().isBlank()) {
            throw new RegraNegocioException("Modelo é obrigatório.");
        }

        if (equipamento.getSerial() == null || equipamento.getSerial().isBlank()) {
            throw new RegraNegocioException("Serial é obrigatório.");
        }

        if (equipamento.getStatus() == null) {
            throw new RegraNegocioException("Status é obrigatório.");
        }
    }
}