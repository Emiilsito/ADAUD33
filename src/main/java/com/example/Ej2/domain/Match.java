package com.example.Ej2.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
public class Match {

    public enum Result {
        WIN, LOSE, DRAW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "duration_sec", nullable = false)
    private int durationSec;

    @Column(nullable = false)
    private int score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Result result;

    @Column(name = "credits_used", nullable = false)
    private int creditsUsed;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cabinet_id", nullable = false)
    private Cabinet cabinet;
}
