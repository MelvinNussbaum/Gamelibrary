/******************************************************************************
 *
 * [ GenreService.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.persistence.dao.GenreDAO;

public class GenreService extends EntityService<Genre> {

    @Override
    public void printEntities() {

        System.out.println("\n------ ALL GENRES ------" + "\n------------------------------");
        for (Genre genre : this.readAll()) {
            System.out.println(genre);
        }
        System.out.println();
    }

    @Override
    public void createEntityManagerAndDAO() {

        this.em = factory.createEntityManager();
        this.dao = new GenreDAO(em);
    }
}
