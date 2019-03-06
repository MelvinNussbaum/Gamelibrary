/******************************************************************************
 *
 * [ GameDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.dao;

import javax.persistence.EntityManager;

import ch.mn.gamelibrary.model.Game;

public class GameDAO extends AbstractDAO<Game> {

    public GameDAO(EntityManager em) {
        super(em);
    }

    @Override
    public void delete(Game game) {

        game.removeAllGenres();
        super.delete(game);
    }

}
