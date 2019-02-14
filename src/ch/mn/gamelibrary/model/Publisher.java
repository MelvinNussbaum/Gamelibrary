/******************************************************************************
 *
 * [ Publisher.java ]
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
public class Publisher extends DBEntity implements Serializable {

    private String name;

    private String ceo;

    private String hq;

    public Publisher() {

    }

    public Publisher(String name, String ceo, String hq) {
        super();
        this.name = name;
        this.ceo = ceo;
        this.hq = hq;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getCeo() {

        return ceo;
    }

    public void setCeo(String ceo) {

        this.ceo = ceo;
    }

    public String getHq() {

        return hq;
    }

    public void setHq(String hq) {

        this.hq = hq;
    }

    @Override
    public String toString() {

        String toString;
        toString = "\n" + name + "\n------------------------------\nCEO: " + ceo + "\nHeadquarters: " + hq;
        return toString;
    }

    @Override
    public int hashCode() {

        return (int) (name.hashCode() + ceo.hashCode() + hq.hashCode() + id);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (!(obj instanceof Publisher))
            return false;
        return this.hashCode() == obj.hashCode();
    }
}
