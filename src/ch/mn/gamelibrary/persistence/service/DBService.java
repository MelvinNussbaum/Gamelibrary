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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

            Developer naughtyDogDev = new Developer("Naughty Dog", "Evan Wells", "Santa Monica, Kalifornien, USA");
            Developer rockstarDev = new Developer("Rockstar North", "Andrew Semple", "Edinburgh, Schottland");
            Developer eaCanDev = new Developer("EA Vancouver", "Don Mattrick (Founder)",
                "Burnaby, British Columbia, Kanada");

            Publisher eaPub = new Publisher("Electronic Arts", "Andrew Wilson", "Redwood City, Kalifornien, USA");
            Publisher sonyPub = new Publisher("Sony Interactive Entertainment", "John Kodera, Shawn Layden, Jim Ryan",
                "San Mateo, Kalifornien, USA");
            Publisher rockstarPub = new Publisher("Rockstar Games", "Dan & Sam Houser", "New York City, New York, USA");

            Genre sportGen = new Genre("Sport");
            Genre actionGen = new Genre("Action");
            Genre adventureGen = new Genre("Adventure");
            Genre openWorld = new Genre("Open World");

            byte[] fifa19Cover = null;
            byte[] gtaVCover = null;
            byte[] tlouCover = null;
            byte[] unchartedCover = null;

            try {
                BufferedImage bFifa19Cover = ImageIO.read(new File("src/assets/img/fifa19Cover.jpg"));
                BufferedImage bGTAVCover = ImageIO.read(new File("src/assets/img/GTAVCover.jpg"));
                BufferedImage bTLoUCover = ImageIO.read(new File("src/assets/img/TLoUCover.jpg"));
                BufferedImage bUnchartedCover = ImageIO.read(new File("src/assets/img/Uncharted4Cover.jpg"));

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bFifa19Cover, "jpg", bos);
                fifa19Cover = bos.toByteArray();
                bos.reset();

                ImageIO.write(bGTAVCover, "jpg", bos);
                gtaVCover = bos.toByteArray();
                bos.reset();

                ImageIO.write(bTLoUCover, "jpg", bos);
                tlouCover = bos.toByteArray();
                bos.reset();

                ImageIO.write(bUnchartedCover, "jpg", bos);
                unchartedCover = bos.toByteArray();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Game tlouGame = new Game("The Last of Us", naughtyDogDev, sonyPub, 95, 17000000, tlouCover, actionGen,
                adventureGen);
            Game uncharted4Game = new Game("Uncharted 4: A Thief’s End", naughtyDogDev, sonyPub, 93, 8700000,
                unchartedCover, actionGen, adventureGen);
            Game gtaVGame = new Game("Grand Theft Auto V", rockstarDev, rockstarPub, 97, 100000000, gtaVCover,
                openWorld);
            Game fifa19 = new Game("FIFA 19", eaCanDev, eaPub, 83, 5000000, fifa19Cover, sportGen);

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
