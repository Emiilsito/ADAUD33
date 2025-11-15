package com.example.Ej1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_card")
@Data
public class AccessCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, length = 64, unique = true)
    private String cardUid;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Override
    public String toString() {
        return "AccessCard{id=" + id + "}";
    }

}

