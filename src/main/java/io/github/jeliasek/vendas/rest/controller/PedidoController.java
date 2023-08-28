package io.github.jeliasek.vendas.rest.controller;

import io.github.jeliasek.vendas.domain.entity.ItemPedido;
import io.github.jeliasek.vendas.domain.entity.Pedido;
import io.github.jeliasek.vendas.domain.enums.StatusPedido;
import io.github.jeliasek.vendas.exception.RegraNegocioException;
import io.github.jeliasek.vendas.rest.dto.AtualizacaoStatusPedidoDto;
import io.github.jeliasek.vendas.rest.dto.InformacoesItemPedidoDto;
import io.github.jeliasek.vendas.rest.dto.InformacoesPedidoDto;
import io.github.jeliasek.vendas.rest.dto.PedidoDto;
import io.github.jeliasek.vendas.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer pedidoSave (@RequestBody @Valid PedidoDto pedido) {
        Pedido pedidoCriado = pedidoService.salvar(pedido);
        return pedidoCriado.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDto getById(@PathVariable Integer id) {
        return pedidoService.getAllInfoPedido(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não Encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDto dto) {
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(dto.getNovoStatus()));
    }

    private InformacoesPedidoDto converter(Pedido pedido) {
        return InformacoesPedidoDto.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemPedidoDto> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) return Collections.emptyList();

        return itens.stream().map(
                item -> InformacoesItemPedidoDto
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()).collect(Collectors.toList());
    }

}
