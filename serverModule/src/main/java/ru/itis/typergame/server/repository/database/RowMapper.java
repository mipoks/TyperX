package ru.itis.typergame.server.repository.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T> {
    T mapRow(ResultSet resultSet) throws SQLException;
    void setResult(int result);
    int getResult();
}
