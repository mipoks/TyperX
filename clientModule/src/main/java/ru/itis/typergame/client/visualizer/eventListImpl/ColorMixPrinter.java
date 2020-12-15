package ru.itis.typergame.client.visualizer.eventListImpl;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.client.controllers.MainGameController;
import ru.itis.typergame.client.model.Room;
import ru.itis.typergame.client.util.ColorMixer;
import ru.itis.typergame.client.visualizer.EventListener;

@Slf4j
public class ColorMixPrinter implements EventListener<Pair> {
   private MainGameController mainGameController;
   private ObservableList<Node> filed;
   private volatile Room room;
   private int position;
   private Text symb;
   private String paint;
   private Integer colorIndexHome;
   private Integer colorIndexServer;
   private  Integer  color;


   public  ColorMixPrinter(MainGameController controller){
       this.mainGameController = controller;
       filed = controller.getGameScreen().getChildren();

   }

    @Override
    public void onEventAction(Pair object) {
       log.info("library of colors(hash) {}",ColorMixer.getLibraryColor().toString());
       /*filed.stream().map(x->(Text)x).forEach(x->log.info("Fill of text in text flow {}",x.getFill()));*/

       room = Room.getActualRoom();
       log.info("room gamers {}",room.getGamers().get());
       position = (int)object.getValue()-1;
       log.info("Pair object key {}, value {}",object.getKey(),object.getValue());
        room.getGamers().ifPresent(x -> {
            x.stream().forEach(gamer -> {
                if(gamer.getId() == (int) object.getKey()) {

                    symb = (Text) filed.get(position);
                    symb.setStyle("-fx-stroke: #353535");
                    log.info("symb fills {}", symb.getFill().toString().substring(2, 8));
                    switch (symb.getFill().toString().substring(2, 8)) {
                        case "778899" -> {
                            symb.setFill(Paint.valueOf(ColorMixer.getLibraryColor().get(gamer)));
                            log.info("case 778899  gamers color: {}", Paint.valueOf(ColorMixer.getLibraryColor().get(gamer)));
                        }
                        case "000000" -> {
                            symb.setFill(Paint.valueOf(ColorMixer.getLibraryColor().get(gamer)));
                            log.info("case 000000  gamers color: {}", Paint.valueOf(ColorMixer.getLibraryColor().get(gamer)));
                        }
                        case "ff2376" -> {symb.setFill(Paint.valueOf("f5b03b"));
                        log.info("case FF2376");}



                        case "ff0000", "000fff" ->{symb.setFill(Paint.valueOf("d4c80b"));} // смешивание красного с синим ?
                        case "00ff33", "E13CF0" ->{
                            switch (ColorMixer.getLibraryColor().get(gamer)){
                                case "000fff"->{ symb.setFill(Paint.valueOf("ffef08"));}//желтый
                                case "ff0000"->{symb.setFill(Paint.valueOf("053742"));}
                            }

                        }
                        case "d4c80b"->{symb.setFill(Paint.valueOf("ec7544"));}
                        case "ec7544"->{symb.setFill(Paint.valueOf("852A26"));}

                        default -> {symb.setFill(Paint.valueOf("ff2376"));
                        log.info("case default");}

                    }

                     ((Text) filed.get(position)).setFont(Font.font(40));

                    ((Text) filed.get(position)).setFont(Font.font(48));
                   /* paint =symb.getFill().toString().substring(2,8);
                    colorIndexHome = Integer.parseInt(paint,16);
                    colorIndexServer = Integer.parseInt(ColorMixer.getLibraryColor().get(gamer),16);*/
                    log.info("Устанавливаю красный стиль");


              /*      color = ((colorIndexHome*colorIndexServer+colorIndexHome*colorIndexHome)-colorIndexHome*colorIndexServer);
                    log.info("home color {}",Integer.toString(colorIndexHome,16));
                    log.info("server color {}",Integer.toString(colorIndexServer,16));
                    log.info("final color {}",Integer.toString(color,16).substring(1,7));
                    symb.setFill(Paint.valueOf(Integer.toString(color,16).substring(1,7)));*/



                }
            });
        });

    }
}
