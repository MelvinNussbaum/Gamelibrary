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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.persistence.service.GameService;
import ch.mn.gamelibrary.utils.NumberFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class MainViewController {

    private GameService service = new GameService();

    private GamePanelController panelController = new GamePanelController(this);

    @FXML
    private BorderPane parent;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane gamesContainer;

    @FXML
    private VBox detailContainer;

    @FXML
    private ImageView detailImageView;

    @FXML
    private Label detailGameTitle;

    @FXML
    private Label detailGameGenres;

    @FXML
    private Label detailGameMetaScore;

    @FXML
    private Label detailGameUnitsSold;

    @FXML
    private Label detailDeveloperTitle;

    @FXML
    private Label detailDeveloperHQ;

    @FXML
    private Label detailDeveloperCEO;

    @FXML
    private Label detailPublisherTitle;

    @FXML
    private Label detailPublisherHQ;

    @FXML
    private Label detailPublisherCEO;

    public MainViewController() {
    }

    @FXML
    private void initialize() {

        for (Game game : service.readAll()) {
            createGamePanel(game);
        }

        gamesContainer.prefWrapLengthProperty().bind(scrollPane.widthProperty());
    }

    private void createGamePanel(Game game) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/component/GamePanel.fxml"));
            fxmlLoader.setController(panelController);
            VBox gamePanel = fxmlLoader.load();
            panelController.setGame(game);

            gamePanel.setOnMouseClicked(panelController);
            gamesContainer.getChildren().add(gamePanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillDetailContainer(String gameTitle) {

        Game game = null;
        try {
            game = service.findByName(gameTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder genreString = new StringBuilder();
        for (Genre genre : game.getGenres()) {
            if (genreString.length() != 0) {
                genreString.append(", ");
            }
            genreString.append(genre.toString());
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(game.getCover());
        detailImageView.setImage(new Image(bis));
        detailGameTitle.setText(game.getName());
        detailGameGenres.setText(genreString.toString());
        detailGameMetaScore.setText("MetaScore: " + game.getMetaScore() + "/100");
        detailGameUnitsSold.setText("Units sold: " + NumberFormat.format(game.getUnitsSold()));
        detailPublisherTitle.setText(game.getPublisher().getName());
        detailPublisherCEO.setText("CEO: " + game.getPublisher().getCeo());
        detailPublisherHQ.setText("Headquarters: " + game.getPublisher().getHq());
        detailDeveloperTitle.setText(game.getDeveloper().getName());
        detailDeveloperCEO.setText("CEO: " + game.getDeveloper().getCeo());
        detailDeveloperHQ.setText("Headquarters: " + game.getDeveloper().getHq());

        if (!detailContainer.isVisible()) {
            detailContainer.setVisible(true);
        }
    }
}
