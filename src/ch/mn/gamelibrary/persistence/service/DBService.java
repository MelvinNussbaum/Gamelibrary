/******************************************************************************
 *
 * [ DBService.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.model.Publisher;

public class DBService {

    private GameService gameService = new GameService();

    private GenreService genreService = new GenreService();

    private DeveloperService devService = new DeveloperService();

    private PublisherService pubService = new PublisherService();

    public DBService() {
        // TODO Auto-generated constructor stub
    }

    public void fillDatabaseIfEmpty() {

        if (gameService.readAll().isEmpty() || devService.readAll().isEmpty() || pubService.readAll().isEmpty()
            || genreService.readAll().isEmpty()) {
            deleteAllData();

            Developer naughtyDogDev = new Developer("Naughty Dog", "Evan Wells",
                "Santa Monica, Kalifornien, Vereinigte Staaten");
            Developer rockstarDev = new Developer("Rockstar North", "Andrew Semple", "Edinburgh, Schottland");
            Developer eaCanDev = new Developer("EA Vancouver", "Don Mattrick (Founder)",
                "Burnaby, British Columbia, Kanada");

            Publisher eaPub = new Publisher("Electronic Arts", "Andrew Wilson",
                "Redwood City, Kalifornien, Vereinigte Staaten");
            Publisher sonyPub = new Publisher("Sony Interactive Entertainment", "John Kodera, Shawn Layden, Jim Ryan",
                "San Mateo, Kalifornien, Vereinigte Staaten");
            Publisher rockstarPub = new Publisher("Rockstar Games", "Dan & Sam Houser",
                "New York City, New York, Vereinigte Staaten");

            Genre sportGen = new Genre("Sport");
            Genre actionGen = new Genre("Action");
            Genre adventureGen = new Genre("Adventure");
            Genre openWorld = new Genre("Open World");

            Game tlouGame = new Game("The Last of Us", naughtyDogDev, sonyPub, 95, 17000000, actionGen, adventureGen);
            Game uncharted4Game = new Game("Uncharted 4: A Thief’s End", naughtyDogDev, sonyPub, 93, 8700000, actionGen,
                adventureGen);
            Game gtaVGame = new Game("Grand Theft Auto V", rockstarDev, rockstarPub, 97, 100000000, openWorld);
            Game fifa19 = new Game("FIFA 19", eaCanDev, eaPub, 83, 5000000, sportGen);

            gameService.varPersist(tlouGame, uncharted4Game, gtaVGame, fifa19);
        }
    }

    public void printAllEntities() {

        gameService.printEntities();
        devService.printEntities();
        pubService.printEntities();
        genreService.printEntities();
    }

    public void deleteAllData() {

        gameService.deleteAll();
        genreService.deleteAll();
        devService.deleteAll();
        pubService.deleteAll();
    }
}
