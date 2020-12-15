package ru.itis.typergame.client.util;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.itis.typergame.client.model.Gamer;

import java.util.HashMap;
@ToString
public class ColorMixer {
    @Setter
    @Getter
    private static HashMap<Gamer,String> libraryColor = new HashMap<>();

}
