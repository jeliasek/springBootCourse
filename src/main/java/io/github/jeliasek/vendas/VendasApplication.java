package io.github.jeliasek.vendas;

import io.github.jeliasek.vendas.domain.entity.Cliente;
import io.github.jeliasek.vendas.domain.entity.Produto;
import io.github.jeliasek.vendas.domain.repository.Clientes;
import io.github.jeliasek.vendas.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner commandLineRunner(@Autowired Clientes clientes, @Autowired Produtos produtos) {
		return args -> {
			Cliente c1 = new Cliente(null, "Joao", "10689851944");
			clientes.save(c1);

			Produto p1 = new Produto(null, "Batata", BigDecimal.valueOf(10));
			produtos.save(p1);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
}

