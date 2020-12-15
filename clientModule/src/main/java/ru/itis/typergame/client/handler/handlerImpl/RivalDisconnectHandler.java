package ru.itis.typergame.client.handler.handlerImpl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.client.handler.Handler;
import ru.itis.typergame.client.logic.Client;
import ru.itis.typergame.client.model.Gamer;
import ru.itis.typergame.client.model.Room;
import ru.itis.typergame.client.visualizer.EventListener;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

@Slf4j
public class RivalDisconnectHandler implements Handler {
    private ru.itis.typergame.client.logic.Client client;
    private ArrayList<EventListener> listeners;

    public RivalDisconnectHandler(Client client) {
        this.client = client;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void handleMessage(Message message) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getData())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            if (object instanceof Pair) {
                Pair<Integer, String> pair = (Pair<Integer, String>) object; //id of disconnected rival

                //new code
                Room room = Room.getActualRoom();
                room.removeGamer(new Gamer(pair.getKey(), pair.getValue()));
                log.info("GAMERS IN ROOM:" + room.getGamers().get().toString());

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
        return Type.DISCONNECTED;
    }
}
