package br.com.lsantos.techstock;

import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.enums.TipoEquipamento;
import br.com.lsantos.techstock.service.EquipamentoService;
import org.junit.jupiter.api.Test;

public class TesteCadastroEquipamento {

    @Test
    public void cadastrarEquipamento() {

        Equipamento equipamento = new Equipamento(
                "TS-0001",
                TipoEquipamento.NOTEBOOK,
                "Dell",
                "Latitude 5440",
                "DELL-5440-001"
        );

        EquipamentoService service = new EquipamentoService();

        service.cadastrar(equipamento);

        System.out.println("Equipamento cadastrado com sucesso!");
    }
}