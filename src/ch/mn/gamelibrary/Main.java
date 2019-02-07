/******************************************************************************
 *
 * [ Main.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary;

import ch.mn.gamelibrary.dbservices.GameDAO;
import ch.mn.gamelibrary.dbservices.PublisherDAO;

public class Main {

    public static final String DB_SCHEMA = "Gamelibrary";

    public static final String PERSISTENCE_UNIT = "PU_Eclipselink";

    public static void main(String[] args) {

        GameDAO gameDAO = new GameDAO();
        PublisherDAO publisherDao = new PublisherDAO();
        gameDAO.create();
        publisherDao.create();
    }

}
