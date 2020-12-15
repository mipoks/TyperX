package ru.itis.typergame.client.visualizer;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public interface ISceneChanger {
    public static void changeScene(Scene scene, InputEvent inputEvent){

        Stage primaryStage = (Stage)((Node)inputEvent.getSource()).getScene().getWindow();
        ThemeContext.checkTheme(scene);
        primaryStage.setScene(scene);
        scene.getRoot().requestFocus();
    };
}
