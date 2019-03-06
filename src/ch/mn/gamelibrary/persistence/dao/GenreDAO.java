/******************************************************************************
 *
 * [ GenreDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import ch.mn.gamelibrary.model.Genre;

public class GenreDAO extends AbstractDAO<Genre> {

    public GenreDAO(EntityManager em) {
        super(em);
    }

    public Genre findByName(String genre) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        ParameterExpression<String> params = cb.parameter(String.class);
        CriteriaQuery<Genre> criteria = cb.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);

        criteria.select(root);
        criteria.where(cb.equal(root.get("name"), params));

        TypedQuery<Genre> query = em.createQuery(criteria);
        query.setParameter(params, genre);

        List<Genre> queryResult = query.getResultList();

        if (queryResult.isEmpty()) {
            return null;
        }

        return queryResult.get(0);
    }

}
