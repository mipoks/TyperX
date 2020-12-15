package ru.itis.typergame.server.repository;

import ru.itis.typergame.protocol.Record;
import java.util.Collection;

public interface RecordRepository {
    int save(Record record);
    Collection<Record> getAll();
}
