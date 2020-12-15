package ru.itis.typergame.client.message;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.protocol.Message;
import ru.itis.typergame.protocol.Type;

import java.io.*;

@Slf4j
public class MessageCreater {

    private static byte[] serialize(Object object) {
        byte[] bytes = new byte[1];
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

//    public static Message createTextMsg(Text text) {
//        byte[] bytes = serialize(text);
//        try {
//            return Message.createMessage(Type.TEXT, bytes);
//        } catch (IllegalAccessException e) {
//            throw new IllegalStateException(e);
//        }
//    }

    public static Message createRoomCreateMsg() {
        byte[] bytes = new byte[1];
        return Message.createMessage(Type.ROOM_CREATE, bytes);
    }

    public static Message createRoomConnectMsg(String room) {
        return Message.createMessage(Type.ROOM_CONNECT, serialize(room));
    }

    public static Message createNickNameChangeMsg(String name) {
        return Message.createMessage(Type.NAME_CHANGE, serialize(name));
    }
    public static Message createStartGameMsg(int difficulty) {
        return Message.createMessage(Type.GAME_START, serialize(new Pair<Integer, Integer>(difficulty, 0)));
    }

    public static Message createPlayGameMsg(String oneSymbol) {
        return Message.createMessage(Type.GAME_PLAY, serialize(oneSymbol));
    }

    public static Message createRoomPublicityMsg(boolean publicity) {
        return Message.createMessage(Type.ROOM_PUBLICITY_CHANGE, serialize(new Boolean(publicity)));
    }
    public static Message createGetRecordsMsg(){
        return Message.createMessage(Type.RECORD_GET,new byte[1]);
    }

    public static Message createConnectRandomMsg() {
        return Message.createMessage(Type.ROOM_CONNECT_RAND, new byte[1]);
    }

}
