package com.traincustomer.train_transport_customer_service.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    // Secret key for generating tokens
    private final String SECRET_KEY = "testSecretKey";
    // 24 hours in milliseconds
    private final long EXPIRATION_TIME = 86400000L;

    // Generate a secret key
}
