package com.stock.app.entities.concretes;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "tckn")
    private String tckn;
    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class)
    private Set<UserRole> roles;

    @Column(name = "is_enabled")
    private boolean isEnabled;

}