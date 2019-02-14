/******************************************************************************
 *
 * [ AbstractDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.DAO;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

public abstract class AbstractDAO<T, K> implements IDAO<T, K> {

    protected EntityManager em;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractDAO(EntityManager em) {
        super();
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.em = em;
    }

    @Override
    public void persist(T dbObject) {

        em.persist(dbObject);
    }

    @Override
    public T retrieve(K primaryKey) {

        return em.find(entityClass, primaryKey);
    }

    @Override
    public void update(T dbObject) {

        em.merge(dbObject);
    }

    @Override
    public void delete(T dbObject) {

        em.remove(em.merge(dbObject));
    }
}
