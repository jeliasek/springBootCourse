package io.github.jeliasek.vendas.domain.repository;

import io.github.jeliasek.vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
