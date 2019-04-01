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
public class Developer extends Producer implements Serializable {

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private Set<Game> developedGames = new HashSet<>();

    public Developer() {

    }

    public Developer(String name, String ceo, String hq) {
        super(name, ceo, hq);
    }

    public Set<Game> getDevelopedGames() {

        return developedGames;
    }

    public void setDevelopedGames(Set<Game> developedGames) {

        this.developedGames = developedGames;
    }

    @Override
    public int hashCode() {

        return super.hashCode() + developedGames.hashCode();
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
