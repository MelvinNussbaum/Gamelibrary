/******************************************************************************
 *
 * [ GamePanel.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.controller;

import java.io.ByteArrayInputStream;
import java.util.Observable;

import ch.mn.gamelibrary.model.Game;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class GamePanelController extends Observable implements EventHandler<MouseEvent> {

    private Game game;

    @FXML
    private ImageView imageView;

    @FXML
    private Label title;

    @FXML
    private Label publisher;

    @FXML
    private Label developer;

    public GamePanelController() {

    }

    @FXML
    private void initialize() {

    }

    private void loadModelIntoView() {

        ByteArrayInputStream bis = new ByteArrayInputStream(game.getCover());
        imageView.setImage(new Image(bis));

        title.setText(game.getName());
        publisher.setText(game.getPublisher().getName());
        developer.setText(game.getDeveloper().getName());

    }

    @Override
    public void handle(MouseEvent event) {

        VBox source = (VBox) event.getSource();
        setChanged();
        notifyObservers(((Label) source.getChildren().get(1)).getText());
    }

    public Game getGame() {

        return game;
    }

    public void setGame(Game game) {

        this.game = game;
        loadModelIntoView();
    }

}
