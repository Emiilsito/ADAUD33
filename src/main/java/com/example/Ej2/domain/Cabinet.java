package com.example.Ej2.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "cabinets")
@Data
public class Cabinet {

    public enum Status {
        ACTIVE, MAINTENANCE, RETIRED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String slug;

    @Column(name = "build_year", nullable = false)
    private int buildYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "arcade_id", nullable = false)
    private Arcade arcade;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cabinet_tags",
            joinColumns = @JoinColumn(name = "cabinet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    private List<Match> matches;

    @Override
    public String toString() {
        return "Cabinet{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", buildYear=" + buildYear +
                ", status=" + status +
                ", arcade=" + (arcade != null ? arcade.getName() : "null") +
                ", game=" + (game != null ? game.getName() : "null") +
                '}';
    }
}
