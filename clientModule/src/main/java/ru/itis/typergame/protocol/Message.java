package ru.itis.typergame.protocol;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
@Slf4j
public class Message {

    protected static final byte[] START_BYTES = new byte[]{0xA, 0xB};

    private static Pair<Integer, Integer> isSupportedType(int messageType) {
        Class typeClass = Type.class;
        Field[] fields = typeClass.getDeclaredFields();

        Integer type = null, maxSize = null;
        Pair<Integer, Integer> pair = null;
        for (int i = 0; i < fields.length; i += 2) {
            try {
                type = (Integer) fields[i].get(type);

                if (type.intValue() == messageType) {
                    i++;
                    maxSize = (Integer) fields[i].get(maxSize);
                    pair = new Pair<Integer, Integer>(type, maxSize);
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return pair;
    }

    public static Message createMessage(int messageType, byte[] data)
            throws IllegalArgumentException {

        Pair<Integer, Integer> pair = isSupportedType(messageType);
        if (pair == null) {
            throw new IllegalArgumentException("Wrong message type");
        }
        if (data.length > pair.getValue()) {
            throw new IllegalArgumentException("Message can't be " + data.length
                    + " bytes length. Maximum is " + pair.getValue() + "."
            );
        }
        return new Message(messageType, data);
    }

    public static byte[] getBytes(Message message) {
        //ToDo: check message for correct type and data size

        int rawMessageLength = START_BYTES.length
                + 4 + 4 // 4 for length size and 4 for message type
                + message.getData().length;
        byte[] rawMessage = new byte[rawMessageLength];
        int j = 0;
        for (int i = 0; i < START_BYTES.length; i++) {
            rawMessage[j++] = START_BYTES[i];
        }
        byte[] type = ByteBuffer.allocate(4).putInt(message.getType()).array();
        for (int i = 0; i < type.length; i++) {
            rawMessage[j++] = type[i];
        }
        byte[] length = ByteBuffer.allocate(4).putInt(message.getData().length).array();
        for (int i = 0; i < length.length; i++) {
            rawMessage[j++] = length[i];
        }
        byte[] data = message.getData();
        for (int i = 0; i < data.length; i++) {
            rawMessage[j++] = data[i];
        }
        return rawMessage;
    }

    public static String toString(Message message) {
        StringBuilder sb = new StringBuilder();
        String delimeter = " ";
        String nl = System.getProperty("line.separator");
        byte[] bytes = Message.getBytes(message);
        sb.append("First bytes: ");
        for (int i = 0; i < START_BYTES.length; i++) {
            sb.append(bytes[i]);
            sb.append(delimeter);
        }
        sb.append(nl);
        sb.append("Type: ");
        sb.append(ByteBuffer.wrap(bytes, 2, 4).getInt());
        sb.append(nl);
        sb.append("Length: ");
        sb.append(ByteBuffer.wrap(bytes, 6, 4).getInt());
        sb.append(nl);
        sb.append("Data: ");
        for (int i = 10; i < bytes.length; i++) {
            sb.append(bytes[i]);
            sb.append(delimeter);
        }
        return sb.toString();
    }

    public static Message readMessage(InputStream in)
            throws IllegalArgumentException {//Better to create own Exception

        byte[] start = new byte[START_BYTES.length];
        try {
            in.read(start, 0, START_BYTES.length);
            if (!Arrays.equals(
                    Arrays.copyOfRange(start, 0, START_BYTES.length),
                    START_BYTES)) {
                throw new IllegalArgumentException(
                        "Message first bytes must be " + Arrays.toString(START_BYTES)
                );
            }

            byte[] inter = new byte[4];
            in.read(inter, 0, 4);//Block Thread here
            int messageType = ByteBuffer.wrap(inter, 0, 4).getInt();

            Pair<Integer, Integer> supported = isSupportedType(messageType);
            if (supported == null) {
                throw new IllegalArgumentException("Wrong message type: " + messageType + ".");
            }
            in.read(inter, 0, 4);//Block Thread here
            int messageLength = ByteBuffer.wrap(inter, 0, 4).getInt();
            if (messageLength > supported.getValue()) {
                throw new IllegalArgumentException(
                        "Message can't be " + messageLength
                                + " bytes length. Maximum is " + supported.getValue() + "."
                );
            }
            inter = new byte[messageLength];
            in.read(inter, 0, messageLength);//Can end before messageLength

            return new Message(messageType, Arrays.copyOfRange(inter, 0, messageLength));

        } catch (IOException ex) {
            throw new IllegalArgumentException("Can't read message", ex);
        }
    }

    protected byte[] data;
    protected int type;

    protected Message(int type, byte[] data) {
        this.data = data;
        this.type = type;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
