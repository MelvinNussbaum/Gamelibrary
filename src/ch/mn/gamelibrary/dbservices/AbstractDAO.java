/******************************************************************************
 *
 * [ AbstractDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.dbservices;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import ch.mn.gamelibrary.Main;

public abstract class AbstractDAO<T, K> implements IDAO<T, K> {

    protected EntityManagerFactory factory;

    protected EntityManager em;

    protected Class<T> entityClass;

    public AbstractDAO() {
        super();
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_ECLIPSELINK);
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected void before() {

        em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    protected void after() {

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void persist(T dbObject) {

        before();
        em.persist(dbObject);
        after();
    }

    @Override
    public T retrieve(K primaryKey) {

        em = factory.createEntityManager();
        T dbObject = em.find(entityClass, primaryKey);
        em.close();
        return dbObject;
    }

    @Override
    public void update(T dbObject) {

        before();
        em.merge(dbObject);
        after();
    }

    @Override
    public void delete(T dbObject) {

        before();
        T mergedObject = em.merge(dbObject);
        em.remove(mergedObject);
        after();
    }

    @Override
    public List<T> readAll() {

        em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        CriteriaQuery<T> getAll = cq.select(cq.from(entityClass));
        TypedQuery<T> query = em.createQuery(getAll);
        List<T> results = query.getResultList();
        em.close();
        return results;
    }

}
