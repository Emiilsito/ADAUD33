package com.example.Ej2.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tags")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Cabinet> cabinets;
}
