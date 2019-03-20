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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.persistence.service.GameService;
import ch.mn.gamelibrary.utils.NumberFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class MainViewController implements PropertyChangeListener {

    private GameService service = new GameService();

    private GamePanelController panelController = new GamePanelController();

    private AddGameDialogController addGameController = new AddGameDialogController();

    @FXML
    private BorderPane parent;

    @FXML
    private MenuItem addGameMenuItem;

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
            gamesContainer.getChildren().add(panelController.createPanel(game));
        }
        gamesContainer.prefWrapLengthProperty().bind(scrollPane.widthProperty());
        panelController.addPropertyChangeListener(this);
    }

    public void fillDetailContainer(String gameTitle) {

        Game game = null;
        try {
            game = service.findByName(gameTitle);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
        }

        StringBuilder genreString = new StringBuilder();
        for (Genre genre : game.getGenres()) {
            if (genreString.length() != 0) {
                genreString.append(", ");
            }
            genreString.append(genre.toString());
        }

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(game.getCover());
            detailImageView.setImage(new Image(bis));
        } catch (NullPointerException e) {
            detailImageView.setImage(new Image("assets/img/placeholder.png"));
        }
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

    @FXML
    private void handleAddGameMenuItem() {

        try {
            Game game = addGameController.openDialog();
            if (game != null) {
                service.persist(game);
                gamesContainer.getChildren().add(panelController.createPanel(game));
            }
        } catch (IOException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        fillDetailContainer((String) evt.getNewValue());
    }
}
