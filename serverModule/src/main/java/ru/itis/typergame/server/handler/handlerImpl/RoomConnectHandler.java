package ru.itis.typergame.server.handler.handlerImpl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;
import ru.itis.typergame.server.Server;
import ru.itis.typergame.server.exception.ServerException;
import ru.itis.typergame.server.handler.Handler;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectDeserializer;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectSerializer;
import ru.itis.typergame.server.model.Client;
import ru.itis.typergame.server.model.Room;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class RoomConnectHandler implements Handler {

    private Server server;

    public RoomConnectHandler(Server server) {
        this.server = server;
    }

    public void handleMessage(Client client, Message message) {

        try {
            Object roomNameObj = (ObjectDeserializer.fromByteArray(message.getData()));
            if (!(roomNameObj instanceof String))
                return;
            log.info("string res : {}", (String)(ObjectDeserializer.fromByteArray(message.getData())));

            String roomName = (String) roomNameObj;
            Optional<Room> room = Room.getRoomByUnique(roomName);
            if (!room.isPresent()) {
                return;
            }
            if (room.get().getClients().contains(client))
                return;
            if (!room.get().addClient(client)) {
                return;
            }
            client.setRoom(room.get());


            Message msg = Message.createMessage(Type.ROOM_CONNECT, ObjectSerializer.toByteArray(
                    new Pair<Integer, String>(client.getId(), client.getName())));
            room.get().sendMessageExcept(client, msg);

            msg.setType(Type.ROOM_CONNECT_ANSWER);
            msg.setData(ObjectSerializer.toByteArray(new Pair<Integer, String>(client.getId(), client.getName())));
            server.sendMessage(client, msg);

            Message roomCode = Message.createMessage(Type.ROOM_CREATE_ANSWER, ObjectSerializer.toByteArray(room.get().getRoomUniqueString()));
            server.sendMessage(client, roomCode);

            Collection<Client> clients = room.get().getClients();
            msg.setType(Type.ROOM_CONNECT);
            for (Client client1 : clients) {
                if (!client.equals(client1)) {
                    msg.setData(ObjectSerializer.toByteArray(
                            new Pair<Integer, String>(client1.getId(), client1.getName())));
                    server.sendMessage(client, msg);
                }
            }
        } catch (ServerException ex) {
            log.info(ex.getMessage());
        }
    }

    @Override
    public int getType() {
        return Type.ROOM_CONNECT;
    }
}
