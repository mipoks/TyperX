package ru.itis.typergame.client.logic;

import ru.itis.typergame.client.handler.Handler;
import ru.itis.typergame.client.listener.ServerMessageListener;
import ru.itis.typergame.client.model.Room;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



@Data
@Slf4j
public class Client {

    protected final InetAddress address;
    protected final int port;
    protected Socket socket;
    protected List<Handler> listeners;

    protected Room room;

    public InetAddress getAddress() {
        return address;
    }

    public void registerListener(Handler listener) {
        this.listeners.add(listener);
    }

    public void connect()  {
        try {
            socket = new Socket(address, port);
        } catch (IOException ex) {
            throw new IllegalStateException(new IOException("Can't connect.", ex));
        }
    }
    public void start() {
        room = Room.getActualRoom();
        Thread thread = new Thread(new ServerMessageListener(this,  socket));
        thread.start();
    }

    public void sendMessage(Message message)  {
        try {
            socket.getOutputStream().write(Message.getBytes(message));
            socket.getOutputStream().flush();
            //return Message.readMessage(socket.getInputStream());
        } catch (IOException ex) {
            throw new IllegalStateException(new IOException("Can't connect.", ex));
        }
    }

    public void handleMessage(Message message) {
        // Every connection will wait creating and handling of message
//        System.out.println("New message:");
//        System.out.println(Message.toString(message));
        for(Handler listener : listeners){
            if(message.getType() == listener.getType()){
                // One by one! Another left listeners will wait current
                // Another thread could be created here or before for every Listener
                listener.handleMessage(message);
            }
        }
    }

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.listeners = new ArrayList<>();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

