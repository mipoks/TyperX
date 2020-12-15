package ru.itis.typergame.client.handler.handlerImpl;

import ru.itis.typergame.client.visualizer.EventListener;
import ru.itis.typergame.client.handler.Handler;
import ru.itis.typergame.client.logic.Client;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.MExtendedPair;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
@Slf4j
public class GameEndHandler implements Handler {
    private Client client;
    private ArrayList<EventListener> listeners;

    public GameEndHandler(Client client) {
        this.client = client;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void handleMessage(Message message) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getData())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            if (object instanceof MExtendedPair) {
                MExtendedPair<Integer, String> pair = (MExtendedPair) object; //id + name of rival
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
        return Type.GAME_END;
    }
}
