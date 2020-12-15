package ru.itis.typergame.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import ru.itis.typergame.client.logic.Client;
import ru.itis.typergame.client.message.MessageCreater;
import ru.itis.typergame.client.visualizer.ISceneChanger;
import ru.itis.typergame.client.visualizer.ThemeContext;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsController implements Initializable {
    @FXML
    private TextField newName;
    @FXML
    @Getter
    private VBox body;
    @Setter
    private Scene mainScene;
    @Setter
    private Client client;
    private Pattern pattern;
    private Matcher matcher;

    public void changeTheme(MouseEvent mouseEvent) {
        if (ThemeContext.currentTheme.equals(ThemeContext.DEFAULT_THEME)) {
            ThemeContext.currentTheme =ThemeContext.DARK_THEME;

            body.setStyle(ThemeContext.DARK_THEME);
        }else {
            ThemeContext.currentTheme =ThemeContext.DEFAULT_THEME;
            body.setStyle(ThemeContext.DEFAULT_THEME);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void getMainScene(MouseEvent mouseEvent) {

        ISceneChanger.changeScene(mainScene,mouseEvent);
    }

    public void saveName(MouseEvent mouseEvent) {
        if(newName.getText().matches("^(?=.{1,15}$)[a-zA-Z][a-zA-Z0-9]*(?: [a-zA-Z0-9]+)*$")){
            client.sendMessage(MessageCreater.createNickNameChangeMsg(newName.getText()));

        }else {newName.setText("Попробуйте другое имя");}

    }
}
