package ru.itis.typergame.server.repository.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class JdbcTemplate <T>  {

    private ConnectionGiver connectionGiver;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private boolean connected = false;

    public JdbcTemplate(ConnectionGiver connectionGiver) {
        this.connectionGiver = connectionGiver;
    }

    //use this and commit() if you want to do smth in one transaction
    public void connect() {
        try {
            connection = connectionGiver.getConnection();
            connection.setAutoCommit(false);
            connected = true;
        } catch (SQLException e) {
            connected = false;
            try {
                connection.close();
            } catch (SQLException ex) {}
            e.printStackTrace();
        }
    }

    //use this and connect() if you want to do smth in one transaction
    public void commit() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connected = false;
    }

    public Collection<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        if (!connected) {
            try {
                connection = connectionGiver.getConnection();
            } catch (SQLException e) {
            }
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = null;
            Collection<T> result = new ArrayList<>();

            int position = 1;
            for (Object arg : args) {
                preparedStatement.setObject(position, arg);
                position++;
            }
            System.out.println(sql);
            if (sql.contains("UPDATE") || sql.contains("DELETE") || sql.contains("INSERT")) {
                rowMapper.setResult(preparedStatement.executeUpdate());
            } else {
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(rowMapper.mapRow(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            rowMapper.setResult(States.NOT_EXECUTED);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (!connected) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }
}