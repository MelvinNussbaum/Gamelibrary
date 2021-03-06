/******************************************************************************
 *
 * [ AddGameDialogController.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import ch.mn.gamelibrary.model.Developer;
import ch.mn.gamelibrary.model.Game;
import ch.mn.gamelibrary.model.Genre;
import ch.mn.gamelibrary.model.Publisher;
import ch.mn.gamelibrary.persistence.service.DeveloperService;
import ch.mn.gamelibrary.persistence.service.GenreService;
import ch.mn.gamelibrary.persistence.service.PublisherService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

public class AddGameDialogController {

    private Game newGame = new Game();

    private Dialog<Game> dialog;

    private ButtonType addGameButtonType;

    private Set<ToggleButton> buttonGroup = new HashSet<>();

    private PublisherService pubService = new PublisherService();

    private DeveloperService devService = new DeveloperService();

    private GenreService genreService = new GenreService();

    private boolean titleValidated;

    private boolean pubValidated;

    private boolean devValidated;

    private boolean unitsSoldValidated;

    private boolean metaScoreValidated;

    @FXML
    private TextField titleTF;

    @FXML
    private ComboBox<Publisher> publisherCB;

    @FXML
    private ComboBox<Developer> developerCB;

    @FXML
    private Button newPublisherBtn;

    @FXML
    private Button newDeveloperBtn;

    @FXML
    private TextField unitsSoldTF;

    @FXML
    private TextField metaScoreTF;

    @FXML
    private FlowPane genresPane;

    @FXML
    private Button removeFileBtn;

    @FXML
    private ImageView imagePreView;

    public AddGameDialogController() {
    }

    @FXML
    private void initialize() {

    }

    public Game openDialog() throws IOException {

        loadDialog();
        loadProducers();
        addDialogButtons();
        addGenreButtons();
        validate();

        return prepareResult();
    }

    private void loadDialog() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddGameDialog.fxml"));
        fxmlLoader.setController(this);
        VBox dialogGrid = fxmlLoader.load();

        this.dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setTitle("Add New Game");
        dialog.getDialogPane().setContent(dialogGrid);
    }

    private void loadProducers() {

        publisherCB.getItems().addAll(pubService.readAll());
        developerCB.getItems().addAll(devService.readAll());
    }

    private void addDialogButtons() {

        this.addGameButtonType = new ButtonType("Add Game", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(0, addGameButtonType);
        dialog.getDialogPane().getButtonTypes().add(1, ButtonType.CANCEL);

        Node confirmButton = dialog.getDialogPane().lookupButton(addGameButtonType);
        confirmButton.setDisable(true);
    }

    private void addGenreButtons() {

        for (Genre genre : genreService.readAll()) {
            ToggleButton but = new ToggleButton(genre.getName());
            buttonGroup.add(but);
            genresPane.getChildren().add(but);
        }
    }

    private Game prepareResult() {

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == this.addGameButtonType) {

                newGame.setName(titleTF.getText());
                newGame.setPublisher(publisherCB.getValue());
                newGame.setDeveloper(developerCB.getValue());
                newGame.setUnitsSold(Integer.parseInt(unitsSoldTF.getText()));
                newGame.setMetaScore(Integer.parseInt(metaScoreTF.getText()));
                newGame.setGenres(extractGenres());
                return newGame;
            }
            return null;
        });

        Optional<Game> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    private Genre[] extractGenres() {

        List<Genre> genreList = new ArrayList<>();
        for (ToggleButton toggleButton : buttonGroup) {
            if (toggleButton.isSelected()) {
                genreList.add(genreService.findByName(toggleButton.getText()));
            }
        }
        return genreList.toArray(new Genre[genreList.size() - 1]);
    }

    private void validate() {

        BooleanProperty validationChanged = new SimpleBooleanProperty(false);
        titleValidated = false;
        pubValidated = false;
        devValidated = false;
        unitsSoldValidated = false;
        metaScoreValidated = false;

        titleTF.textProperty().addListener((observable, oldValue, newValue) -> {
            titleValidated = !newValue.trim().isEmpty();
            validationChanged.set(true);
        });

        unitsSoldTF.textProperty().addListener((observable, oldValue, newValue) -> {
            unitsSoldValidated = !newValue.trim().isEmpty() && newValue.chars().allMatch(Character::isDigit);
            validationChanged.set(true);
        });

        metaScoreTF.textProperty().addListener((observable, oldValue, newValue) -> {
            metaScoreValidated = !newValue.trim().isEmpty() && newValue.matches("^[1-9][0-9]?$|^100$");
            validationChanged.set(true);
        });

        titleTF.textProperty().addListener((observable, oldValue, newValue) -> {
            titleValidated = !newValue.trim().isEmpty();
            validationChanged.set(true);
        });

        publisherCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            pubValidated = !newValue.toString().isEmpty();
            validationChanged.set(true);
        });

        developerCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            devValidated = !newValue.toString().isEmpty();
            validationChanged.set(true);
        });

        validationChanged.addListener((observable, oldValue, newVal) -> {
            this.dialog.getDialogPane().lookupButton(this.addGameButtonType).setDisable(
                !(titleValidated && pubValidated && devValidated && unitsSoldValidated && metaScoreValidated));
            validationChanged.set(false);
        });
    }

    @FXML
    private void handleNewPublisher(ActionEvent ea) {

        NewPublisherController pController;
        try {
            pController = new NewPublisherController();
            Publisher newPublisher = pController.openDialog();
            if (newPublisher != null) {
                publisherCB.getItems().add(newPublisher);
                publisherCB.setValue(newPublisher);
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            e.printStackTrace();
        }

    }

    @FXML
    private void handleNewDeveloper(ActionEvent ea) {

        try {
            NewDeveloperController dController = new NewDeveloperController();
            Developer newDeveloper = dController.openDialog();
            if (newDeveloper != null) {
                developerCB.getItems().add(newDeveloper);
                developerCB.setValue(newDeveloper);
            }
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChooseFile(ActionEvent ae) {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node) ae.getSource()).getScene().getWindow());
        if (file != null) {
            try {
                BufferedImage bCover = ImageIO.read(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bCover, "jpg", bos);
                byte[] cover = bos.toByteArray();
                bos.close();
                newGame.setCover(cover);

                imagePreView.setImage(new Image(file.toURI().toString()));
                removeFileBtn.setDisable(false);

            } catch (IOException e) {
                new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleRemoveFile(ActionEvent ae) {

        newGame.setCover(null);
        imagePreView.setImage(new Image("assets/img/placeholder.png"));
        removeFileBtn.setDisable(true);
    }
}
