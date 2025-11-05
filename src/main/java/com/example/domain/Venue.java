package com.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Ej1.Space;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String name;

    @Column(nullable = false, length = 250, unique = true)
    private String address;

    @Column(nullable = false, length = 120)
    private String city;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Space> spaces;
}
