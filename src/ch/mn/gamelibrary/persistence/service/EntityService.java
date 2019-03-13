/******************************************************************************
 *
 * [ MainController.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

import ch.mn.gamelibrary.Main;
import ch.mn.gamelibrary.model.DBEntity;
import ch.mn.gamelibrary.persistence.dao.AbstractDAO;

public abstract class EntityService<T extends DBEntity> implements IEntityService<T> {

    protected EntityManagerFactory factory;

    protected EntityManager em;

    protected AbstractDAO<T> dao;

    private Class<T> entityClass;

    public EntityService() {
        super();
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_ECLIPSELINK);
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void persist(T entity) {

        createEntityManagerAndDAO();

        em.getTransaction().begin();
        dao.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void varPersist(T... entities) {

        createEntityManagerAndDAO();

        em.getTransaction().begin();
        for (T entity : entities) {
            dao.persist(entity);
        }
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public T read(T entity) {

        createEntityManagerAndDAO();
        T result = dao.retrieve(entity);
        em.close();
        return result;
    }

    @Override
    public T findByName(String name) throws Exception {

        return readAll().stream().filter(obj -> obj.getName().equals(name)).findFirst().get();
    }

    @Override
    public void delete(T entity) {

        createEntityManagerAndDAO();

        em.getTransaction().begin();
        dao.delete(entity);
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public void deleteAll() {

        createEntityManagerAndDAO();

        em.getTransaction().begin();
        CriteriaDelete<T> cd = em.getCriteriaBuilder().createCriteriaDelete(entityClass);
        cd.from(entityClass);
        em.createQuery(cd).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<T> readAll() {

        createEntityManagerAndDAO();

        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        CriteriaQuery<T> getAll = cq.select(cq.from(entityClass));
        List<T> results = em.createQuery(getAll).getResultList();
        em.close();
        return results;
    }
}
