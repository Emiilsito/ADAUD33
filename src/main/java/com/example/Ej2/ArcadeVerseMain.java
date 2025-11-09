package com.example.Ej2;


import com.example.Ej2.config.HibernateUtil;
import com.example.Ej2.dao.hibernateimpl.*;
import com.example.Ej2.domain.*;
import com.example.Ej2.service.CabinetService;
import com.example.Ej2.service.PlayerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArcadeVerseMain {

    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        PlayerService playerService = new PlayerService();
        CabinetService cabinetService = new CabinetService();

        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();

            System.out.println("=== INICIO DE PRUEBAS ARCADEVERSE ===");

            // Alta de Arcade y Game
            Arcade arcade = getOrCreateArcade(session, "Arcade Central", "Madrid");
            Game game = getOrCreateGame(session, "SF2", "Street Fighter II", 1991);

            // Alta de Cabinet asociado
            Cabinet cabinet = getOrCreateCabinet(session, "cabinet-sf2", arcade, game);
            System.out.println("Cabinet asignado a arcade y juego.");

            // Alta de Player
            Player player = getOrCreatePlayer(session, "Ryu", "ryu@arcadeverse.com");
            System.out.println("Jugador creado o recuperado: " + player.getNickname());

            // Emitir RFID Card usando la misma sesión
            playerService.emitirRfidCard(player.getId(), "UID-001");
            System.out.println("Tarjeta RFID emitida para el jugador.");

            // Alta de Tags y asociación al Cabinet
            Tag tagLucha = getOrCreateTag(session, "Lucha");
            Tag tagRetro = getOrCreateTag(session, "Retro");
            cabinetService.addTag(cabinet.getId(), tagLucha.getId());
            cabinetService.addTag(cabinet.getId(), tagRetro.getId());
            System.out.println("Tags añadidos al cabinet.");

            // Crear dos Matches
            MatchDaoHibernate matchDao = new MatchDaoHibernate();

            Match m1 = new Match();
            m1.setPlayer(player);
            m1.setCabinet(cabinet);
            m1.setScore(5000);
            m1.setCreditsUsed(2);
            m1.setDurationSec(300);
            m1.setResult(Match.Result.WIN);
            m1.setStartedAt(LocalDateTime.now());
            matchDao.create(session, m1);

            Match m2 = new Match();
            m2.setPlayer(player);
            m2.setCabinet(cabinet);
            m2.setScore(8000);
            m2.setCreditsUsed(3);
            m2.setDurationSec(400);
            m2.setResult(Match.Result.LOSE);
            m2.setStartedAt(LocalDateTime.now());
            matchDao.create(session, m2);
            System.out.println("Matches creados para el jugador.");

            // Lectura/Listado
            List<Match> matches = matchDao.findAll(session);
            System.out.println("\n=== Listado de partidas ===");
            for (Match match : matches) {
                System.out.printf("Jugador: %s | Juego: %s | Puntuación: %d | Resultado: %s%n",
                        match.getPlayer().getNickname(),
                        match.getCabinet().getGame().getName(),
                        match.getScore(),
                        match.getResult());
            }

            // Actualizar estado del Cabinet
            cabinet.setStatus(Cabinet.Status.MAINTENANCE);
            new CabinetDaoHibernate().update(session, cabinet);
            System.out.println("\nCabinet puesto en mantenimiento: " + cabinet.getSlug());

            // Borrados controlados
            cabinetService.removeTag(cabinet.getId(), tagRetro.getId());
            Long cardId = playerService.emitirRfidCard(player.getId(), "UID-001");
            System.out.println("Tarjeta RFID emitida para el jugador con ID: " + cardId);

            RfidCard card = player.getRfidCard();
            if (card != null) {
                playerService.cambiarEstadoTarjeta(card.getId(), false);
                playerService.retirarTarjeta(player.getId());
            }


            System.out.println("\nTags y tarjeta retirados correctamente.");

            tx.commit();
            System.out.println("\nPRUEBA COMPLETADA CON ÉXITO.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // HELPERS DE UNICIDAD
    // ============================================================

    private static Arcade getOrCreateArcade(Session s, String name, String address) {
        Arcade existing = (Arcade) s.createQuery("FROM Arcade a WHERE a.name = :n")
                .setParameter("n", name)
                .uniqueResult();
        if (existing != null) return existing;

        Arcade a = new Arcade();
        a.setName(name);
        a.setAddress(address);
        a.setCabinets(new ArrayList<>());
        new ArcadeDaoHibernate().create(s, a);
        return a;
    }

    private static Game getOrCreateGame(Session s, String code, String name, int year) {
        Game existing = (Game) s.createQuery("FROM Game g WHERE g.code = :c OR g.name = :n")
                .setParameter("c", code)
                .setParameter("n", name)
                .uniqueResult();
        if (existing != null) return existing;

        Game g = new Game();
        g.setCode(code);
        g.setName(name);
        g.setReleaseYear(year);
        g.setCabinets(new ArrayList<>());
        new GameDaoHibernate().create(s, g);
        return g;
    }

    private static Cabinet getOrCreateCabinet(Session s, String slug, Arcade arcade, Game game) {
        Cabinet existing = (Cabinet) s.createQuery("FROM Cabinet c WHERE c.slug = :s")
                .setParameter("s", slug)
                .uniqueResult();
        if (existing != null) return existing;

        Cabinet c = new Cabinet();
        c.setSlug(slug);
        c.setBuildYear(2023);
        c.setStatus(Cabinet.Status.ACTIVE);
        c.setTags(new ArrayList<>());
        c.setMatches(new ArrayList<>());
        c.setArcade(arcade);
        c.setGame(game);

        new CabinetDaoHibernate().create(s, c);
        return c;
    }

    private static Player getOrCreatePlayer(Session s, String nickname, String email) {
        Player existing = (Player) s.createQuery("FROM Player p WHERE p.nickname = :n OR p.email = :e")
                .setParameter("n", nickname)
                .setParameter("e", email)
                .uniqueResult();
        if (existing != null) return existing;

        Player p = new Player();
        p.setNickname(nickname);
        p.setEmail(email);
        p.setCreatedAt(LocalDateTime.now());
        new PlayerDaoHibernate().create(s, p);
        return p;
    }

    private static Tag getOrCreateTag(Session s, String name) {
        Tag existing = (Tag) s.createQuery("FROM Tag t WHERE t.name = :n")
                .setParameter("n", name)
                .uniqueResult();
        if (existing != null) return existing;

        Tag t = new Tag();
        t.setName(name);
        t.setCabinets(new ArrayList<>());
        new TagDaoHibernate().create(s, t);
        return t;
    }
}
