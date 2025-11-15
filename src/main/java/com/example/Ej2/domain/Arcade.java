package com.example.Ej2.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "arcades")
@Data
public class Arcade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @OneToMany(mappedBy = "arcade")
    private List<Cabinet> cabinets;
}
