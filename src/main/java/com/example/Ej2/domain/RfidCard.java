package com.example.Ej2.domain;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rfid_cards")
@Data
public class RfidCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String uid;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private boolean active;

    @OneToOne
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;
}

