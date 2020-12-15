package ru.itis.typergame.client;

public class Main {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 4888;

    public static void main(String[] args)  {
//        Client client = new Client(InetAddress.getByName(HOST), PORT);
//        Controller clientWindow = new Controller(client);
//        client.connect();
//        client.registerListener(new TextHandler(client, clientWindow));
//        client.registerListener(new RoomCreateHandler(client, clientWindow));
//        client.start();

//        System.out.println(GameTexter.getText());
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//            Object object = objectInputStream.readObject();
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ff"));
//        Message room = Message.createMessage(1, new byte[0], "ROOM");
//        out.writeObject(room);
//
////        Room d = new Room();
////        out.writeObject(d);
//        out.flush();
//        out.close();
//
//        ObjectInputStream in = new ObjectInputStream(new FileInputStream("ff"));
//
//        System.out.println(in.available());
//        try {
//            Message om = (Message) in.readObject();
//            System.out.println(om);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//
////        byte[] data = new byte[0];
////        Message request = Message.createMessage(Type.ROOM_CREATE, data, new String("ROOM".getBytes("UTF-8"), "UTF-8"));
////        System.out.println("Request");
////        System.out.println(Message.toString(request));
////        System.out.println();
////        client.sendMessage(request);
////
////        Scanner scanner = new Scanner(System.in);
////        while (true) {
////            String str = scanner.next();
////            client.sendMessage(Message.createMessage(Type.TEXT, str.getBytes("UTF-8"),  new String(Room.roomUnique.getBytes("UTF-8"), "UTF-8")));
////
////        }
    }
}
