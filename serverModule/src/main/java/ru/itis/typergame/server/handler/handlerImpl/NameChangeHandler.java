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

@Slf4j
public class NameChangeHandler implements Handler {
    private Server server;

    public NameChangeHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handleMessage(Client client, Message message) {
        try {
            if (!(ObjectDeserializer.fromByteArray(message.getData()) instanceof String)) {
                return;
            }
            client.setName((String)(ObjectDeserializer.fromByteArray(message.getData())));

            Message msgAns = Message.createMessage(Type.NAME_CHANGE_ANSWER, message.getData());
            server.sendMessage(client, msgAns);

            if (client.getRoom().isPresent()) {
                msgAns.setData(ObjectSerializer.toByteArray(new Pair<Integer, String>(client.getId(), client.getName())));
                msgAns.setType(Type.NAME_CHANGE);
                client.getRoom().get().sendMessage(message);
            }
        } catch (ServerException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public int getType() {
        return Type.NAME_CHANGE;
    }
}
