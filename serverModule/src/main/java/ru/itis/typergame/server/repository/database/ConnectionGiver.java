package ru.itis.typergame.server.repository.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionGiver {
    Connection getConnection() throws SQLException;
}
