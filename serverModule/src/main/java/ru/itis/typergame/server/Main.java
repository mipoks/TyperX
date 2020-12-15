package ru.itis.typergame.server;


import ru.itis.typergame.server.exception.ServerException;
import ru.itis.typergame.server.handler.handlerImpl.*;
import ru.itis.typergame.server.repository.impl.RecordRepoImpl;

public class Main {
    public static void main(String[] args) throws ServerException {
        Server server = new Server(4888);
        server.registerListener(new GamePlayHandler(server));
        server.registerListener(new GameStartHandler(server));
        server.registerListener(new NameChangeHandler(server));
        server.registerListener(new RecordGetHandler(server, new RecordRepoImpl()));
        server.registerListener(new RoomConnectHandler(server));
        server.registerListener(new RoomCreateHandler(server));
        server.registerListener(new RoomPublicityChangeHandler(server));
        server.registerListener(new RoomRandomConnectHandler(server));
        server.start();
    }
}
