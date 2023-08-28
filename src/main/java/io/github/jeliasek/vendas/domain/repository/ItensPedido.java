package io.github.jeliasek.vendas.domain.repository;

import io.github.jeliasek.vendas.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
