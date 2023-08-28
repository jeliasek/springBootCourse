package io.github.jeliasek.vendas.service.impl;

import io.github.jeliasek.vendas.domain.entity.Cliente;
import io.github.jeliasek.vendas.domain.entity.ItemPedido;
import io.github.jeliasek.vendas.domain.entity.Pedido;
import io.github.jeliasek.vendas.domain.entity.Produto;
import io.github.jeliasek.vendas.domain.enums.StatusPedido;
import io.github.jeliasek.vendas.domain.repository.Clientes;
import io.github.jeliasek.vendas.domain.repository.ItensPedido;
import io.github.jeliasek.vendas.domain.repository.Pedidos;
import io.github.jeliasek.vendas.domain.repository.Produtos;
import io.github.jeliasek.vendas.exception.PedidoNaoEncontradoException;
import io.github.jeliasek.vendas.exception.RegraNegocioException;
import io.github.jeliasek.vendas.rest.dto.ItemPedidoDto;
import io.github.jeliasek.vendas.rest.dto.PedidoDto;
import io.github.jeliasek.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDto dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itemPedidos = converterItens(pedido, dto.getItems());

        repository.save(pedido);
        itensPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);

        return pedido;
    }

    @Override
    public Optional<Pedido> getAllInfoPedido(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDto> itens) {
        if (itens.isEmpty()) throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");

        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.
                    findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }


}
