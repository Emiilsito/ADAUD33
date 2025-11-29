package com.example.Ej2;

import com.example.Ej2.config.HibernateUtil;
import com.example.Ej2.domain.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class MainADA342 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        long timestamp = System.currentTimeMillis(); // Para generar nombres/UID únicos

        System.out.println("\n========= INSERTANDO ARCADE + CABINETS =========");
        Transaction tx1 = session.beginTransaction();

        Arcade arcade = new Arcade();
        arcade.setName("Arcade " + timestamp);
        arcade.setAddress("Calle Prueba " + timestamp);

        Game game1 = new Game();
        game1.setCode("PAC-" + timestamp);
        game1.setName("Pac-Man " + timestamp);
        game1.setReleaseYear(1980);
        game1.setGenre("Arcade");

        Game game2 = new Game();
        game2.setCode("TET-" + timestamp);
        game2.setName("Tetris " + timestamp);
        game2.setReleaseYear(1984);
        game2.setGenre("Puzzle");

        Cabinet c1 = new Cabinet();
        c1.setSlug("CAB-01-" + timestamp);
        c1.setBuildYear(2020);
        c1.setStatus(Cabinet.Status.ACTIVE);
        c1.setArcade(arcade);
        c1.setGame(game1);

        Cabinet c2 = new Cabinet();
        c2.setSlug("CAB-02-" + timestamp);
        c2.setBuildYear(2019);
        c2.setStatus(Cabinet.Status.MAINTENANCE);
        c2.setArcade(arcade);
        c2.setGame(game2);

        arcade.setCabinets(List.of(c1, c2));

        session.persist(game1);
        session.persist(game2);
        session.persist(arcade);

        tx1.commit();
        System.out.println("Cabinets en Arcade: " + arcade.getCabinets().size());

        System.out.println("\n========= INSERTANDO PLAYER + RFID =========");
        Transaction tx2 = session.beginTransaction();

        Player player = new Player();
        player.setNickname("player" + timestamp);
        player.setEmail("player" + timestamp + "@test.com");
        player.setCreatedAt(LocalDateTime.now());

        RfidCard rfid = new RfidCard();
        rfid.setUid("RFID-" + timestamp);
        rfid.setIssuedAt(LocalDateTime.now());
        rfid.setActive(true);
        rfid.setPlayer(player);

        player.setRfidCard(rfid);

        session.persist(player); // Cascade ALL → inserta RFID
        tx2.commit();
        System.out.println("RfidCard del Player: " + player.getRfidCard());

        System.out.println("\n========= INSERTANDO MATCHES =========");
        Transaction tx3 = session.beginTransaction();

        Match m1 = new Match();
        m1.setPlayer(player);
        m1.setCabinet(c1);
        m1.setStartedAt(LocalDateTime.now());
        m1.setDurationSec(300);
        m1.setScore(1000);
        m1.setResult(Match.Result.WIN);
        m1.setCreditsUsed(5);

        Match m2 = new Match();
        m2.setPlayer(player);
        m2.setCabinet(c2);
        m2.setStartedAt(LocalDateTime.now());
        m2.setDurationSec(200);
        m2.setScore(500);
        m2.setResult(Match.Result.LOSE);
        m2.setCreditsUsed(3);

        session.persist(m1);
        session.persist(m2);

        tx3.commit();
        System.out.println("Matches insertados: 2");

        System.out.println("\n========= BORRADO “PELIGROSO”: BORRAR PLAYER =========");
        Transaction tx4 = session.beginTransaction();

        Player foundPlayer = session.find(Player.class, player.getId());
        System.out.println("RfidCard antes de borrar Player: " + foundPlayer.getRfidCard());

        session.remove(foundPlayer); //Aqui esta el borrado peligroso, no quiero que se borre el jugador si tiene matches asignados, por eso hay excepcion.
        tx4.commit();
        System.out.println("Player borrado");

        System.out.println("\n========= FIN DEL PROGRAMA =========");
        session.close();
        HibernateUtil.close();
    }
}
