package ru.itis.typergame.client.visualizer.eventListImpl;

import ru.itis.typergame.client.controllers.MainGameController;
import ru.itis.typergame.client.visualizer.EventListener;
import ru.itis.typergame.protocol.MExtendedPair;

public class EndgameResultsPrinter implements EventListener<MExtendedPair<Integer,String>> {
    private MainGameController mainGameController;


    public EndgameResultsPrinter(){

    }
    @Override
    public void onEventAction(MExtendedPair<Integer,String> object) { //Integer - id String //name // 3-d parametr spec

    }
}
