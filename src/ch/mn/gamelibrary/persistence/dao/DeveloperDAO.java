/******************************************************************************
 *
 * [ DeveloperDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.dao;

import javax.persistence.EntityManager;

import ch.mn.gamelibrary.model.Developer;

public class DeveloperDAO extends AbstractDAO<Developer> {

    public DeveloperDAO(EntityManager em) {
        super(em);
        // TODO Auto-generated constructor stub
    }

}
