/******************************************************************************
 *
 * [ GameService.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.persistence.dao.GameDAO;

public class GameService extends EntityService<Game> {

    @Override
    public void printEntities() {

        System.out.println("\n------ ALL GAMES ------" + "\n------------------------------");
        for (Game game : this.readAll()) {
            System.out.println(game);
        }
        System.out.println();
    }

    @Override
    public void createEntityManagerAndDAO() {

        this.em = factory.createEntityManager();
        this.dao = new GameDAO(em);
    }
}
