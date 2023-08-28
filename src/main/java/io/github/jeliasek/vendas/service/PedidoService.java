package io.github.jeliasek.vendas.service;

import io.github.jeliasek.vendas.domain.entity.Pedido;
import io.github.jeliasek.vendas.domain.enums.StatusPedido;
import io.github.jeliasek.vendas.rest.dto.PedidoDto;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDto dto);

    Optional<Pedido> getAllInfoPedido(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
