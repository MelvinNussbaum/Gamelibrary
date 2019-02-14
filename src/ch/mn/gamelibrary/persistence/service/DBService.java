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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

import ch.mn.gamelibrary.Main;
import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.DAO.DeveloperDAO;
import ch.mn.gamelibrary.persistence.DAO.GameDAO;
import ch.mn.gamelibrary.persistence.DAO.PublisherDAO;

public class DBService {

    private EntityManagerFactory factory;

    EntityManager em;

    private GameDAO gameDAO;

    private DeveloperDAO devDAO;

    private PublisherDAO pubDAO;

    public DBService() {
        super();
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_ECLIPSELINK);
    }

    public void fillDatabaseIfEmpty() {

        createEntityManagerAndDAOs();

        if (readAll(Game.class).isEmpty() || readAll(Developer.class).isEmpty() || readAll(Publisher.class).isEmpty()) {

            Developer naughtyDogDev = new Developer("Naughty Dog", "Evan Wells",
                "Santa Monica, Kalifornien, Vereinigte Staaten");
            Developer rockstarDev = new Developer("Rockstar North", "Andrew Semple", "Edinburgh, Schottland");
            Developer eaCanDev = new Developer("EA Vancouver", "Don Mattrick (Founder)",
                "Burnaby, British Columbia Kanada");

            Publisher eaPub = new Publisher("Electronic Arts", "Andrew Wilson",
                "Redwood City, Kalifornien, Vereinigte Staaten");
            Publisher sonyPub = new Publisher("Sony Interactive Entertainment", "John Kodera, Shawn Layden, Jim Ryan",
                "San Mateo, Kalifornien, Vereinigte Staaten");
            Publisher rockstarPub = new Publisher("Rockstar Games", "Dan & Sam Houser",
                "New York City, New York, Vereinigte Staaten");

            Game tlouGame = new Game("The Last of Us", naughtyDogDev, sonyPub, 95, 17000000);
            Game uncharted4Game = new Game("Uncharted 4: A Thief’s End", naughtyDogDev, sonyPub, 93, 8700000);
            Game gtaVGame = new Game("Grand Theft Auto V", rockstarDev, rockstarPub, 97, 100000000);
            Game fifa19 = new Game("FIFA 19", eaCanDev, eaPub, 83, 5000000);

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
        deleteFromTable(Developer.class);
        deleteFromTable(Publisher.class);
    }

    public <T extends Object> void deleteFromTable(Class<T> c) {

        createEntityManagerAndDAOs();

        em.getTransaction().begin();
        CriteriaDelete<T> cd = em.getCriteriaBuilder().createCriteriaDelete(c);
        cd.from(c);
        em.createQuery(cd).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public <T extends Object> List<T> readAll(Class<T> c) {

        createEntityManagerAndDAOs();

        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(c);
        CriteriaQuery<T> getAll = cq.select(cq.from(c));
        return em.createQuery(getAll).getResultList();
    }

    public void printAllGames() {

        createEntityManagerAndDAOs();

        System.out.println("\n--------- ALL GAMES ----------" + "\n------------------------------");
        for (Game game : readAll(Game.class)) {
            System.out.println(game);
        }
        System.out.println();

        em.close();
    }

    public void printAllDevelopers() {

        createEntityManagerAndDAOs();

        System.out.println("\n------- ALL DEVELOPERS -------" + "\n------------------------------");
        for (Developer dev : readAll(Developer.class)) {
            System.out.println(dev);
        }
        System.out.println();

        em.close();
    }

    public void printAllPublisher() {

        createEntityManagerAndDAOs();

        System.out.println("\n------- ALL PUBLISHER --------" + "\n------------------------------");
        for (Publisher pub : readAll(Publisher.class)) {
            System.out.println(pub);
        }
        System.out.println();

        em.close();
    }

    private void createEntityManagerAndDAOs() {

        this.em = factory.createEntityManager();
        this.gameDAO = new GameDAO(em);
        this.devDAO = new DeveloperDAO(em);
        this.pubDAO = new PublisherDAO(em);
    }

    public EntityManager getEm() {

        return em;
    }

    public void setEm(EntityManager em) {

        this.em = em;
        this.gameDAO = new GameDAO(em);
        this.devDAO = new DeveloperDAO(em);
        this.pubDAO = new PublisherDAO(em);
    }

}
