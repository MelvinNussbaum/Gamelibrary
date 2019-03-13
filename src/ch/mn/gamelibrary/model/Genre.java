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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Genre extends DBEntity implements Serializable {

    @ManyToMany(targetEntity = Game.class, mappedBy = "genres")
    private Set<Game> games;

    public Genre() {

    }

    public Genre(String name) {
        super(name);
        this.games = new HashSet<>();
    }

    public Set<Game> getGames() {

        return games;
    }

    public void setGames(Set<Game> games) {

        this.games = games;
    }

    @Override
    public String toString() {

        String toString;
        toString = name;
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
