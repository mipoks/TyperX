package ru.itis.typergame.server.handler.eventListImpl;

import ru.itis.typergame.protocol.Record;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.server.handler.EventListener;
import ru.itis.typergame.server.model.Client;
import ru.itis.typergame.server.model.Game;
import ru.itis.typergame.server.repository.RecordRepository;
import ru.itis.typergame.server.repository.impl.RecordRepoImpl;

@Slf4j
public class RecordSaver implements EventListener {

    private Game game;
    private static RecordRepository recordRepository = new RecordRepoImpl();

    public RecordSaver(Game game) {
        this.game = game;
    }

    @Override
    public void onEventAction(Object object) {
        log.info("рекорд должен быть сохранен");
        if (object instanceof Client) {
            Client client = (Client) object;
            log.info("LEN TEXT: " + game.getGameText().length());
            log.info("CLIENT END TIME: " + client.getEndTime().getTime());
            log.info("CLIENT START TIME: " + client.getStartTime().getTime());
            double cpersec = game.getGameText().length() * 1000 * 60 /
                    (double) (client.getEndTime().getTime() - client.getStartTime().getTime());

            client.setCpersec(cpersec);
            log.info("Скокроть клиента: " + cpersec);
            Record record = new Record(client.getName(), cpersec);
            recordRepository.save(record);
        }
    }
}
