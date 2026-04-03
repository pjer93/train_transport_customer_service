package com.traincustomer.train_transport_customer_service.repository;

import com.traincustomer.train_transport_customer_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
