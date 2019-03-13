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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Developer extends DBEntity implements Serializable {

    private String ceo;

    private String hq;

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private Set<Game> developedGames;

    public Developer() {

    }

    public Developer(String name, String ceo, String hq) {
        super(name);
        this.ceo = ceo;
        this.hq = hq;
        this.developedGames = new HashSet<>();
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

    public Set<Game> getDevelopedGames() {

        return developedGames;
    }

    public void setDevelopedGames(Set<Game> developedGames) {

        this.developedGames = developedGames;
    }

    @Override
    public String toString() {

        String toString;
        toString = "\n" + name + "\n------------------------------\nCEO: " + ceo + "\nHeadquarters: " + hq;
        return toString;
    }

    @Override
    public int hashCode() {

        return (int) (name.hashCode() + ceo.hashCode() + hq.hashCode() + developedGames.hashCode() + id);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (!(obj instanceof Developer))
            return false;
        return this.hashCode() == obj.hashCode();
    }
}
