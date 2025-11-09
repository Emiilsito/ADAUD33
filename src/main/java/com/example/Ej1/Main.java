package com.example.Ej1;

import com.example.Ej1.domain.*;
import com.example.Ej1.service.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Servicios
        VenueService venueService = new VenueService();
        SpaceService spaceService = new SpaceService();
        UserService userService = new UserService();
        BookingService bookingService = new BookingService();
        TagService tagService = new TagService();

        // ---------- 1. Alta de Venue ----------
        Venue venue = new Venue();
        venue.setName("Coworking Central");
        venue.setAddress("Calle Mayor 123");
        venue.setCity("Madrid");
        venue.setCreatedAt(LocalDateTime.now());
        venueService.create(venue);
        System.out.println("Venue creado con ID: " + venue.getId());

        // ---------- 2. Alta de Space ----------
        Space space = new Space();
        space.setName("Sala Reuniones A");
        space.setCode("SR-A");  // único
        space.setCapacity(10);
        space.setActive(true);
        space.setHourlyPrice(new BigDecimal("15.00"));
        space.setType(Space.SpaceType.MEETING_ROOM);

        spaceService.createSpace(venue.getId(), space);
        System.out.println("Space creado con ID: " + space.getId());

        // ---------- 3. Alta de User ----------
        User user = new User();
        user.setFullName("Emilio García");
        user.setEmail("emilio@example.com"); // único
        userService.create(user);
        System.out.println("Usuario creado con ID: " + user.getId());

        // ---------- 4. Asignar AccessCard ----------
        String cardUid = "AC-1001";  // único
        userService.assignAccessCard(user.getId(), cardUid);
        System.out.println("AccessCard asignada: " + cardUid + " al usuario " + user.getId());

        // ---------- 5. Crear Booking ----------
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        BigDecimal totalPrice = space.getHourlyPrice().multiply(new BigDecimal("2")); // 2 horas

        Booking booking = new Booking();
        Long bookingId = bookingService.create(
                user.getId(),
                space.getId(),
                start,
                end,
                totalPrice,
                Booking.BookingStatus.CONFIRMED
        );
        booking.setId(bookingId);

        // ---------- 6. Alta de Tag y asociación a Space ----------
        Tag tag = new Tag();
        tag.setName("Reunión");

        // Guardamos el tag usando el service
        Long tagId = tagService.create(tag);

        // Asociamos el tag al Space
        tagService.addTagToSpace(space.getId(), tagId);

        System.out.println("Tag creado y asociado a Space: " + tag.getName());



        // ---------- 7. Listados ----------
        System.out.println("\n--- Listado de Spaces ---");
        spaceService.findAll().forEach(s -> System.out.println(s.getName() + " - " + s.getCode()));

        System.out.println("\n--- Listado de Users ---");
        userService.findAll().forEach(u -> System.out.println(u.getFullName() + " - " + u.getEmail()));

        System.out.println("\n--- Listado de Bookings ---");
        bookingService.findAll().forEach(b -> System.out.println("Booking ID: " + b.getId() +
                ", User: " + b.getUser().getFullName() +
                ", Space: " + b.getSpace().getName() +
                ", Total: " + b.getTotalPrice()));

        System.out.println("\n--- Listado de Tags en Space ---");
        spaceService.findAll().forEach(s -> {
            System.out.println(s.getName());
            s.getTags().forEach(t -> System.out.println(" - " + t.getName()));
        });
        // ---------- 8. Borrados ----------
        System.out.println("\n--- Borrando Booking ---");
        bookingService.deleteById(booking.getId());

        System.out.println("Booking borrado");

        System.out.println("\n--- Borrando AccessCard del User ---");
        userService.removeAccessCard(user.getId());

        System.out.println("\n--- Borrando User ---");
        userService.deleteById(user.getId());

        System.out.println("\n--- Borrando Tags ---");

        // Primero desasociar de los spaces
        spaceService.findAll().forEach(s -> {
            if (s.getTags() != null) {
                List<Tag> tagsCopy = new ArrayList<>(s.getTags());
                for (Tag t : tagsCopy) {
                    tagService.removeTagFromSpace(s.getId(), t.getId());
                }
            }
        });

        // Ahora sí se pueden borrar los tags
        tagService.findAll().forEach(t -> tagService.deleteById(t.getId()));

        System.out.println("\n--- Borrando Space ---");
        spaceService.deleteById(space.getId());

        System.out.println("\n--- Borrando Venue ---");
        venueService.deleteById(venue.getId());

        System.out.println("\n--- Main finalizado ---");
    }
}
