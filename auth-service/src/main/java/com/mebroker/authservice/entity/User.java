package com.mebroker.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true) 
    private String phone;

    @Column(nullable = false)
    private String password;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @BatchSize(size = 10)
    private Set<UserSystemRole> userRoles;

    @Column(nullable = false)
    private boolean isActive;
}