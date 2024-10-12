package com.traincustomer.train_transport_customer_service.controller;

import com.traincustomer.train_transport_customer_service.DTO.ClientDTO;
import com.traincustomer.train_transport_customer_service.DTO.TokenDTO;
import com.traincustomer.train_transport_customer_service.request.LoginRequest;
import com.traincustomer.train_transport_customer_service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/reister")
    public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = getClientService().registerClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginClient(@RequestBody LoginRequest loginRequest) {
        TokenDTO token = getClientService().loginClient(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable UUID id) {
        ClientDTO client = getClientService().getClientById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable UUID id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = getClientService().updateClient(id, clientDTO);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable UUID id) {
        getClientService().deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected ClientService getClientService() {
        return clientService;
    }

}
