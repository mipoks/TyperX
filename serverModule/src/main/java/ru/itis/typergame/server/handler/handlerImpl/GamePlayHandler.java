package ru.itis.typergame.server.handler.handlerImpl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.MExtendedPair;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;
import ru.itis.typergame.server.Server;
import ru.itis.typergame.server.handler.Handler;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectDeserializer;
import ru.itis.typergame.server.handler.handlerImpl.helper.ObjectSerializer;
import ru.itis.typergame.server.model.Client;
import ru.itis.typergame.server.model.Game;
import ru.itis.typergame.server.model.Room;

import java.util.Optional;

@Slf4j
public class GamePlayHandler implements Handler {

    private Server server;

    public GamePlayHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handleMessage(Client client, Message message) {
        if (client.getRoom().isPresent()) {
            Room room = client.getRoom().get();
            room.sendMessage(client, message);

            String cchar = (String) ObjectDeserializer.fromByteArray(message.getData());
            log.info(cchar);
            Optional<Game> optionalGame = Game.findByRoom(room);

            log.info(optionalGame.isPresent() + " ОН СУЩ");
            int ans = 0;
            if (optionalGame.isPresent())
                ans = optionalGame.get().save(client, cchar);
            else
                return;
            Message msg = Message.createMessage(Type.GAME_PLAY,
                    ObjectSerializer.toByteArray(new Pair<Integer, Integer>
                            (client.getId(), ans)));
            room.sendMessage(msg);
            
            if (ans + 1 == optionalGame.get().getGameText().length()) {
                Message msgGameEnd = Message.createMessage(Type.GAME_END, ObjectSerializer.
                        toByteArray(new MExtendedPair<Integer, String>(client.getId(), client.getName(), client.getCpersec())));
                room.sendMessage(msgGameEnd);
                log.info("Игра окончена" );
            }
        }
    }

    @Override
    public int getType() {
        return Type.GAME_PLAY;
    }
}
