package ru.itis.typergame.server.handler.handlerImpl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;
import ru.itis.typergame.server.Server;
import ru.itis.typergame.server.handler.Handler;
import ru.itis.typergame.server.handler.eventListImpl.RecordSaver;
import ru.itis.typergame.server.handler.handlerImpl.helper.GameTexter;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectDeserializer;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectSerializer;
import ru.itis.typergame.server.model.Client;
import ru.itis.typergame.server.model.Game;
import ru.itis.typergame.server.model.Room;

@Slf4j
public class GameStartHandler implements Handler {
    private Server server;

    public GameStartHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handleMessage(Client client, Message message) {
        if (client.getRoom().isPresent()) {
            Room room = client.getRoom().get();
            if (room.getRoomOwner().isPresent() && room.getRoomOwner().get().equals(client)) {
                Game game = null;

                int difficulty = 0;
                Object obj = ObjectDeserializer.fromByteArray(message.getData());
                if (obj instanceof Pair) {
                    Pair<Integer, Integer> pair = (Pair) obj;
                    difficulty = pair.getKey();
                }

                if (Game.findByRoom(room).isPresent()) {
                    Client winner = Game.findByRoom(room).get().getWinner();
                    if (winner != null) {
                        game = new Game(GameTexter.getText(difficulty), room);
                    }

                } else {
                    game = new Game(GameTexter.getText(difficulty), room);
                }
                game.start();
                game.addEventListener(new RecordSaver(game));

                Message msg = Message.createMessage(Type.GAME_START_ANSWER,
                        ObjectSerializer.toByteArray(game.getGameText()));
                room.sendMessage(msg);
            }
        }
    }

    @Override
    public int getType() {
        return Type.GAME_START;
    }
}
