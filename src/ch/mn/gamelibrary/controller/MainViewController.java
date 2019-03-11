/******************************************************************************
 *
 * [ MainViewController.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.controller;

import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.persistence.service.GameService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainViewController {

    private GameService service = new GameService();

    @FXML
    private BorderPane parent;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane gamesContainer;

    public MainViewController() {
    }

    @FXML
    private void initialize() {

        scrollPane.widthProperty()
            .addListener((obs, newVal, oldVal) -> gamesContainer.setPrefWrapLength((double) newVal));

        for (Game game : service.readAll()) {
            createGamePanel(game);
        }
    }

    private void createGamePanel(Game game) {

        VBox gamePanel = new VBox();
        gamePanel.setAlignment(Pos.TOP_CENTER);
        gamePanel.setMaxWidth(120);

        Image image = new Image(getClass().getResourceAsStream("/assets/img/Video_Game_Cover_The_Last_of_Us.jpg"));
        ImageView imgView = new ImageView(image);
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(150);

        Label title = new Label(game.getTitle());
        Label pub = new Label(game.getPublisher().getName());
        Label dev = new Label(game.getDeveloper().getName());
        title.setFont(new Font(14));
        pub.setFont(new Font(10));
        dev.setFont(new Font(10));

        gamePanel.getChildren().add(imgView);
        gamePanel.getChildren().add(title);
        gamePanel.getChildren().add(pub);
        gamePanel.getChildren().add(dev);

        gamesContainer.getChildren().add(gamePanel);
    }

}
