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
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.dao.DeveloperDAO;
import ch.mn.gamelibrary.persistence.dao.GameDAO;
import ch.mn.gamelibrary.persistence.dao.PublisherDAO;

public class DBTests {

    private static EntityManagerFactory factory;

    private EntityManager em;

    private DeveloperDAO devDAO;

    private PublisherDAO pubDAO;

    private GameDAO gameDAO;

    private Developer developer = new Developer("Naughty Dog", "Andy Gavin, Jason Rubin",
        "Santa Monica, Kalifornien, Vereinigte Staaten");

    private Publisher publisher = new Publisher("Electronic Arts", "Trip Hawkins",
        "Redwood City, Kalifornien, Vereinigte Staaten");

    private Game game = new Game("The Last of Us", developer, publisher, 95, 17000000);

    @BeforeClass
    public static void beforeClass() {

        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_ECLIPSELINK);
    }

    @Before
    public void before() {

        em = factory.createEntityManager();
        devDAO = new DeveloperDAO(em);
        pubDAO = new PublisherDAO(em);
        gameDAO = new GameDAO(em);

        em.getTransaction().begin();
        devDAO.persist(developer);
        pubDAO.persist(publisher);
        gameDAO.persist(game);
        em.getTransaction().commit();
    }

    @Test
    public void testGameDeveloperPublisherDBRetrieve() {

        Publisher dbPublisher = pubDAO.retrieve(publisher.getId());
        Developer dbDeveloper = devDAO.retrieve(developer.getId());
        Game dbGame = gameDAO.retrieve(game.getId());

        assertEquals(publisher, dbPublisher);
        assertEquals(developer, dbDeveloper);
        assertEquals(game, dbGame);

        em.getTransaction().begin();
        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
        em.getTransaction().commit();
    }

    @Test
    public void testGameDeveloperPublisherDBUpdate() {

        game.setTitle("TLoU");
        developer.setName("ND");
        publisher.setName("EA");

        em.getTransaction().begin();
        gameDAO.update(game);
        devDAO.update(developer);
        pubDAO.update(publisher);
        em.getTransaction().commit();

        Game dbGame = gameDAO.retrieve(game.getId());
        Developer dbDeveloper = devDAO.retrieve(developer.getId());
        Publisher dbPublisher = pubDAO.retrieve(publisher.getId());

        assertEquals(game, dbGame);
        assertEquals(developer, dbDeveloper);
        assertEquals(publisher, dbPublisher);

        em.getTransaction().begin();
        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
        em.getTransaction().commit();
    }

    @Test
    public void testGameDeveloperPublisherDBRemove() {

        em.getTransaction().begin();
        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);
        em.getTransaction().commit();

        Publisher dbPublisher = pubDAO.retrieve(publisher.getId());
        Developer dbDeveloper = devDAO.retrieve(developer.getId());
        Game dbGame = gameDAO.retrieve(game.getId());

        assertNull(dbPublisher);
        assertNull(dbDeveloper);
        assertNull(dbGame);
    }

    @After
    public void after() {

        em.close();
    }

    @AfterClass
    public static void afterClass() {

    }

}
