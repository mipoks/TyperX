package ru.itis.typergame.server.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityCreater<T> {
    T create(ResultSet resultSet) throws SQLException;
}
