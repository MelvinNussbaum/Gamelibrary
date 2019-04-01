/******************************************************************************
 *
 * [ Producer.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Producer extends DBEntity implements Serializable {

    protected String ceo;

    protected String hq;

    public Producer() {
        super();
    }

    public Producer(String name, String ceo, String hq) {
        super(name);
        this.ceo = ceo;
        this.hq = hq;
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

        return this.name;
    }

    @Override
    public int hashCode() {

        return (int) (name.hashCode() + ceo.hashCode() + hq.hashCode() + id);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (!(obj instanceof Producer))
            return false;
        return this.hashCode() == obj.hashCode();
    }
}
