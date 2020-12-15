package ru.itis.typergame.server.handler.handlerImpl;

import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;
import ru.itis.typergame.server.Server;
import ru.itis.typergame.server.exception.ServerException;
import ru.itis.typergame.server.handler.Handler;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectDeserializer;
import ru.itis.typergame.server.model.Client;
import ru.itis.typergame.server.model.Room;

@Slf4j
public class RoomPublicityChangeHandler implements Handler {
    private Server server;

    public RoomPublicityChangeHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handleMessage(Client client, Message message) {

        try {
            log.info("1___");
            if (!client.getRoom().isPresent())
                return;
            log.info("2___");
            Room room = client.getRoom().get();
            if (!room.getRoomOwner().isPresent() || !room.getRoomOwner().get().equals(client))
                return;

            log.info("3___");

            boolean publicity = (Boolean) ObjectDeserializer.fromByteArray(message.getData());
            room.setPublicity(publicity);
            Message answer = Message.createMessage(Type.ROOM_PUBLICITY_CHANGE_ANSWER, message.getData());

            log.info("4___");
            server.sendMessage(client, answer);
            log.info("ROOM PUBLICITY: " + room.isPublicity());
        } catch (ServerException ex) {
            log.info(ex.getMessage());
        }
    }

    @Override
    public int getType() {
        return Type.ROOM_PUBLICITY_CHANGE;
    }
}
