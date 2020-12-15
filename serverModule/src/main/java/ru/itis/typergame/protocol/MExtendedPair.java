package ru.itis.typergame.protocol;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MExtendedPair<K, V> extends Pair implements Serializable {
    private int id;
    private String name;
    private double cpersec;

    public MExtendedPair(K o, V o2, double cpersec) {
        super(o, o2);
        this.cpersec = cpersec;
    }
}
