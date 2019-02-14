/******************************************************************************
 *
 * [ IDAO.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.DAO;

public interface IDAO<T, K> {

    void persist(T dbObject);

    T retrieve(K primaryKey);

    void update(T dbObject);

    void delete(T dbObject);

}
