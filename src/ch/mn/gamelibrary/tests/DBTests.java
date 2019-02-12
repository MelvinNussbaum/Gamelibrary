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

public class DBTests {

    private static EntityManagerFactory managerFactory;

    private EntityManager em;

    @BeforeClass
    public static void beforeClass() {

        managerFactory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT);
    }

    @Before
    public void before() {

        em = managerFactory.createEntityManager();
        em.getTransaction().begin();

    }

    @Test
    public void testGameDeveloperPublisherDBTransaction() {

        Developer developer = new Developer("Naughty Dog", "NDCEO", "NDHQ");
        Publisher publisher = new Publisher("Electronic Arts", "EACEO", "EAHQ");
        Game game = new Game("The Last of Us", developer, publisher, 69.90f, 95, 17000000);

        em.persist(developer);
        em.persist(publisher);
        em.persist(game);

        em.getTransaction().commit();

        Publisher dbPublisher = em.find(Publisher.class, publisher.getId());
        Developer dbDeveloper = em.find(Developer.class, developer.getId());
        Game dbGame = em.find(Game.class, game.getId());

        assertEquals(dbPublisher, publisher);
        assertEquals(dbDeveloper, developer);
        assertEquals(dbGame, game);

        em.getTransaction().begin();
        em.remove(dbGame);
        em.remove(dbDeveloper);
        em.remove(dbPublisher);
        em.getTransaction().commit();
    }

    @After
    public void after() {

        em.close();
    }

    @AfterClass
    public static void afterClass() {

        managerFactory.close();
    }

}
