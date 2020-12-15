package ru.itis.typergame.client.handler.handlerImpl;



import ru.itis.typergame.client.logic.Client;
import ru.itis.typergame.client.handler.Handler;
import ru.itis.typergame.client.model.Room;
import ru.itis.typergame.client.visualizer.EventListener;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class RoomCreateHandler implements Handler {

    private Client client;
    private ArrayList<EventListener> listeners;

    public RoomCreateHandler(Client client) {
        this.client = client;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void handleMessage(Message message) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getData())) {

            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            if (object instanceof String) {
                String text = (String) object;

                //new code
                //copy all listeners of old room
                Room room = Room.getActualRoom();
                Collection<EventListener> listeners2 = room.getAllListeners();
                //creating room
                room = Room.createNewRoom();
                room.setRoomUnique(text);
                for (EventListener eventListener : listeners2) {
                    room.addEventListener(eventListener);
                }
                log.info("GAMERS IN ROOM:" + room.getGamers().get().toString());

                for (EventListener eventListener : listeners) {
                    eventListener.onEventAction(text);
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
        return Type.ROOM_CREATE_ANSWER;
    }
}
