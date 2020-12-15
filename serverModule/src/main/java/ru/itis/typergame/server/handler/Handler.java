package ru.itis.typergame.server.handler;

import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.server.model.Client;

public interface Handler {
    void handleMessage(Client client, Message message);

    int getType();
}
