package it.unicam.cs.mpgc.rpg118302.repositories.impl;

import jakarta.persistence.*;

/**
 * Repository di base generico per operazioni CRUD comuni.
 */
public abstract class BaseRepository<T, ID> {
    private final Class<T> entityClass;
    protected EntityManager entityManager;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public void delete(ID id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public java.util.Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        return java.util.Optional.ofNullable(entity);
    }
}