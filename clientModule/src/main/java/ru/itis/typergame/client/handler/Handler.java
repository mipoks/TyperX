package ru.itis.typergame.client.handler;

import ru.itis.typergame.protocol.Message;

public interface Handler {
    void handleMessage(Message message);

    int getType();
}
