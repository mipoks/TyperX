package ru.itis.typergame.server.handler.handlerImpl.helper;


import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Slf4j
public class ObjectDeserializer {
    public static Object fromByteArray(byte[] bytes) {
        Object object = null;
        try (ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(bytes)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream);
            object = objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
