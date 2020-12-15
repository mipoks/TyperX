package ru.itis.typergame.client;

import ru.itis.typergame.client.controllers.*;
import ru.itis.typergame.client.handler.handlerImpl.*;
import ru.itis.typergame.client.logic.Client;
import ru.itis.typergame.client.model.Room;
import ru.itis.typergame.client.visualizer.eventListImpl.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;

@Slf4j
public class ClientStart extends Application {
    public TextField textField;
    public Text textStatus;

    private Parent root;
    private Parent wint;
    private Parent mainGame;
    private Parent loose;
    private Parent records;
    private Parent settings;
    private Controller controller;
    private MainGameController controller1;
    private WinController controller3;
    private LostController controller4;
    private RecordsController controller5;
    private SettingsController settingsController;
    private Client client;
    private Scene scene;
    private FXMLLoader loader;
    private FXMLLoader loader2;
    private FXMLLoader loader3;
    private FXMLLoader loader4;
    private FXMLLoader loader5;
    private FXMLLoader settingsLoader;
    private RoomCreateHandler roomCreateHandler;
    private RoomConnectHandler roomConnectHandler;
    private RivalConnectHandler rivalConnectHandler;
    private GameStartHandler gameStartHandler;
    private GamePlayHandler gamePlayHandler;
    private RecordGetHandler recordGetHandler;
    private RoomCodePrinter roomCodePrinter;
    private RoomConnectPrinter roomConnectPrinter;
    private RivalPrinter rivalPrinter;
    private GameTextPrinter gameTextPrinter;
    private ColorMixPrinter colorMixPrinter;
    private RecordsCodePrinter recordsCodePrinter;

    private volatile Room room;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loader = new FXMLLoader(getClass().getResource("/chooseRoomType.fxml"));
        loader2 = new FXMLLoader(getClass().getResource("/gameTextMain.fxml"));
        loader3 = new FXMLLoader(getClass().getResource("/Win.fxml"));
        loader4 = new FXMLLoader(getClass().getResource("/lost.fxml"));
        loader5 = new FXMLLoader(getClass().getResource("/RecordTable.fxml"));
        settingsLoader = new FXMLLoader(getClass().getResource("/settingGame.fxml"));

        try {
            loose = loader4.load();
            root = loader.load();
            wint = loader3.load();
            mainGame = loader2.load();
            records = loader5.load();
            settings = settingsLoader.load();


            controller = loader.getController(); //manuController
            controller1 = loader2.getController();//MainGame controller
            controller3 = loader3.getController();//win scene controller
            controller4 = loader4.getController();//loose scene controller
            controller5 = loader5.getController(); //record scene
            settingsController = settingsLoader.getController();
            scene = new Scene(root);


            client = new Client(InetAddress.getByName("127.0.0.1"), 4888);
            controller.setClient(client);
            controller1.setClient(client);
            settingsController.setClient(client);


            Scene gameScene = new Scene(mainGame);
            Scene recordScene = new Scene(records);
            Scene settingsScene = new Scene(settings);

            controller.setGameScene(gameScene);
            controller.setRecordScene(recordScene);
            controller.setSettingsScene(settingsScene);
            controller1.setWinScene(new Scene(wint));
            controller1.setLostScene(new Scene(loose));
            controller3.setScene(scene);
            controller4.setScene(scene);
            controller5.setMainScene(scene);
            settingsController.setMainScene(scene);


            client.connect();

            roomCreateHandler = new RoomCreateHandler(client);
            roomConnectHandler = new RoomConnectHandler(client);
            rivalConnectHandler = new RivalConnectHandler(client);
            gameStartHandler = new GameStartHandler(client);
            gamePlayHandler = new GamePlayHandler(client);
            recordGetHandler = new RecordGetHandler(client);

            room = Room.getActualRoom();
            client.registerListener(roomConnectHandler);
            client.registerListener(roomCreateHandler);
            client.registerListener(rivalConnectHandler);
            client.registerListener(gameStartHandler);
            client.registerListener(gamePlayHandler);
            client.registerListener(recordGetHandler);

            client.start();






            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();


           /* textStatus = (Text) scene.lookup("#textStatus");*/
            roomCodePrinter = new RoomCodePrinter(controller1.getRoomCode());
            log.info("Имя геймера с контроллера игры{}",controller1.getGamerTwoName().getText());
            roomConnectPrinter = new RoomConnectPrinter(controller1.getGamerOneName());
            gameTextPrinter = new GameTextPrinter(controller1);
            rivalPrinter = new RivalPrinter(controller1.getGamerOneName(),controller1.getGamerTwoName(),controller1.getGamerThreeName(),controller1.getGamerFourName());//Выводит принтер в контроллер
            colorMixPrinter = new ColorMixPrinter(controller1);
            recordsCodePrinter = new RecordsCodePrinter(controller5.getChart());
            gameStartHandler.addEventListener(gameTextPrinter);
            roomConnectHandler.addEventListener(roomConnectPrinter);
            roomCreateHandler.addEventListener(roomCodePrinter);
            gamePlayHandler.addEventListener(colorMixPrinter);
            recordGetHandler.addEventListener(recordsCodePrinter);


            room.addEventListener(rivalPrinter);//каждый раз когда соперник подключился , отправит всем листенерам Pair

            log.info(" opponent row{}", controller.getOpponentRow());




        } catch (IOException e ) {
            e.printStackTrace();
        }


    }
}
