package com.traincustomer.train_transport_customer_service.entity;

import com.traincustomer.train_transport_customer_service.enumeration.AccountStatus;
import com.traincustomer.train_transport_customer_service.enumeration.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.User;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "clients")
@Data
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String userName;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email( message = "Email is not valid",
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$")
    @Column(unique = true, nullable = false)
    private String email;

    //Hashed and salted pwd(never store plain text)
    @NotNull(message = "password cant be null")
    private String passwordHash;

    @Column(unique = true)
    private String phoneNumber;

    //Defines customer role: customer, admin, support
    private UserRole role;

    //Defines Account Status: active, inactive, suspended, pending_verification
    private AccountStatus accountStatus;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime lastLogin;

    private boolean emailVerified;
    private boolean phoneNumberVerified;
    private boolean twoStepAuthenticationEnabled;
    private String twoStepAuthenticationSecret;

    @Column(columnDefinition = "json")
    private String preferences;

    private String passwordResetToken;
    private String passwordResetTokenExpiry;
    private String refreshToken;

    @Column(columnDefinition = "json")
    private String sessionTokens;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;
    private int loginAttempts;

    @Temporal(TemporalType.TIMESTAMP)
    private Date accountLockedAt;

}
