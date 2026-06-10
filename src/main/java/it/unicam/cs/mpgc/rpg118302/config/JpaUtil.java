package it.unicam.cs.mpgc.rpg118302.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe di utilità per la gestione EntityManager JPA.
 */
public class JpaUtil {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    static {
        try {
            Configuration config = new Configuration();
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            config.setProperty("hibernate.connection.url", "jdbc:h2:file:./dungeon-rpg-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            config.setProperty("hibernate.connection.username", "sa");
            config.setProperty("hibernate.connection.password", "");
            config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            config.setProperty("hibernate.show_sql", "false");
            config.setProperty("hibernate.format_sql", "true");

            config.addAnnotatedClass(it.unicam.cs.mpgc.rpg118302.models.entities.Character.class);
            config.addAnnotatedClass(it.unicam.cs.mpgc.rpg118302.models.entities.Enemy.class);
            config.addAnnotatedClass(it.unicam.cs.mpgc.rpg118302.models.entities.Battle.class);

            emf = config.buildSessionFactory().unwrap(EntityManagerFactory.class);
            em = emf.createEntityManager();
        } catch (Exception e) {
            System.err.println("Error initializing JPA: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize JPA", e);
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new RuntimeException("EntityManagerFactory not initialized");
        }
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public static void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public static void closeEntityManagerFactory() {
        closeEntityManager();
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}