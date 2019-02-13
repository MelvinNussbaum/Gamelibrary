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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Publisher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

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

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
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
    public int hashCode() {

        return (int) (name.hashCode() + id);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Publisher))
            return false;
        Publisher publisher = (Publisher) obj;
        return publisher.id == this.id && publisher.name.equals(name);
    }
}
