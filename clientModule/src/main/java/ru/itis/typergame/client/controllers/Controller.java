package ru.itis.typergame.client.controllers;

import javafx.scene.control.RadioButton;
import ru.itis.typergame.client.logic.Client;
import ru.itis.typergame.client.message.MessageCreater;
import ru.itis.typergame.client.visualizer.ISceneChanger;
import ru.itis.typergame.client.visualizer.ThemeContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Data
public class Controller implements Initializable {

    @FXML
    private Text opponentRow;
    @FXML
    private VBox body;
    @FXML
    TextField roomCode;
    @Setter
    private Client client;
    private String theme;
    @Setter
    private Scene gameScene;
    private Scene recordScene;
    private Scene settingsScene;
    private Message message;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void createRoom(MouseEvent mouseEvent){

        log.info("Комнта создана");
        try {
            Message message = MessageCreater.createRoomCreateMsg();
            client.sendMessage(message);


            ISceneChanger.changeScene(gameScene,mouseEvent);

            (gameScene.getRoot().lookup("#publicity")).setVisible(true);
            (gameScene.getRoot().lookup("#hardGameMode")).setVisible(true);
            (gameScene.getRoot().lookup("#demoGame")).setVisible(true);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void sendConnect(MouseEvent mouseEvent) {
        log.info("connect sended");
        try {

            Message message = MessageCreater.createRoomConnectMsg(roomCode.getText());
            log.info("message text {}",roomCode.getText());

            client.sendMessage(message);

            ISceneChanger.changeScene(gameScene,mouseEvent);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void getRecordsTable(MouseEvent mouseEvent) {
        message =MessageCreater.createGetRecordsMsg();
        client.sendMessage(message);
        ISceneChanger.changeScene(recordScene,mouseEvent);


    }

    public void getSettingsScene(MouseEvent mouseEvent) {
        Stage primaryStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        ThemeContext.checkTheme(settingsScene);


        primaryStage.setScene(settingsScene);
        settingsScene.getRoot().requestFocus();
    }

    public void playRandom(MouseEvent mouseEvent) {
        client.sendMessage(MessageCreater.createConnectRandomMsg());

        ISceneChanger.changeScene(gameScene,mouseEvent);

    }

}
