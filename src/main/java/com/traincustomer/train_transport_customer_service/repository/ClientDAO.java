package com.traincustomer.train_transport_customer_service.repository;

import com.traincustomer.train_transport_customer_service.entity.Client;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClientDAO extends JpaRepository<Client, UUID> {

    Optional<Client> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("select c from Client c where c.email = :email")
    Optional<Client> findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END from Client c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);

}
