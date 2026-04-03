package com.traincustomer.train_transport_customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
