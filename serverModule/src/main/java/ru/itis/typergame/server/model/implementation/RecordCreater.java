package ru.itis.typergame.server.model.implementation;

import ru.itis.typergame.server.model.EntityCreater;

import java.sql.ResultSet;
import java.sql.SQLException;
import ru.itis.typergame.protocol.Record;

public class RecordCreater implements EntityCreater {
    public Record create(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        Long time = resultSet.getLong("cpersec");
        Record clientDTO = new Record(name, time);
        return clientDTO;
    }
}
