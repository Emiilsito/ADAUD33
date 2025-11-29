package com.example.Ej1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 190, unique = true, nullable = false)
    private String email;

    @Column(name = "name", length = 150, nullable = false)
    private String fullName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AccessCard accessCard;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Booking> bookings;

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + fullName + "'}";
    }

}
