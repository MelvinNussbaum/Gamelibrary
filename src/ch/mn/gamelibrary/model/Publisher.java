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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Publisher extends Producer implements Serializable {

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Game> publishedGames = new HashSet<>();

    public Publisher() {

    }

    public Publisher(String name, String ceo, String hq) {
        super(name, ceo, hq);
    }

    public Set<Game> getPublishedGames() {

        return publishedGames;
    }

    public void setPublishedGames(Set<Game> publishedGames) {

        this.publishedGames = publishedGames;
    }

    @Override
    public int hashCode() {

        return super.hashCode() + publishedGames.hashCode();
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
