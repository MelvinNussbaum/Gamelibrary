/******************************************************************************
 *
 * [ Genre.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Genre extends DBEntity implements Serializable {

    private String name;

    public Genre() {

    }

    public Genre(String name) {
        super();
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String toString;
        toString = "\n" + name;
        return toString;
    }

    @Override
    public int hashCode() {

        return (int) (name.hashCode() + id);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (!(obj instanceof Genre))
            return false;
        return this.hashCode() == obj.hashCode();
    }

}
