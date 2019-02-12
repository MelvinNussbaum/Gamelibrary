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
import ch.mn.gamelibrary.dbservices.DeveloperDAO;
import ch.mn.gamelibrary.dbservices.GameDAO;
import ch.mn.gamelibrary.dbservices.PublisherDAO;
import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Publisher;

public class DBTests {

    private static EntityManagerFactory factory;

    private EntityManager em;

    private DeveloperDAO devDAO = new DeveloperDAO();

    private PublisherDAO pubDAO = new PublisherDAO();

    private GameDAO gameDAO = new GameDAO();

    private Developer developer = new Developer("Naughty Dog", "NDCEO", "NDHQ");

    private Publisher publisher = new Publisher("Electronic Arts", "EACEO", "EAHQ");

    private Game game = new Game("The Last of Us", developer, publisher, 69.90f, 95, 17000000);

    @BeforeClass
    public static void beforeClass() {

        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT);
    }

    @Before
    public void before() {

        em = factory.createEntityManager();
        em.getTransaction().begin();

    }

    @Test
    public void testGameDeveloperPublisherDBPersist() {

        devDAO.persist(developer);
        pubDAO.persist(publisher);
        gameDAO.persist(game);

        em.getTransaction().commit();

        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Developer dbDeveloper = devDAO.retrieve(developer);
        Game dbGame = gameDAO.retrieve(game);

        assertEquals(dbPublisher, publisher);
        assertEquals(dbDeveloper, developer);
        assertEquals(dbGame, game);

    }

    @Test
    public void testGameDeveloperPublisherDBRemove() {

        gameDAO.delete(game);
        devDAO.delete(developer);
        pubDAO.delete(publisher);

        em.getTransaction().commit();

        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Developer dbDeveloper = devDAO.retrieve(developer);
        Game dbGame = gameDAO.retrieve(game);

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

        factory.close();
    }

}
