package ru.itis.typergame.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import ru.itis.typergame.client.visualizer.ISceneChanger;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordsController implements Initializable {

    @FXML
    @Setter
    @Getter
    private  NumberAxis y;

    @FXML
    @Setter
    @Getter
    private  CategoryAxis x;
    @FXML
    @Setter
    @Getter
    private BarChart chart;
    @FXML
    @Getter
    private VBox body;
    @Setter
    private Scene mainScene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void getMainScene(MouseEvent mouseEvent) {

        ISceneChanger.changeScene(mainScene,mouseEvent);
    }

}
