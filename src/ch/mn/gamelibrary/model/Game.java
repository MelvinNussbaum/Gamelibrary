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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Game extends DBEntity implements Serializable {

    @ManyToMany(targetEntity = Genre.class, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    })
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne(cascade = {
        CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    })
    private Developer developer;

    @ManyToOne(cascade = {
        CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    })
    private Publisher publisher;

    private int metaScore;

    private int unitsSold;

    private byte[] cover;

    public Game() {

    }

    public Game(String name, Developer developer, Publisher publisher, int metaScore, int unitsSold, byte[] cover,
        Genre... genres) {
        super(name);
        this.metaScore = metaScore;
        this.unitsSold = unitsSold;
        this.cover = cover;
        setDeveloper(developer);
        setPublisher(publisher);
        setGenres(genres);
    }

    public void addGenre(Genre genre) {

        this.genres.add(genre);
        genre.getGames().add(this);
    }

    public void removeGenre(Genre genre) {

        this.genres.remove(genre);
        genre.getGames().remove(this);
    }

    public void removeAllGenres() {

        for (Genre genre : new ArrayList<>(genres)) {
            removeGenre(genre);
        }
    }

    public Set<Genre> getGenres() {

        return genres;
    }

    public void setGenres(Set<Genre> genres) {

        this.genres = genres;
    }

    public void setGenres(Genre[] genres) {

        for (Genre genre : genres) {
            addGenre(genre);
        }
    }

    public Developer getDeveloper() {

        return developer;
    }

    public void setDeveloper(Developer developer) {

        developer.getDevelopedGames().add(this);
        this.developer = developer;
    }

    public Publisher getPublisher() {

        return publisher;
    }

    public void setPublisher(Publisher publisher) {

        publisher.getPublishedGames().add(this);
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

    public byte[] getCover() {

        return cover;
    }

    public void setCover(byte[] cover) {

        this.cover = cover;
    }

    @Override
    public String toString() {

        String toString;
        StringBuilder genreBuilder = new StringBuilder();
        for (Genre genre : genres) {
            if (genreBuilder.length() != 0) {
                genreBuilder.append(", ");
            }
            genreBuilder.append(genre.getName());
        }

        toString = "\n" + name + "\n------------------------------\nGenres: " + genreBuilder.toString()
            + "\nDeveloper: " + developer.getName() + "\nPublisher: " + publisher.getName() + "\nMetaScore: "
            + metaScore + "/100\nUnits sold: " + unitsSold;

        return toString;
    }

    @Override
    public int hashCode() {

        return (int) (name.hashCode()
            //  + genres.hashCode()
            //  + developer.hashCode()
            //  + publisher.hashCode()
            + metaScore + unitsSold + id);
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
