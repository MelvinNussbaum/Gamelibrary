/******************************************************************************
 *
 * [ PublisherService.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.dao.PublisherDAO;

public class PublisherService extends EntityService<Publisher> {

    @Override
    public void printEntities() {

        System.out.println("\n------ ALL PUBLISHERS ------" + "\n------------------------------");
        for (Publisher pub : this.readAll()) {
            System.out.println(pub);
        }
        System.out.println();
    }

    @Override
    public void createEntityManagerAndDAO() {

        this.em = factory.createEntityManager();
        this.dao = new PublisherDAO(em);

    }

}
