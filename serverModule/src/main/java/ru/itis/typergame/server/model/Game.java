package ru.itis.typergame.server.model;

import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.server.handler.EventListener;

import java.util.*;

@Slf4j
public class Game {
    private String gameText;
    private Client winner;

    private HashMap<Client, Integer> gameState;
    private Room room;
    private boolean started;
    private ArrayList<EventListener> listeners;

    public Game(String gameText, Room room) {
        this.gameText = gameText.toLowerCase();
        this.room = room;
        games.put(room, this);
        started = false;
        listeners = new ArrayList<>();
    }

    private static HashMap<Room, Game> games = new HashMap<>();

    public String getGameText() {
        return gameText;
    }

    public void setGameText(String gameText) {
        this.gameText = gameText;
    }

    public final Client getWinner() {
        return winner;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        this.started = true;
        gameState = new HashMap<>();
        Collection<Client> clients = room.getClients();
        for (Client client : clients) {
            client.setStartTime(new Date());
        }
    }

    public int save(Client client, String cchar) {
        log.info("МЫ ЗДЕСЬ1");
        if (!started || cchar.length() < 1) {
            log.info("МЫ ЗДЕСЬ2");
            return 0;
        }
        int num = 0;
        if (gameState.containsKey(client)) {
            log.info("МЫ ЗДЕСЬ3");
            num = gameState.get(client);
            log.info("МЫ ЗДЕСЬ5 " + num);
            if (cchar.charAt(0) == gameText.charAt(num)) {

                log.info("МЫ ЗДЕСЬ6 ");
                gameState.put(client, ++num);
            }
            if (num == gameText.length()) {
                client.setEndTime(new Date());
                onClientEndGame(client);
                if (winner == null) {
                    winner = client;
                    return num + 1;
                }
            }
        } else {
            log.info("МЫ ЗДЕСЬ4");
            if (cchar.charAt(0) == gameText.charAt(0)) {
                gameState.put(client, ++num);
            }
        }
        return num;
    }

    public static Optional<Game> findByRoom(Room room) {
        Game game = null;
        if (games.containsKey(room)) {
            game = games.get(room);
        }
        return Optional.ofNullable(game);
    }


    public void addEventListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    private void onClientEndGame(Client client) {
        for (EventListener eventListener : listeners)
            eventListener.onEventAction(client);
    }
}
