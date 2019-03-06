/******************************************************************************
 *
 * [ DeveloperService.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.persistence.dao.DeveloperDAO;

public class DeveloperService extends EntityService<Developer> {

    @Override
    public void printEntities() {

        System.out.println("\n------ ALL DEVELOPERS ------" + "\n------------------------------");
        for (Developer dev : this.readAll()) {
            System.out.println(dev);
        }
        System.out.println();
    }

    @Override
    public void createEntityManagerAndDAO() {

        this.em = factory.createEntityManager();
        this.dao = new DeveloperDAO(em);
    }

}
