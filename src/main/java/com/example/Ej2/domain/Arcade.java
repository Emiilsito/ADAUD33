package com.example.Ej2.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "arcades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Arcade.findByNamePattern",
        query = "SELECT a FROM Arcade a WHERE a.name LIKE :name"
)
public class Arcade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @OneToMany(mappedBy = "arcade", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Cabinet> cabinets;

    public Arcade(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Arcade{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
