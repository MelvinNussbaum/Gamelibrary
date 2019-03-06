/******************************************************************************
 *
 * [ IDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.dao;

import ch.mn.gamelibrary.model.DBEntity;

public interface IDAO<T extends DBEntity> {

    void persist(T dbObject);

    T retrieve(T dbObject);

    void update(T dbObject);

    void delete(T dbObject);

}
