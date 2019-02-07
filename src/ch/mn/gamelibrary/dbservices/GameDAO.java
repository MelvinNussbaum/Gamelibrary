/******************************************************************************
 *
 * [ GameDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.dbservices;

import javax.persistence.EntityManager;

import ch.mn.gamelibrary.model.Game;

public class GameDAO extends AbstractDAO<Game> {

    @Override
    public void create() {

        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(new Game("title", "developer", "publisher", 0, 0, 0));

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Game read(Game game) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Game game) {

        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Game game) {

        // TODO Auto-generated method stub

    }

    @Override
    public Game[] readAll() {

        // TODO Auto-generated method stub
        return null;
    }

}
