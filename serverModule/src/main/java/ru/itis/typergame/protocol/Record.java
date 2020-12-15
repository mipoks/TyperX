package ru.itis.typergame.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Record implements Serializable {
    private String name;
    private double time;

}
