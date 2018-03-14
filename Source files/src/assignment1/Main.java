/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Thomas Pedraza
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("assign1Scene.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("assign2Scene.fxml"));
        Parent root3 = FXMLLoader.load(getClass().getResource("assign3Scene.fxml"));

        BorderPane bp = new BorderPane();
        TabPane tp = new TabPane();
        Tab assign1 = new Tab("Assignment 1");
        Tab assign2 = new Tab("Assignment 2");
        Tab assign3 = new Tab("Assignment 3");
        assign1.setClosable(false);
        assign2.setClosable(false);
        assign3.setClosable(false);

        tp.getTabs().addAll(assign1, assign2, assign3);
        bp.setTop(tp);

        assign1.setContent(root1);
        assign2.setContent(root2);
        assign3.setContent(root3);

        stage.setTitle("Network Simulator - Thomas Pedraza");
        stage.setScene(new Scene(bp));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
