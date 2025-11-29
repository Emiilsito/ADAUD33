package com.example.Ej1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venues")
@Data
@NamedQuery(
        name = "Venue.FindbyCity",
        query = "SELECT v FROM Venue v WHERE v.city = :city ORDER BY v.name ASC"
)
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

    @OneToMany(
            mappedBy = "venue",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Space> spaces;

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
