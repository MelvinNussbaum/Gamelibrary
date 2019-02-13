/******************************************************************************
 *
 * [ MainController.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.controller;

import ch.mn.gamelibrary.dbservices.DeveloperDAO;
import ch.mn.gamelibrary.dbservices.GameDAO;
import ch.mn.gamelibrary.dbservices.PublisherDAO;
import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Publisher;

public class MainController {

    private GameDAO gameDAO = new GameDAO();

    private DeveloperDAO devDAO = new DeveloperDAO();

    private PublisherDAO pubDAO = new PublisherDAO();

    public void fillDatabaseIfEmpty() {

        if (gameDAO.readAll().size() == 0) {

            Developer naughtyDogDev = new Developer("Naughty Dog", "Evan Wells",
                "Santa Monica, Kalifornien, Vereinigte Staaten");
            Developer rockstarDev = new Developer("Rockstar North", "Andrew Semple", "Edinburgh, Schottland");

            Publisher eaPub = new Publisher("Electronic Arts", "Andrew Wilson",
                "Redwood City, Kalifornien, Vereinigte Staaten");
            Publisher sonyPub = new Publisher("Sony Interactive Entertainment", "John Kodera, Shawn Layden, Jim Ryan",
                "San Mateo, Kalifornien, Vereinigte Staaten");
            Publisher rockstarPub = new Publisher("Rockstar Games", "Dan & Sam Houser",
                "New York City, New York, Vereinigte Staaten");

            Game tlouGame = new Game("The Last of Us", naughtyDogDev, eaPub, 95, 17000000);
            Game uncharted4Game = new Game("Uncharted 4: A Thief’s End", naughtyDogDev, sonyPub, 93, 8700000);
            Game gtaVGame = new Game("Grand Theft Auto V", rockstarDev, rockstarPub, 97, 100000000);

            devDAO.persist(naughtyDogDev);
            devDAO.persist(rockstarDev);
            pubDAO.persist(eaPub);
            pubDAO.persist(sonyPub);
            pubDAO.persist(rockstarPub);
            gameDAO.persist(tlouGame);
            gameDAO.persist(uncharted4Game);
            gameDAO.persist(gtaVGame);
        }
    }

    public void printAllGames() {

        for (Game game : gameDAO.readAll()) {
            System.out.println(game);
        }
    }
}
