package ru.itis.typergame.server.handler.handlerImpl;

import ru.itis.typergame.protocol.Record;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;
import ru.itis.typergame.server.Server;
import ru.itis.typergame.server.exception.ServerException;
import ru.itis.typergame.server.handler.Handler;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectSerializer;
import ru.itis.typergame.server.model.Client;
import ru.itis.typergame.server.repository.RecordRepository;

import java.util.Collection;

public class RecordGetHandler implements Handler {
    private Server server;
    private RecordRepository recordRepository;

    public RecordGetHandler(Server server, RecordRepository recordRepository) {
        this.server = server;
        this.recordRepository = recordRepository;
    }

    @Override
    public void handleMessage(Client client, Message message) {
        try {
            Collection<Record> records = recordRepository.getAll();
            Message msgAns = Message.createMessage(Type.RECORD_GET_ANSWER, ObjectSerializer.toByteArray(records));
            server.sendMessage(client, msgAns);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getType() {
        return Type.RECORD_GET;
    }
}
