package com.traincustomer.train_transport_customer_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traincustomer.train_transport_customer_service.dto.UserRegistrationDTO;
import com.traincustomer.train_transport_customer_service.dto.UserResponseDTO;
import com.traincustomer.train_transport_customer_service.exception.EmailAlreadyExistsException;
import com.traincustomer.train_transport_customer_service.security.SecurityConfig;
import com.traincustomer.train_transport_customer_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void register_validBody_returns201() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("Password1");
        dto.setFirstName("John");
        dto.setLastName("Doe");

        UserResponseDTO response = new UserResponseDTO(1L, "test@example.com", "John", "Doe");
        when(userService.register(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void register_invalidBody_returns400() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("not-an-email");
        dto.setPassword("short");
        dto.setFirstName("");
        dto.setLastName("Doe");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.firstName").exists());
    }

    @Test
    void register_duplicateEmail_returns409() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("existing@example.com");
        dto.setPassword("Password1");
        dto.setFirstName("Jane");
        dto.setLastName("Doe");

        when(userService.register(any())).thenThrow(new EmailAlreadyExistsException("Email already exists"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Email already exists"));
    }
}
