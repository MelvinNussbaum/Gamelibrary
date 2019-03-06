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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.mn.gamelibrary.Main;
import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.dao.DeveloperDAO;
import ch.mn.gamelibrary.persistence.dao.GameDAO;
import ch.mn.gamelibrary.persistence.dao.GenreDAO;
import ch.mn.gamelibrary.persistence.dao.PublisherDAO;

public class TransactionTests {

    private static EntityManagerFactory factory;

    private EntityManager em;

    private DeveloperDAO devDAO;

    private PublisherDAO pubDAO;

    private GenreDAO genreDAO;

    private GameDAO gameDAO;

    private Developer developer = new Developer("Naughty Dog", "Andy Gavin, Jason Rubin",
        "Santa Monica, Kalifornien, Vereinigte Staaten");

    private Publisher publisher = new Publisher("Electronic Arts", "Trip Hawkins",
        "Redwood City, Kalifornien, Vereinigte Staaten");

    private Genre actionGen = new Genre("Action");

    private Genre adventureGen = new Genre("Adventure");

    private Game game1 = new Game("Game1", developer, publisher, 95, 17000000, actionGen, adventureGen);

    private Game game2 = new Game("Game2", developer, publisher, 0, 0, actionGen, adventureGen);

    @BeforeClass
    public static void beforeClass() {

        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_ECLIPSELINK);
    }

    @Before
    public void before() {

        em = factory.createEntityManager();
        devDAO = new DeveloperDAO(em);
        pubDAO = new PublisherDAO(em);
        genreDAO = new GenreDAO(em);
        gameDAO = new GameDAO(em);

        em.getTransaction().begin();
        gameDAO.persist(game1);
        gameDAO.persist(game2);
        em.getTransaction().commit();
    }

    @Test
    public void testGameDeveloperPublisherDBRetrieve() {

        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Developer dbDeveloper = devDAO.retrieve(developer);
        Genre dbGenre = genreDAO.retrieve(actionGen);
        Game dbGame1 = gameDAO.retrieve(game1);
        Game dbGame2 = gameDAO.retrieve(game2);

        assertEquals(publisher, dbPublisher);
        assertEquals(developer, dbDeveloper);
        assertEquals(actionGen, dbGenre);
        assertEquals(game1, dbGame1);
        assertEquals(game2, dbGame2);

        em.getTransaction().begin();
        gameDAO.delete(game1);
        gameDAO.delete(game2);
        genreDAO.delete(actionGen);
        genreDAO.delete(adventureGen);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
        em.getTransaction().commit();
    }

    @Test
    public void testGameDeveloperPublisherDBUpdate() {

        game1.setTitle("TLoU");
        actionGen.setName("ACTION");
        developer.setName("ND");
        publisher.setName("EA");

        em.getTransaction().begin();
        gameDAO.update(game1);
        genreDAO.update(actionGen);
        devDAO.update(developer);
        pubDAO.update(publisher);
        em.getTransaction().commit();

        Game dbGame = gameDAO.retrieve(game1);
        Genre dbGenre = genreDAO.retrieve(actionGen);
        Developer dbDeveloper = devDAO.retrieve(developer);
        Publisher dbPublisher = pubDAO.retrieve(publisher);

        assertEquals(game1, dbGame);
        assertEquals(actionGen, dbGenre);
        assertEquals(developer, dbDeveloper);
        assertEquals(publisher, dbPublisher);

        em.getTransaction().begin();
        gameDAO.delete(game1);
        genreDAO.delete(actionGen);
        genreDAO.delete(adventureGen);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
        em.getTransaction().commit();
    }

    @Test
    public void testGameDeveloperPublisherDBRemove() {

        em.getTransaction().begin();
        gameDAO.delete(game1);
        genreDAO.delete(actionGen);
        genreDAO.delete(adventureGen);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
        em.getTransaction().commit();

        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Genre dbGenre = genreDAO.retrieve(actionGen);
        Developer dbDeveloper = devDAO.retrieve(developer);
        Game dbGame = gameDAO.retrieve(game1);

        assertNull(dbPublisher);
        assertNull(dbGenre);
        assertNull(dbDeveloper);
        assertNull(dbGame);
    }

    @After
    public void after() {

        em.close();
    }

    @AfterClass
    public static void afterClass() {

        factory.close();
    }

}
