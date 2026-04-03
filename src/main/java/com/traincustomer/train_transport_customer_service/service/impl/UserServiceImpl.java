package com.traincustomer.train_transport_customer_service.service.impl;

import com.traincustomer.train_transport_customer_service.dto.UserRegistrationDTO;
import com.traincustomer.train_transport_customer_service.dto.UserResponseDTO;
import com.traincustomer.train_transport_customer_service.exception.EmailAlreadyExistsException;
import com.traincustomer.train_transport_customer_service.model.Role;
import com.traincustomer.train_transport_customer_service.model.User;
import com.traincustomer.train_transport_customer_service.repository.RoleRepository;
import com.traincustomer.train_transport_customer_service.repository.UserRepository;
import com.traincustomer.train_transport_customer_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO register(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + dto.getEmail());
        }

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException(
                        "ROLE_USER not found in database. Ensure Flyway migrations have run."));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEnabled(true); // mirrors DB default (enabled BOOLEAN NOT NULL DEFAULT TRUE) — kept explicit for clarity
        user.setRoles(Set.of(roleUser));

        User saved = userRepository.save(user);

        return new UserResponseDTO(saved.getId(), saved.getEmail(), saved.getFirstName(), saved.getLastName());
    }
}
