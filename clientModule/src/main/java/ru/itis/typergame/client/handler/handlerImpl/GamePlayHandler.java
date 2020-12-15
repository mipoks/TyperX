package ru.itis.typergame.client.handler.handlerImpl;


import ru.itis.typergame.client.model.Gamer;
import ru.itis.typergame.client.model.Room;
import ru.itis.typergame.client.visualizer.EventListener;
import ru.itis.typergame.client.handler.Handler;
import ru.itis.typergame.client.logic.Client;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class GamePlayHandler implements Handler {
    private String theme;
    private Client client;
    private ArrayList<EventListener> listeners;

    public GamePlayHandler(Client client) {
        this.client = client;
        this.listeners = new ArrayList<>();
    }


    @Override
    public void handleMessage(Message message) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getData())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            if (object instanceof Pair) {
                Pair<Integer, Integer> pair = (Pair) object; //id + cursor on game text

                //new code
                Room room = Room.getActualRoom();
                Optional<Gamer> gamerOptional = room.findById(pair.getKey());
                if (gamerOptional.isPresent()) {
                    Gamer gamer = gamerOptional.get();
                    gamer.setProgress(pair.getValue());
                }

                //old code
                for (EventListener eventListener : listeners) {
                    eventListener.onEventAction(pair);
                }
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            log.info(e.getMessage());
        }
    }
    public void addEventListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public int getType() {
        return Type.GAME_PLAY;
    }
}
