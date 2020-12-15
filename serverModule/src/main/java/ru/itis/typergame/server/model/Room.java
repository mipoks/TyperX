package ru.itis.typergame.server.model;

import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

@Slf4j
public class Room {
    public static final int ROOM_UNIQUE_LENGTH = 4;
    public static final int MAX_CLIENT = 4;

    private static HashMap<String, Room> uniqueString = new HashMap<>();
    private static final String CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public synchronized static String createRoomUniqueString() {
        char[] text = new char[ROOM_UNIQUE_LENGTH];
        do {
            for (int i = 0; i < ROOM_UNIQUE_LENGTH; i++) {
                text[i] = CHARACTERS.charAt((int) (Math.random() * CHARACTERS.length()));
            }
        } while (getRoomByUnique(new String(text)).isPresent());
        return new String(text);
    }

    public static Optional<Room> getRoomByUnique(String roomUnique) {
        Room room = null;
        Set<String> keys = uniqueString.keySet();
        for (String roomCode : keys) {
            if (roomCode.equals(roomUnique)) {
                room = uniqueString.get(roomCode);
                break;
            }
        }
        return Optional.ofNullable(room);
    }

    public static Room getOpenRoom() {
        Collection<Room> rooms = uniqueString.values();
        for (Room room : rooms) {
            if (room.isPublicity() && room.getClientsCount() < Room.MAX_CLIENT) {
                return room;
            }
        }
        return new Room(Room.createRoomUniqueString());
    }

    public int getClientsCount() {
        return clients.size();
    }

    private String roomUniqueString;
    private ArrayList<Client> clients;
    private boolean publicity;
    public Room(String roomUniqueString) {
        this.roomUniqueString = roomUniqueString;
        this.clients = new ArrayList<>();
        this.publicity = false;
        uniqueString.put(roomUniqueString, this);
    }

    public Room(String roomUniqueString, boolean publicity) {
        this.roomUniqueString = roomUniqueString;
        this.clients = new ArrayList<>();
        this.publicity = publicity;
        uniqueString.put(roomUniqueString, this);
    }

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public Optional<Client> getRoomOwner() {
        Client client = null;
        if (clients.size() > 0) {
            client = clients.get(0);
        }
        return Optional.ofNullable(client);
    }

    public boolean isPublicity() {
        return publicity;
    }

    public void setPublicity(boolean publicity) {
        this.publicity = publicity;
    }

    public synchronized boolean addClient(Client client) {
        if (getClientsCount() < Room.MAX_CLIENT) {
            if (!clients.contains(client)) {
                clients.add(client);
                return true;
            }
        }
        return false;
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void sendMessage(Message message) {
        byte[] rawMessage = Message.getBytes(message);
        for (Client client : clients) {
            try {
                Socket socket = client.getSocket();
                socket.getOutputStream().write(rawMessage);
                socket.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("SENDED TO " + clients.size() + " CLIENTS OF ROOM " + roomUniqueString);
    }

    public void sendMessageExcept(Client except, Message message) {
        byte[] rawMessage = Message.getBytes(message);
        for (Client client : clients) {
            if (!client.equals(except)) {
                try {
                    System.out.println("SENEDEDED");
                    Socket socket = client.getSocket();
                    socket.getOutputStream().write(rawMessage);
                    socket.getOutputStream().flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        log.info("SENDED TO " + clients.size() + " CLIENTS OF ROOM " + roomUniqueString);
    }

    public void sendMessage(Client from, Message message) {
        byte[] rawMessage = Message.getBytes(message);
        if (clients.contains(from)) {
            for (Client client : clients) {
                try {
                    Socket socket = client.getSocket();
                    socket.getOutputStream().write(rawMessage);
                    socket.getOutputStream().flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("SENDED TO " + clients.size() + " CLIENTS OF ROOM " + roomUniqueString);
        }
    }

    public static void cleanEmptyRooms() {
        Set<String> keys = uniqueString.keySet();
        for (String roomCode : keys) {
            Room room = uniqueString.get(roomCode);
            if (room.getClients().size() == 0) {
                synchronized (room) {
                    if (room.getClients().size() == 0)
                        uniqueString.remove(roomCode);
                }
            }
        }
    }

    public String getRoomUniqueString() {
        return roomUniqueString;
    }
}
