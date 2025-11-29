package com.example.Ej1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NamedQuery(
        name= "Booking.confirmedVenueRange",
        query = "SELECT b FROM Booking b " +
                "JOIN b.space s " +
                "JOIN s.venue v " +
                "WHERE v.name = :venueName " +
                "AND b.status = com.example.Ej1.domain.Booking.BookingStatus.CONFIRMED " +
                "AND b.startTime >= :startDate " +
                "AND b.endTime <= :endDate"
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

}
