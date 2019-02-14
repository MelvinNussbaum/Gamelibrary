/******************************************************************************
 *
 * [ Game.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Game extends DBEntity implements Serializable {

    private String title;

    @ManyToOne
    private Developer developer;

    @ManyToOne
    private Publisher publisher;

    private int metaScore;

    private int unitsSold;

    public Game() {

    }

    public Game(String title, Developer developer, Publisher publisher, int metaScore, int unitsSold) {
        super();

        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.metaScore = metaScore;
        this.unitsSold = unitsSold;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public Developer getDeveloper() {

        return developer;
    }

    public void setDeveloper(Developer developer) {

        this.developer = developer;
    }

    public Publisher getPublisher() {

        return publisher;
    }

    public void setPublisher(Publisher publisher) {

        this.publisher = publisher;
    }

    public int getMetaScore() {

        return metaScore;
    }

    public void setMetaScore(int metaScore) {

        this.metaScore = metaScore;
    }

    public int getUnitsSold() {

        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {

        this.unitsSold = unitsSold;
    }

    @Override
    public String toString() {

        String toString;
        toString = "\n" + title + "\n------------------------------\nDeveloper: " + developer.getName()
            + "\nPublisher: " + publisher.getName() + "\nMetaScore: " + metaScore + "/100\nUnits sold: " + unitsSold;

        return toString;
    }

    @Override
    public int hashCode() {

        return (int) (title.hashCode() + developer.hashCode() + publisher.hashCode() + metaScore + unitsSold + id);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (!(obj instanceof Game))
            return false;
        return hashCode() == obj.hashCode();
    }

}
