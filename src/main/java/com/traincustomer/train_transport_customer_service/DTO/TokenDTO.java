package com.traincustomer.train_transport_customer_service.DTO;

import lombok.Data;

public class TokenDTO {

    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
