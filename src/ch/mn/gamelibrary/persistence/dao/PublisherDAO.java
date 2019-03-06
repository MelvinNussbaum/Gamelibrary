/******************************************************************************
 *
 * [ PublisherDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.dao;

import javax.persistence.EntityManager;

import ch.mn.gamelibrary.model.Publisher;

public class PublisherDAO extends AbstractDAO<Publisher> {

    public PublisherDAO(EntityManager em) {
        super(em);
        // TODO Auto-generated constructor stub
    }

}
