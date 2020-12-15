package ru.itis.typergame.client.controllers;

import lombok.Getter;
import ru.itis.typergame.client.visualizer.ISceneChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class WinController implements Initializable {
    @FXML
    public ImageView imageView;
    @FXML
    @Getter
    private VBox body;
    @Setter
    private Scene scene;


    public void goToMenu(MouseEvent mouseEvent) {

        ISceneChanger.changeScene(scene,mouseEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info(imageView.toString());
        Image i = new Image(new File("src/main/resources/assets/SdRockFact.gif").toURI().toString());
        imageView.setImage(i);

    }

}
