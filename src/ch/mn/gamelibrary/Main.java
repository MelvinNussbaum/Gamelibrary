/******************************************************************************
 *
 * [ Main.java ]
 *
 * COPYRIGHT (c) 2002 - 2019 by Allianz-Suisse, Zürich, Switzerland.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Allianz-Suisse.
 *
 ******************************************************************************/
package ch.mn.gamelibrary;

import java.io.IOException;

import ch.mn.gamelibrary.controller.MainViewController;
import ch.mn.gamelibrary.persistence.service.DBService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String DB_SCHEMA = "Gamelibrary";

    public static final String PERSISTENCE_UNIT_ECLIPSELINK = "PU_Eclipselink";

    public static final String PERSISTENCE_UNIT_HIBERNATE = "PU_Hibernate";

    public static void main(String[] args) {

        DBService service = new DBService();

        service.fillDatabaseIfEmpty();
        service.printAllEntities();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));

        Parent root;
        MainViewController controller;
        try {
            root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.show();
    }
}
