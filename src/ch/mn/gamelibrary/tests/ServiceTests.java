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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.service.DBService;

public class ServiceTests {

    DBService service = new DBService();

    @Before
    public void before() {

        service.fillDatabaseIfEmpty();
    }

    @Test
    public void testFillDatabaseIfEmpty() {

        service.deleteAllData();

        assertTrue(service.readAll(Game.class).isEmpty());
        assertTrue(service.readAll(Developer.class).isEmpty());
        assertTrue(service.readAll(Publisher.class).isEmpty());

        service.fillDatabaseIfEmpty();

        assertFalse(service.readAll(Game.class).isEmpty());
        assertFalse(service.readAll(Developer.class).isEmpty());
        assertFalse(service.readAll(Publisher.class).isEmpty());
    }

    @Test
    public void testDeleteFromEveryTable() {

        service.fillDatabaseIfEmpty();
        service.deleteFromTable(Game.class);
        service.deleteFromTable(Developer.class);
        service.deleteFromTable(Publisher.class);

        assertTrue(service.readAll(Game.class).isEmpty());
        assertTrue(service.readAll(Developer.class).isEmpty());
        assertTrue(service.readAll(Publisher.class).isEmpty());

        service.fillDatabaseIfEmpty();
        service.deleteAllData();

        assertTrue(service.readAll(Game.class).isEmpty());
        assertTrue(service.readAll(Developer.class).isEmpty());
        assertTrue(service.readAll(Publisher.class).isEmpty());
    }

    @After
    public void after() {

        service.deleteAllData();
    }

}
