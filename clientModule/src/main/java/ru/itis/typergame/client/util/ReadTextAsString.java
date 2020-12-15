package ru.itis.typergame.client.util;

import java.io.IOException;
import java.nio.file.*;;
//пока не пригодился
public class ReadTextAsString
{
    public static String readFileAsString(String fileName)
    {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return data;
    }

   /* public static void main(String[] args) throws Exception
    {
        String data = readFileAsString("C:\\Users\\pankaj\\Desktop\\test.java");
        System.out.println(data);
    }*/
}