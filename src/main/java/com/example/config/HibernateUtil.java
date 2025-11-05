package com.example.config;

import com.example.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.util.Properties;

public final class HibernateUtil {

    private static volatile SessionFactory sessionFactory;
    private static volatile StandardServiceRegistry registry;

    private HibernateUtil() { }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties props = loadAppProperties();
            init(props);
        }
        return sessionFactory;
    }

    private static synchronized void init(Properties properties) {
        if (sessionFactory == null) {
            registry = new StandardServiceRegistryBuilder()
                    .applySettings(properties)
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(AccessCard.class)
                    .addAnnotatedClass(Booking.class)
                    .addAnnotatedClass(Space.class)
                    .addAnnotatedClass(Tag.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Venue.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    private static Properties loadAppProperties() {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties")) {

            if (in == null) {
                throw new IllegalStateException("No se encuentra application.properties en el classpath");
            }
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (Exception e) {
            throw new RuntimeException("Error cargando application.properties", e);
        }
    }

    public static void close() {
        //Closes caches and connections pools
        getSessionFactory().close();
    }
}
