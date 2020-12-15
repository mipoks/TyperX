package ru.itis.typergame.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.client.logic.Client;
import ru.itis.typergame.client.message.MessageCreater;
import ru.itis.typergame.client.visualizer.ThemeContext;
import ru.itis.typergame.protocol.Message;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Data
public class MainGameController implements Initializable {
    @FXML
    private RadioButton hardGameMode;
    @FXML
    private RadioButton publicity;
    @FXML
    private Button demoGame;
    @FXML
    private Label roomCode;
    @FXML
    private Text gamerTwoName;
    @FXML
    private Text gamerOneName;
    @FXML
    private Text gamerThreeName;
    @FXML
    private Text gamerFourName;
    @FXML
    private Text tappedChar;
    @FXML
    private VBox body;
    private String theme;
    private int n;
    private Text utillText;
    private String[] textArray;
    @FXML
    private TextFlow gameScreen;
    private Client client;
    private List<Text> textList = new ArrayList<>();
    private Scene winScene;
    private Scene lostScene;


    @FXML
    public void prepare(String text) {
        n = 0;
        body.setStyle(ThemeContext.DEFAULT_THEME);
        if (ThemeContext.currentTheme.equals(ThemeContext.DARK_THEME)) {
            dupplMapper(text, "-fx-stroke: #ffffff");
        } else {
            dupplMapper(text, "-fx-stroke: #353535");
        }

    }

    private void dupplMapper(String text, String style) {
        log.info("Удалено букв {}", gameScreen.getChildren().size());
        gameScreen.getChildren().remove(0, gameScreen.getChildren().size());
        textArray = (text + " ").split("");
        n = 0;
        Arrays.stream(textArray).forEach(x -> {
            utillText = new Text(x);
            utillText.setFont(Font.font(40));
            utillText.setFill(Paint.valueOf("778899"));

            utillText.setStyle(style);
            textList.add(utillText);
            gameScreen.getChildren().add(utillText);
        });
        System.out.println(Arrays.toString(textArray));

        log.info("Vbox body :{}", body);
        demoGame.setVisible(false);
    }


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        publicity.setVisible(false);
        hardGameMode.setVisible(false);
        demoGame.setVisible(false);

    }

    public void checkSensor(MouseEvent mouseEvent) {
        log.info("Sensor mouse checked");
    }

    public void handleTypedCode(KeyEvent keyEvent) {
        try {

            log.info("Code typed");
            System.out.println("code");

            log.info("Taked code{}", keyEvent.getCharacter());


            if (keyEvent.getCharacter().toLowerCase().equals(getTextArray()[n].toLowerCase()) ||
                    getTextArray()[n].equals(" ") && keyEvent.getCharacter().toLowerCase().equals("space")) {

                n++;
                Message message = MessageCreater.createPlayGameMsg(keyEvent.getCharacter());


                try {
                    client.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("Совпадение символа {}", n);
            }
//
            if (n == getTextArray().length - 1) {
                if (((Text) getGameScreen().getChildren().get(n)).getFill().toString().startsWith("778899", 2)) {
                    endgame(keyEvent, winScene);
                } else {
                    endgame(keyEvent, lostScene);
                }


            }
            ((Text) getGameScreen().getChildren().get(n)).setFont(Font.font(55));


        } catch (Exception e) {
            throw new IllegalStateException(e);
        }


    }

    public void startDemo(MouseEvent mouseEvent) {
        Message message = null;
        message = MessageCreater.createStartGameMsg(hardGameMode.isSelected()?1:0);
        client.sendMessage(message);
        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    }

    private void endgame(KeyEvent keyEvent, Scene resultScene) {
        Stage primaryStage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
        log.info("сцена {}", resultScene);
        primaryStage.setScene(resultScene);
        resultScene.getRoot().requestFocus();
        log.info("Is focused?{}", resultScene.getRoot().isFocused());
        log.info("Следующей буквы не будет");
        gameScreen.getChildren().remove(0, gameScreen.getChildren().size());
        gamerOneName.setText("Gamer 1");
        gamerTwoName.setText("Gamer 2");
        gamerThreeName.setText("Gamer 3");
        gamerFourName.setText("Gamer 4");
        demoGame.setVisible(true);
    }

    public void sendPublicityStatus(MouseEvent mouseEvent) {
        client.sendMessage(MessageCreater.createRoomPublicityMsg(publicity.isSelected()));
    }


}
