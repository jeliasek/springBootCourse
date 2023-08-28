package io.github.jeliasek.vendas.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoStatusPedidoDto {
    private String novoStatus;
}
