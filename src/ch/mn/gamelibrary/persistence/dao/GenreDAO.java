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

import javax.persistence.EntityManager;

import ch.mn.gamelibrary.model.Genre;

public class GenreDAO extends AbstractDAO<Genre, Long> {

    public GenreDAO(EntityManager em) {
        super(em);
    }

}
