package com.traincustomer.train_transport_customer_service.service.impl;

import com.traincustomer.train_transport_customer_service.DTO.ClientDTO;
import com.traincustomer.train_transport_customer_service.DTO.TokenDTO;
import com.traincustomer.train_transport_customer_service.entity.Client;
import com.traincustomer.train_transport_customer_service.exception.ClientAlreadyExistsException;
import com.traincustomer.train_transport_customer_service.exception.ClientNotFoundException;
import com.traincustomer.train_transport_customer_service.exception.InvalidCredentialsException;
import com.traincustomer.train_transport_customer_service.repository.ClientDAO;
import com.traincustomer.train_transport_customer_service.request.LoginRequest;
import com.traincustomer.train_transport_customer_service.security.JwtTokenProvider;
import com.traincustomer.train_transport_customer_service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultClientService implements ClientService {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ClientDTO registerClient(ClientDTO clientDTO) {
        if (clientDAO.existsByEmail(clientDTO.getEmail())){
            throw new ClientAlreadyExistsException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(clientDTO.getPassword());
        clientDTO.setPassword(hashedPassword);

        Client clientEntity = ClientMapper.toEntity(clientDTO);
        clientDAO.save(clientEntity);

        return ClientMapper.toDTO(clientEntity);
    }

    @Override
    public TokenDTO loginClient(LoginRequest loginRequest) {
        Client client = clientDAO.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ClientNotFoundException("Invalid credentials."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        String token = jwtTokenProvider.generateToken(client.getEmail());

        return new TokenDTO(token);
    }

    @Override
    public ClientDTO getClientById(UUID id) {
        User client = clientDAO.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found"));
        return ClientMapper.toDTO(client);
    }

    @Override
    public ClientDTO updateClient(UUID id, ClientDTO clientDTO) {
        Client client = clientDAO.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not foun"));
        client.setFirstName(clientDTO.getFirstName());
        client.setFirstName(clientDTO.getLastName());
        client.setFirstName(clientDTO.getPhoneNumber());
        client.setFirstName(clientDTO.getAddress());

        clientDAO.save(client);
        return ClientMapper.toDTO(client);
    }

    @Override
    public void deleteClient(UUID id) {
        if (clientDAO.existsById(id)) {
            throw new ClientNotFoundException("Client not found.");
        }
        clientDAO.deleteById(id);
    }
}
