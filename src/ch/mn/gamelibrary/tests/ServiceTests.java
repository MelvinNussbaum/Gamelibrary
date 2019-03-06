/******************************************************************************
 *
 * [ ServiceTests.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.persistence.service.DBService;
import ch.mn.gamelibrary.persistence.service.DeveloperService;
import ch.mn.gamelibrary.persistence.service.GameService;
import ch.mn.gamelibrary.persistence.service.GenreService;
import ch.mn.gamelibrary.persistence.service.PublisherService;

public class ServiceTests {

    DBService service = new DBService();

    GameService gameService = new GameService();

    GenreService genreService = new GenreService();

    DeveloperService devService = new DeveloperService();

    PublisherService pubService = new PublisherService();

    @Before
    public void before() {

        service.fillDatabaseIfEmpty();
    }

    @Test
    public void testFillDatabaseIfEmpty() {

        service.deleteAllData();

        assertTrue(gameService.readAll().isEmpty());
        assertTrue(genreService.readAll().isEmpty());
        assertTrue(devService.readAll().isEmpty());
        assertTrue(pubService.readAll().isEmpty());

        service.fillDatabaseIfEmpty();

        assertFalse(gameService.readAll().isEmpty());
        assertFalse(genreService.readAll().isEmpty());
        assertFalse(devService.readAll().isEmpty());
        assertFalse(pubService.readAll().isEmpty());
    }

    @Test
    public void testDeleteFromEveryTable() {

        service.fillDatabaseIfEmpty();
        gameService.deleteAll();
        genreService.deleteAll();
        devService.deleteAll();
        pubService.deleteAll();

        assertTrue(gameService.readAll().isEmpty());
        assertTrue(genreService.readAll().isEmpty());
        assertTrue(devService.readAll().isEmpty());
        assertTrue(pubService.readAll().isEmpty());

        service.fillDatabaseIfEmpty();
        service.deleteAllData();

        assertTrue(gameService.readAll().isEmpty());
        assertTrue(genreService.readAll().isEmpty());
        assertTrue(devService.readAll().isEmpty());
        assertTrue(pubService.readAll().isEmpty());
    }

    @Test
    public void testPersistRetrieveDeleteSingleEntity() {

        Genre genre = new Genre("Genre");
        genreService.persist(genre);
        Genre dbGenre = genreService.read(genre);

        assertEquals(genre, dbGenre);

        genreService.delete(genre);
        dbGenre = genreService.read(genre);

        assertNull(dbGenre);
    }

    @After
    public void after() {

        service.deleteAllData();
    }

}
