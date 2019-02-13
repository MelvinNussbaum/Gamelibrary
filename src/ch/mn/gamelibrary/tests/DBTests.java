/******************************************************************************
 *
 * [ Test.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.mn.gamelibrary.dbservices.DeveloperDAO;
import ch.mn.gamelibrary.dbservices.GameDAO;
import ch.mn.gamelibrary.dbservices.PublisherDAO;
import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Publisher;

public class DBTests {

    private DeveloperDAO devDAO = new DeveloperDAO();

    private PublisherDAO pubDAO = new PublisherDAO();

    private GameDAO gameDAO = new GameDAO();

    private Developer developer = new Developer("Naughty Dog", "Andy Gavin, Jason Rubin",
        "Santa Monica, Kalifornien, Vereinigte Staaten");

    private Publisher publisher = new Publisher("Electronic Arts", "Trip Hawkins",
        "Redwood City, Kalifornien, Vereinigte Staaten");

    private Game game = new Game("The Last of Us", developer, publisher, 95, 17000000);

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {

    }

    @Test
    public void testGameDeveloperPublisherDBRetrieve() {

        devDAO.persist(developer);
        pubDAO.persist(publisher);
        gameDAO.persist(game);

        Publisher dbPublisher = pubDAO.retrieve(publisher.getId());
        Developer dbDeveloper = devDAO.retrieve(developer.getId());
        Game dbGame = gameDAO.retrieve(game.getId());

        assertEquals(publisher, dbPublisher);
        assertEquals(developer, dbDeveloper);
        assertEquals(game, dbGame);

        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
    }

    @Test
    public void testGameDeveloperPublisherDBUpdate() {

        devDAO.persist(developer);
        pubDAO.persist(publisher);
        gameDAO.persist(game);

        game.setTitle("TLoU");
        developer.setName("ND");
        publisher.setName("EA");

        gameDAO.update(game);
        devDAO.update(developer);
        pubDAO.update(publisher);

        Game dbGame = gameDAO.retrieve(game.getId());
        Developer dbDeveloper = devDAO.retrieve(developer.getId());
        Publisher dbPublisher = pubDAO.retrieve(publisher.getId());

        assertEquals(game, dbGame);
        assertEquals(developer, dbDeveloper);
        assertEquals(publisher, dbPublisher);

        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
    }

    @Test
    public void testGameDeveloperPublisherDBRemove() {

        devDAO.persist(developer);
        pubDAO.persist(publisher);
        gameDAO.persist(game);

        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);

        Publisher dbPublisher = pubDAO.retrieve(publisher.getId());
        Developer dbDeveloper = devDAO.retrieve(developer.getId());
        Game dbGame = gameDAO.retrieve(game.getId());

        assertNull(dbPublisher);
        assertNull(dbDeveloper);
        assertNull(dbGame);
    }

    @After
    public void after() {

    }

    @AfterClass
    public static void afterClass() {

    }

}
