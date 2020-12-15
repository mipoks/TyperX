package ru.itis.typergame.client.visualizer.eventListImpl;

import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.client.visualizer.EventListener;
import javafx.scene.text.Text;
import javafx.util.Pair;

@Slf4j
public class RoomConnectPrinter implements EventListener<Pair> {

    private Text text;

    public RoomConnectPrinter(Text text) {
        this.text = text;
    }

    @Override
    public void onEventAction(Pair object) {
        log.info("Roomconncetprinter");
        /*Pair<Integer, String> pair = (Pair<Integer, String>) object;
        text.setText("ID: " + pair.getKey() + " Name: " + pair.getValue());*/
    }
}
