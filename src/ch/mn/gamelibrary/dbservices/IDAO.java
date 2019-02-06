/******************************************************************************
 *
 * [ IDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.dbservices;

import ch.mn.gamelibrary.model.DBObject;

public interface IDAO<T extends DBObject> {

    void create();

    T read(T dbObject);

    void update(T dbObject);

    void delete(T dbObject);

    T[] readAll();
}
