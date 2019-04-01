/******************************************************************************
 *
 * [ NewProducerController.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import ch.mn.gamelibrary.model.Producer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class NewProducerController<T extends Producer> {

    private Class<T> entityClass;

    private T newProducer;

    private boolean nameValidated;

    private boolean hqValidated;

    private boolean ceoValidated;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField headquartersTF;

    @FXML
    private TextField ceoTF;

    public NewProducerController() throws InstantiationException, IllegalAccessException {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
        this.newProducer = entityClass.newInstance();
    }

    @FXML
    private void initialize() {

    }

    public T openDialog() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/NewProducerDialog.fxml"));
        fxmlLoader.setController(this);
        VBox dialogGrid = fxmlLoader.load();

        Dialog<T> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setTitle("Add New " + entityClass.getSimpleName());
        dialog.getDialogPane().setContent(dialogGrid);

        ButtonType addPublisherButtonType = new ButtonType("Add " + entityClass.getSimpleName(), ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addPublisherButtonType, ButtonType.CANCEL);

        Node confirmButton = dialog.getDialogPane().lookupButton(addPublisherButtonType);
        confirmButton.setDisable(true);

        validate(confirmButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addPublisherButtonType) {

                newProducer.setName(nameTF.getText());
                newProducer.setHq(headquartersTF.getText());
                newProducer.setCeo(ceoTF.getText());
                return newProducer;
            }
            return null;
        });

        Optional<T> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    private void validate(Node confirmButton) {

        BooleanProperty validationChanged = new SimpleBooleanProperty(false);
        nameValidated = false;
        hqValidated = false;
        ceoValidated = false;

        nameTF.textProperty().addListener((observable, oldValue, newValue) -> {
            nameValidated = !newValue.trim().isEmpty();
            validationChanged.set(true);
        });

        headquartersTF.textProperty().addListener((observable, oldValue, newValue) -> {
            hqValidated = !newValue.trim().isEmpty();
            validationChanged.set(true);
        });

        ceoTF.textProperty().addListener((observable, oldValue, newValue) -> {
            ceoValidated = !newValue.trim().isEmpty();
            validationChanged.set(true);
        });

        validationChanged.addListener((observable, oldValue, newVal) -> {
            confirmButton.setDisable(!(nameValidated && hqValidated && ceoValidated));
            validationChanged.set(false);
        });
    }

}