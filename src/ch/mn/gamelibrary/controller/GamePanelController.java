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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import ch.mn.gamelibrary.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class GamePanelController {

    private Game game;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

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

    public VBox createPanel(Game game) {

        this.game = game;
        VBox gamePanel = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/component/GamePanel.fxml"));
            fxmlLoader.setController(this);
            gamePanel = fxmlLoader.load();
            loadModelIntoView();
        } catch (IOException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }

        return gamePanel;
    }

    private void loadModelIntoView() {

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(game.getCover());
            imageView.setImage(new Image(bis));
        } catch (NullPointerException e) {
            imageView.setImage(new Image("assets/img/placeholder.png"));
        }

        title.setText(game.getName());
        publisher.setText(game.getPublisher().getName());
        developer.setText(game.getDeveloper().getName());

    }

    @FXML
    private void handlePanelClicked(MouseEvent event) {

        VBox source = (VBox) event.getSource();
        support.firePropertyChange("gameTitle", "", ((Label) source.getChildren().get(1)).getText());
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {

        support.removePropertyChangeListener(pcl);
    }

    public Game getGame() {

        return game;
    }

    public void setGame(Game game) {

        this.game = game;
    }
}
