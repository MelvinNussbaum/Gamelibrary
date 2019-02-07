/******************************************************************************
 *
 * [ DBObject.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DBObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public DBObject() {
        super();
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

}
