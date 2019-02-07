/******************************************************************************
 *
 * [ AbstractDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.dbservices;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ch.mn.gamelibrary.Main;
import ch.mn.gamelibrary.model.DBObject;

public abstract class AbstractDAO<T extends DBObject> implements IDAO<T> {

    protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT);

    public EntityManagerFactory getEntityManagerFactory() {

        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {

        this.entityManagerFactory = entityManagerFactory;
    }
}
