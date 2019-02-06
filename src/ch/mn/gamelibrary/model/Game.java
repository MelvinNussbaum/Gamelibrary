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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.mn.gamelibrary.Main;

@Entity
@Table(schema = Main.DB_SCHEMA)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private Developer developer;

    private Publisher publisher;

    private float price;

    private int metaScore;

    private int unitsSold;

    public Game() {
        super();
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
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

    public float getPrice() {

        return price;
    }

    public void setPrice(float price) {

        this.price = price;
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

}
