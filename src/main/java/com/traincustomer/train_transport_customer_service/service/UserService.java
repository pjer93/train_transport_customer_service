package com.traincustomer.train_transport_customer_service.service;

import com.traincustomer.train_transport_customer_service.dto.UserRegistrationDTO;
import com.traincustomer.train_transport_customer_service.dto.UserResponseDTO;

/**
 * Service contract for user management operations.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param dto the registration request containing email, password, and personal details
     * @return a {@link UserResponseDTO} with the persisted user's data
     * @throws com.traincustomer.train_transport_customer_service.exception.EmailAlreadyExistsException if the email is already registered
     * @throws IllegalStateException if the ROLE_USER seed data is missing from the database
     */
    UserResponseDTO register(UserRegistrationDTO dto);
}
