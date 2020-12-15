package ru.itis.typergame.protocol;

public class Type {
    //For every type designate its max size in next line in bytes
    public static final int ROOM_CREATE = 1;
    public static final int ROOM_CREATE_MAX_SIZE = 1000;
    public static final int ROOM_CREATE_ANSWER = -1;
    public static final int ROOM_CREATE_ANSWER_MAX_SIZE = 1000;

    public static final int ROOM_CONNECT = 2;
    public static final int ROOM_CONNECT_MAX_SIZE = 1000;
    public static final int ROOM_CONNECT_ANSWER = -2;
    public static final int ROOM_CONNECT_ANSWER_MAX_SIZE = 1000;

    public static final int TEXT = 3;
    public static final int TEXT_MAX_SIZE = 10000;
    public static final int TEXT_ANSWER = -3;
    public static final int TEXT_ANSWER_MAX_SIZE = 1000;

    public static final int STATE = 4;
    public static final int STATE_MAX_SIZE = 10000;
    public static final int STATE_ANSWER = -4;
    public static final int STATE_ANSWER_MAX_SIZE = 1000;

    public static final int NAME_CHANGE = 5;
    public static final int NAME_CHANGE_MAX_SIZE = 1000;
    public static final int NAME_CHANGE_ANSWER = -5;
    public static final int NAME_CHANGE_ANSWER_MAX_SIZE = 1000;

    public static final int ROOM_PUBLICITY_CHANGE = 6;
    public static final int ROOM_PUBLICITY_CHANGE_MAX_SIZE = 1000;
    public static final int ROOM_PUBLICITY_CHANGE_ANSWER = -6;
    public static final int ROOM_PUBLICITY_CHANGE_ANSWER_MAX_SIZE = 1000;

    public static final int RECORD_GET = 7;
    public static final int RECORD_GET_MAX_SIZE = 1000;
    public static final int RECORD_GET_ANSWER = -7;
    public static final int RECORD_GET_ANSWER_MAX_SIZE = 1000;

    public static final int GAME_START = 8;
    public static final int GAME_START_MAX_SIZE = 1000;
    public static final int GAME_START_ANSWER = -8;
    public static final int GAME_START_ANSWER_MAX_SIZE = 1000;

    public static final int ROOM_CONNECT_RAND = 9;
    public static final int ROOM_CONNECT_RAND_MAX_SIZE = 1000;
    public static final int ROOM_CONNECT_RAND_ANSWER = -9;
    public static final int ROOM_CONNECT_RAND_ANSWER_MAX_SIZE = 1000;

    public static final int GAME_PLAY = 10;
    public static final int GAME_PLAY_MAX_SIZE = 1000;
    public static final int GAME_PLAY_ANSWER = -10;
    public static final int GAME_PLAY_ANSWER_MAX_SIZE = 1000;

    public static final int GAME_END = 11;
    public static final int GAME_END_MAX_SIZE = 1000;
    public static final int GAME_END_ANSWER = -11;
    public static final int GAME_END_ANSWER_MAX_SIZE = 1000;

    public static final int CLIENT_ID = 12;
    public static final int CLIENT_ID_MAX_SIZE = 1000;
    public static final int CLIENT_ID_ANSWER = -12;
    public static final int CLIENT_ID_ANSWER_MAX_SIZE = 1000;

    public static final int RIVAL_NAME_CHANGE = 13;
    public static final int RIVAL_NAME_CHANGE_MAX_SIZE = 1000;
    public static final int RIVAL_NAME_CHANGE_ANSWER = -13;
    public static final int RIVAL_NAME_CHANGE_ANSWER_MAX_SIZE = 1000;

    public static final int CONNECTED = 14;
    public static final int CONNECTED_MAX_SIZE = 1000;
    public static final int CONNECTED_ANSWER = -14;
    public static final int CONNECTED_ANSWER_MAX_SIZE = 1000;

    public static final int DISCONNECTED = 15;
    public static final int DISCONNECTED_MAX_SIZE = 1000;
    public static final int DISCONNECTED_ANSWER = -15;
    public static final int DISCONNECTED_ANSWER_MAX_SIZE = 1000;

}
