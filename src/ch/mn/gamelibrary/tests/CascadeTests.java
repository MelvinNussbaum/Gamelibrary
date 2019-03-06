/******************************************************************************
 *
 * [ CascadeTests.java ]
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
import ch.mn.gamelibrary.persistence.service.DBService;

public class CascadeTests {

    private DBService service = new DBService();

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

    }

    @Test
    public void developerPublisherGenresArePersistedAsGame() {

        em.getTransaction().begin();
        gameDAO.persist(game1);
        em.getTransaction().commit();

        Genre dbGenre = genreDAO.retrieve(actionGen);
        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Developer dbDeveloper = devDAO.retrieve(developer);

        assertEquals(actionGen, dbGenre);
        assertEquals(publisher, dbPublisher);
        assertEquals(developer, dbDeveloper);
    }

    @Test
    public void developerPublisherGenreAreDeletedLike_LAST_Game() {

        em.getTransaction().begin();
        gameDAO.persist(game1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        gameDAO.delete(game1);
        em.getTransaction().commit();

        Genre dbGenre = genreDAO.retrieve(actionGen);
        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Developer dbDeveloper = devDAO.retrieve(developer);

        assertNull(dbGenre);
        assertNull(dbPublisher);
        assertNull(dbDeveloper);
    }

    @Test
    public void developerPublisherGenresAre_NOT_DeletedLikeGame() {

        em.getTransaction().begin();
        gameDAO.persist(game1);
        gameDAO.persist(game2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        gameDAO.delete(game1);
        em.getTransaction().commit();

        Game dbGame2 = gameDAO.retrieve(game2);
        Genre dbGenre = genreDAO.retrieve(actionGen);
        Publisher dbPublisher = pubDAO.retrieve(publisher);
        Developer dbDeveloper = devDAO.retrieve(developer);

        assertEquals(game2, dbGame2);
        assertEquals(actionGen, dbGenre);
        assertEquals(publisher, dbPublisher);
        assertEquals(developer, dbDeveloper);
    }

    @Test
    public void deletingPublisherDeveloperGenresWithRelationsNotPossible() {

        em.getTransaction().begin();
        gameDAO.persist(game1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        pubDAO.delete(publisher);
        devDAO.delete(developer);
        genreDAO.delete(actionGen);
        em.getTransaction().commit();
    }

    @After
    public void after() {

        service.deleteAllData();
        em.close();
    }

    @AfterClass
    public static void afterClass() {

        factory.close();
    }

}
