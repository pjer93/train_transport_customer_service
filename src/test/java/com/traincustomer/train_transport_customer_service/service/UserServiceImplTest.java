package com.traincustomer.train_transport_customer_service.service;

import com.traincustomer.train_transport_customer_service.dto.UserRegistrationDTO;
import com.traincustomer.train_transport_customer_service.dto.UserResponseDTO;
import com.traincustomer.train_transport_customer_service.exception.EmailAlreadyExistsException;
import com.traincustomer.train_transport_customer_service.model.Role;
import com.traincustomer.train_transport_customer_service.model.User;
import com.traincustomer.train_transport_customer_service.repository.RoleRepository;
import com.traincustomer.train_transport_customer_service.repository.UserRepository;
import com.traincustomer.train_transport_customer_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegistrationDTO dto;

    @BeforeEach
    void setUp() {
        dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("Password1");
        dto.setFirstName("John");
        dto.setLastName("Doe");
    }

    @Test
    void register_happyPath_returnsUserResponseDTO() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(dto.getEmail());
        savedUser.setFirstName(dto.getFirstName());
        savedUser.setLastName(dto.getLastName());

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO result = userService.register(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    void register_duplicateEmail_throwsEmailAlreadyExistsException() {
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(dto))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void register_missingRoleUser_throwsIllegalStateException() {
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        // passwordEncoder.encode() is intentionally not stubbed: the exception is thrown
        // during role lookup (before encode() is called), so no stub is needed here.
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.register(dto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ROLE_USER");
    }
}
