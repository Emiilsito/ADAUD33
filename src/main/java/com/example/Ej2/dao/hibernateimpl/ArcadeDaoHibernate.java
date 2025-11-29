package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej1.domain.AccessCard;
import com.example.Ej2.dao.ArcadeDao;
import com.example.Ej2.domain.Arcade;
import com.example.Ej2.dto.ArcadeEstimatedIncome;
import jakarta.persistence.Tuple;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArcadeDaoHibernate extends GenericDaoHibernate<Arcade, Long> implements ArcadeDao {
    public ArcadeDaoHibernate() {
        super(Arcade.class);
    }

    @Override
    public List<Arcade> findByName(Session session, String name) {
        return session.createNamedQuery("Arcade.findByNamePattern", Arcade.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<ArcadeEstimatedIncome> estimatedIncomeByArcade(Session session) {

        String sql = """
        SELECT a.id, a.name, a.address,
               SUM((m.duration_sec / 3600) * c.hourly_cost) AS expectedIncome
        FROM arcades a
        LEFT JOIN cabinets c ON a.id = c.arcade_id
        LEFT JOIN matches m ON c.id = m.cabinet_id
        GROUP BY a.id, a.name, a.address
    """;

        List<Tuple> rows = session.createNativeQuery(sql, Tuple.class).getResultList();

        List<ArcadeEstimatedIncome> result = new ArrayList<>();

        for (Tuple t : rows) {
            result.add(
                    new ArcadeEstimatedIncome(
                            new Arcade(
                                    t.get("id", Number.class).longValue(),
                                    t.get("name", String.class),
                                    t.get("address", String.class)
                            ),
                            t.get("expectedIncome", BigDecimal.class)
                    )
            );
        }

        return result;
    }
}
