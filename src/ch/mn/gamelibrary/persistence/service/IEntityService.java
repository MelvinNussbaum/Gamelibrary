/******************************************************************************
 *
 * [ IDBService.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.persistence.service;

import java.util.List;

import ch.mn.gamelibrary.model.DBEntity;

public interface IEntityService<T extends DBEntity> {

    void persist(T entity);

    void varPersist(T... entities);

    T read(T entity);

    T findByName(String name) throws Exception;

    void delete(T entity);

    void deleteAll();

    List<T> readAll();

    void printEntities();

    void createEntityManagerAndDAO();

}
