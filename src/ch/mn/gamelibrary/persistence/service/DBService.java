/******************************************************************************
 *
 * [ MainController.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

import ch.mn.gamelibrary.Main;
import ch.mn.gamelibrary.model.DBEntity;
import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.dao.DeveloperDAO;
import ch.mn.gamelibrary.persistence.dao.GameDAO;
import ch.mn.gamelibrary.persistence.dao.GenreDAO;
import ch.mn.gamelibrary.persistence.dao.PublisherDAO;

public class DBService {

    private EntityManagerFactory factory;

    EntityManager em;

    private GameDAO gameDAO;

    private GenreDAO genreDAO;

    private DeveloperDAO devDAO;

    private PublisherDAO pubDAO;

    public DBService() {
        super();
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_ECLIPSELINK);
    }

    public void fillDatabaseIfEmpty() {

        if (readAll(Game.class).isEmpty() || readAll(Developer.class).isEmpty() || readAll(Publisher.class).isEmpty()
            || readAll(Genre.class).isEmpty()) {
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

            Game tlouGame = new Game("The Last of Us", new HashSet<Genre>(Arrays.asList(actionGen, adventureGen)),
                naughtyDogDev, sonyPub, 95, 17000000);
            Game uncharted4Game = new Game("Uncharted 4: A Thief’s End",
                new HashSet<Genre>(Arrays.asList(actionGen, adventureGen)), naughtyDogDev, sonyPub, 93, 8700000);
            Game gtaVGame = new Game("Grand Theft Auto V", new HashSet<Genre>(Arrays.asList(openWorld)), rockstarDev,
                rockstarPub, 97, 100000000);
            Game fifa19 = new Game("FIFA 19", new HashSet<Genre>(Arrays.asList(sportGen)), eaCanDev, eaPub, 83,
                5000000);

            createEntityManagerAndDAOs();
            em.getTransaction().begin();
            devDAO.persist(naughtyDogDev);
            devDAO.persist(rockstarDev);
            devDAO.persist(eaCanDev);
            pubDAO.persist(eaPub);
            pubDAO.persist(sonyPub);
            pubDAO.persist(rockstarPub);
            gameDAO.persist(tlouGame);
            gameDAO.persist(uncharted4Game);
            gameDAO.persist(gtaVGame);
            gameDAO.persist(fifa19);
            em.getTransaction().commit();
            em.close();
        }
    }

    public void deleteAllData() {

        deleteFromTable(Game.class);
        deleteFromTable(Genre.class);
        deleteFromTable(Developer.class);
        deleteFromTable(Publisher.class);
    }

    public <T extends DBEntity> void deleteFromTable(Class<T> c) {

        createEntityManagerAndDAOs();

        em.getTransaction().begin();
        CriteriaDelete<T> cd = em.getCriteriaBuilder().createCriteriaDelete(c);
        cd.from(c);
        em.createQuery(cd).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public <T extends DBEntity> void delete(T obj) {

        createEntityManagerAndDAOs();
        em.getTransaction().begin();

        switch (obj.getClass().getSimpleName()) {
            case "Game":
                gameDAO.delete((Game) obj);
                break;
            case "Developer":
                devDAO.delete((Developer) obj);
                break;
            case "Publisher":
                pubDAO.delete((Publisher) obj);
                break;
            case "Genre":
                genreDAO.delete((Genre) obj);
                break;
            default:
                break;
        }

        em.getTransaction().commit();
        em.close();
    }

    public <T extends DBEntity> void persist(T obj) {

        createEntityManagerAndDAOs();
        em.getTransaction().begin();

        switch (obj.getClass().getSimpleName()) {
            case "Game":
                gameDAO.persist((Game) obj);
                break;
            case "Developer":
                devDAO.persist((Developer) obj);
                break;
            case "Publisher":
                pubDAO.persist((Publisher) obj);
                break;
            case "Genre":
                genreDAO.persist((Genre) obj);
                break;
            default:
                break;
        }

        em.getTransaction().commit();
        em.close();
    }

    public <T extends DBEntity> List<T> readAll(Class<T> c) {

        createEntityManagerAndDAOs();

        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(c);
        CriteriaQuery<T> getAll = cq.select(cq.from(c));
        List<T> results = em.createQuery(getAll).getResultList();
        em.close();
        return results;
    }

    public <T extends DBEntity> void printEntities(Class<T> c) {

        System.out.println(
            "\n------ " + "ALL " + c.getSimpleName().toUpperCase() + "S --------" + "\n------------------------------");
        for (T t : readAll(c)) {
            System.out.println(t);
        }
        System.out.println();
    }

    private void createEntityManagerAndDAOs() {

        this.em = factory.createEntityManager();
        this.gameDAO = new GameDAO(em);
        this.genreDAO = new GenreDAO(em);
        this.devDAO = new DeveloperDAO(em);
        this.pubDAO = new PublisherDAO(em);
    }

    public EntityManager getEm() {

        return em;
    }

    public void setEm(EntityManager em) {

        this.em = em;
        this.gameDAO = new GameDAO(em);
        this.genreDAO = new GenreDAO(em);
        this.devDAO = new DeveloperDAO(em);
        this.pubDAO = new PublisherDAO(em);
    }

}
