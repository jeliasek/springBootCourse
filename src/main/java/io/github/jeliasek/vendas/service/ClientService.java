package io.github.jeliasek.vendas.service;

import io.github.jeliasek.vendas.repository.ClientRepository;
import io.github.jeliasek.vendas.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository repository;

    public ClientService( ClientRepository repository) {
        this.repository = repository;
    }

    public void saveClient(Client client) {
        validateClient(client);
        this.repository.saveClient(client);
    }

    public void validateClient(Client client) {
        // Validate
    }
}
