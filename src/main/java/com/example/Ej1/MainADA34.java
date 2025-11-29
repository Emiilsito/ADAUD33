package com.example.Ej1;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.domain.AccessCard;
import com.example.Ej1.domain.Space;
import com.example.Ej1.domain.User;
import com.example.Ej1.domain.Venue;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MainADA34 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            // ================= INSERT VENUE + SPACES =================
            System.out.println("\n========= INSERTANDO VENUE + SPACES =========");
            Transaction tx1 = session.beginTransaction();

            Venue v = new Venue();
            v.setName("Cowork Fénix");
            v.setAddress("Av. Sol 123");
            v.setCity("Madrid");
            v.setCreatedAt(LocalDateTime.now());

            Space s1 = new Space();
            s1.setActive(true);
            s1.setCapacity(12);
            s1.setCode("SALA-A1");
            s1.setHourlyPrice(BigDecimal.valueOf(12.50));
            s1.setName("Sala Azul");
            s1.setType(Space.SpaceType.MEETING_ROOM);
            s1.setVenue(v);

            Space s2 = new Space();
            s2.setActive(true);
            s2.setCapacity(6);
            s2.setCode("SALA-A2");
            s2.setHourlyPrice(BigDecimal.valueOf(9.30));
            s2.setName("Sala Verde");
            s2.setType(Space.SpaceType.OFFICE);
            s2.setVenue(v);

            v.setSpaces(List.of(s1, s2));

            session.persist(v); // Cascade ALL → inserta Spaces automáticamente
            tx1.commit();

            // Mostrar relaciones LAZY cargadas
            System.out.println("Spaces en Venue: " + v.getSpaces().size()); // dispara SELECT si no está en contexto

            // ================= INSERT USER + ACCESSCARD =================
            System.out.println("\n========= INSERTANDO USER + ACCESSCARD =========");
            Transaction tx2 = session.beginTransaction();

            User u = new User();
            u.setEmail("emilio@test.com");
            u.setFullName("Emilio García");
            u.setCreatedAt(LocalDateTime.now());

            AccessCard card = new AccessCard();
            card.setActive(true);
            card.setIssuedAt(LocalDateTime.now());
            card.setCardUid("CARD-0001");
            card.setUser(u);

            u.setAccessCard(card);

            session.persist(u); // Cascade ALL → inserta AccessCard
            tx2.commit();

            // Mostrar relaciones LAZY cargadas
            System.out.println("AccessCard del User: " + u.getAccessCard());

            // ================= BORRAR VENUE =================
            System.out.println("\n========= BORRANDO VENUE (DEBERÍA BORRAR SPACES) =========");
            Transaction tx3 = session.beginTransaction();
            Venue foundVenue = session.find(Venue.class, v.getId());
            if (foundVenue != null) {
                System.out.println("Spaces antes de borrar Venue: " + foundVenue.getSpaces().size());
                session.remove(foundVenue); // orphanRemoval = true → borra Spaces automáticamente
            }
            tx3.commit();

            // ================= BORRAR USER =================
            System.out.println("\n========= BORRANDO USER (DEBERÍA BORRAR ACCESSCARD) =========");
            Transaction tx4 = session.beginTransaction();
            User foundUser = session.find(User.class, u.getId());
            if (foundUser != null) {
                System.out.println("AccessCard antes de borrar User: " + foundUser.getAccessCard());
                session.remove(foundUser); // orphanRemoval = true → borra AccessCard automáticamente
            }
            tx4.commit();

            System.out.println("\n========= FIN DEL PROGRAMA =========");

        } finally {
            session.close();
            HibernateUtil.close();
        }

    }
}
