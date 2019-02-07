/******************************************************************************
 *
 * [ PublisherDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.dbservices;

import javax.persistence.EntityManager;

import ch.mn.gamelibrary.model.Publisher;

public class PublisherDAO extends AbstractDAO<Publisher> {

    @Override
    public void create() {

        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(new Publisher("name", "ceo", "hq"));

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Publisher read(Publisher publisher) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Publisher publisher) {

        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Publisher publisher) {

        // TODO Auto-generated method stub

    }

    @Override
    public Publisher[] readAll() {

        // TODO Auto-generated method stub
        return null;
    }

}
