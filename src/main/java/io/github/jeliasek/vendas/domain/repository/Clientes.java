package io.github.jeliasek.vendas.domain.repository;

import io.github.jeliasek.vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {
    @Query(value = " SELECT * FROM Cliente c WHERE c.nome LIKE '%:nome%' ", nativeQuery = true)
    List<Cliente> findByNameLike(@Param("nome") String nome);

    @Query(value = " DELETE FROM Cliente c WHERE c.nome = :nome ")
    @Modifying
    void deleteByNome(String nome);
    Boolean existsByNome(String nome);

    @Query(" SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.id = :id  ")
    Cliente findClientFetchPedidos(@Param("id") Integer id );

}
