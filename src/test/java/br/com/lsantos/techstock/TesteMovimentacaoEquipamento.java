package br.com.lsantos.techstock;

import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.entity.Usuario;
import br.com.lsantos.techstock.service.EquipamentoService;
import br.com.lsantos.techstock.service.MovimentacaoService;
import br.com.lsantos.techstock.service.UsuarioService;
import org.junit.jupiter.api.Test;

public class TesteMovimentacaoEquipamento {

    @Test
    public void registrarSaidaEquipamento() {

        UsuarioService usuarioService = new UsuarioService();
        EquipamentoService equipamentoService = new EquipamentoService();
        MovimentacaoService movimentacaoService = new MovimentacaoService();

        Usuario usuario = usuarioService.buscarPorEmail("admin@techstock.com");
        Equipamento equipamento = equipamentoService.buscarPorId(1L);

        movimentacaoService.registrarSaida(
                equipamento,
                usuario,
                "Notebook entregue para colaborador do setor de TI."
        );

        System.out.println("Saída registrada com sucesso!");
    }
}