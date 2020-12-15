package ru.itis.typergame.server.model;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Client {
    private String name;
    private static int idCounter = 1;
    private int id;
    private Socket socket;
    private Room room;
    private double cpersec;

    private Date startTime;
    private Date endTime;

    public Client(String name, Socket socket, Room room) {
        this.name = name;
        this.id = idCounter++;
        this.socket = socket;
        this.room = room;
    }

    public Client(String name, Socket socket) {
        this.name = name;
        this.id = idCounter++;
        this.socket = socket;
    }

    public double getCpersec() {
        return cpersec;
    }

    public void setCpersec(double cpersec) {
        this.cpersec = cpersec;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStartTime(Date date) {
        startTime = date;
    }

    public void setEndTime(Date date) {
        endTime = date;
    }

    public Optional<Room> getRoom() {
        return Optional.ofNullable(room);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public static String generateName() {
        return "Anonimus" + idCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Client client = (Client) o;
        return Objects.equals(socket, client.socket);
    }
}
