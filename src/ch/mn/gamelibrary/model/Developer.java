/******************************************************************************
 *
 * [ Developer.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String ceo;

    private String hq;

    public Developer() {
        super();
    }

    public Developer(String name, String ceo, String hq) {
        super();
        this.name = name;
        this.ceo = ceo;
        this.hq = hq;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

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

}
