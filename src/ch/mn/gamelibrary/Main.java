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

import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.service.DBService;

public class Main {

    public static final String DB_SCHEMA = "Gamelibrary";

    public static final String PERSISTENCE_UNIT_ECLIPSELINK = "PU_Eclipselink";

    public static final String PERSISTENCE_UNIT_HIBERNATE = "PU_Hibernate";

    public static void main(String[] args) {

        DBService controller = new DBService();

        controller.fillDatabaseIfEmpty();
        controller.printEntities(Game.class);
        controller.printEntities(Developer.class);
        controller.printEntities(Publisher.class);
        controller.printEntities(Genre.class);
    }

}
