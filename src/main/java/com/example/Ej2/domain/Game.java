package com.example.Ej2.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "games")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    @Column(name = "genre", length = 50)
    private String genre;

    @OneToMany(mappedBy = "game")
    private List<Cabinet> cabinets;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                '}';
    }

}