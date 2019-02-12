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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ch.mn.gamelibrary.Main;
import ch.mn.gamelibrary.model.DBObject;

public abstract class AbstractDAO<T extends DBObject> implements IDAO<T> {

    protected EntityManagerFactory factory;

    protected EntityManager em;

    protected Class<T> entityClass;

    public AbstractDAO() {
        super();
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT);
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    private void before() {

        em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    private void after() {

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
    public T retrieve(T dbObject) {

        return em.find(entityClass, dbObject.getId());
    }

    @Override
    public void update(T dbObject) {

        before();
        delete(dbObject);
        em.persist(dbObject);
        after();
    }

    @Override
    public void delete(T dbObject) {

        before();
        em.remove(retrieve(dbObject));
        after();
    }

    @Override
    public T[] readAll() {

        // TODO Auto-generated method stub
        return null;
    }

}
