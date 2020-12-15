package ru.itis.typergame.server.repository.database.implementation;

import ru.itis.typergame.server.model.EntityCreater;
import ru.itis.typergame.server.repository.database.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperImpl <T> implements RowMapper {

    private EntityCreater<T> entityCreater;
    private int ans = 0;

    public RowMapperImpl(EntityCreater<T> entityCreater) {
        this.entityCreater = entityCreater;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        return entityCreater.create(resultSet);
    }

    @Override
    public void setResult(int result) {
        ans = result;
    }

    public int getResult() {
        return ans;
    }
}
