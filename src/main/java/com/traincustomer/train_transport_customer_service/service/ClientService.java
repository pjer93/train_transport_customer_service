package com.traincustomer.train_transport_customer_service.service;

import com.traincustomer.train_transport_customer_service.DTO.ClientDTO;
import com.traincustomer.train_transport_customer_service.DTO.TokenDTO;
import com.traincustomer.train_transport_customer_service.request.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ClientService {
    ClientDTO registerClient(ClientDTO clientDTO);
    TokenDTO loginClient(LoginRequest loginRequest);
    ClientDTO getClientById(UUID id);
    ClientDTO updateClient(UUID id, ClientDTO clientDTO);
    void deleteClient(UUID id);
}
