package ru.itis.typergame.client.visualizer.eventListImpl;

import ru.itis.typergame.client.model.Gamer;
import ru.itis.typergame.client.util.ColorMixer;
import ru.itis.typergame.client.visualizer.EventListener;
import javafx.scene.text.Text;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class RivalPrinter implements EventListener<Pair> {
    private final Text gamerName1;
    private Integer n;
    private Text gamerName2;
    private Text gamerName3;
    private Text gamerName4;
    private List<Text> gamersNames;
    private CopyOnWriteArrayList<Gamer> gamers;
    private Integer integer;
    private Gamer gamer;
    public RivalPrinter(Text gamerName1,Text gamerName2,Text gamerName3,Text gamerName4) {
        n=0;
        log.info("Hello from rival printer ");
        gamers = new CopyOnWriteArrayList<>();
        gamersNames = new ArrayList<Text>();
        gamersNames.add(gamerName1);
        gamersNames.add(gamerName2);
        gamersNames.add(gamerName3);
        gamersNames.add(gamerName4);
        this.gamerName1 = gamerName1;
        this.gamerName2 = gamerName2;
        this.gamerName3=gamerName3;
        this.gamerName4=gamerName4;
    }

      @Override
        public void onEventAction(Pair object) {
        log.info("Pair: {}",object);
        if( (Integer)object.getKey()==1) {

            gamer = (Gamer) object.getValue();
            log.info("gamer{}",gamer);
            gamers.add(gamer);
            log.info("Gamer's color {}",gamersNames.get(n%4).getFill());
            ColorMixer.getLibraryColor().put(gamer,gamersNames.get(n%4).getFill().toString().substring(2,8));
            gamersNames.get(n++%4).setText(gamer.getName());
            System.out.println(object.getKey().toString() + object.getValue().toString());

        }
        }
/*    private Text textStatus1;
    private Text textStatus2;
    private Text textStatus3;
    private Text textStatus4;*/

   /* public RivalRoomConnect(Text... args) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length && i < args.length; i++) {
            fields[i].setAccessible(true);
            try {
                fields[i].set(this, args[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fields[i].setAccessible(false);
        }
    }*/
}
