package ru.itis.typergame.client.visualizer.eventListImpl;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.client.visualizer.EventListener;


@Slf4j
public class RoomCodePrinter implements EventListener<String> {
    private Label label;


    public RoomCodePrinter(Label labelText) {
        this.label = labelText;
    }


    @Override
    public synchronized void onEventAction(String object) {
//        log.info("IN CODE PRINTER " + object);
//        label.setText(object);
        Platform.runLater(() -> {
            log.info("IN CODE PRINTER " + object);
            label.setText(object);
        });
    }
}
