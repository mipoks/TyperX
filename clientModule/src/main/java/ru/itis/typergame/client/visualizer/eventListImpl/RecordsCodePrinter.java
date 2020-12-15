package ru.itis.typergame.client.visualizer.eventListImpl;

import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import ru.itis.typergame.client.visualizer.EventListener;
import ru.itis.typergame.protocol.Record;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RecordsCodePrinter implements EventListener<Collection<Record>> {
    private BarChart chart;
    private Text text;
    private XYChart.Series dataSeries1;

    public RecordsCodePrinter(BarChart chart) {

        this.chart = chart;
        dataSeries1 = new XYChart.Series();
    }

    @Override
    public synchronized void onEventAction(Collection<Record> object) {
        Platform.runLater(() -> {
            chart.getData().remove(0, chart.getData().size());
             AtomicInteger i = new AtomicInteger(1);

            object.forEach(x -> {
                log.info("");
                log.info("Прихдящая запись {}  ,  {}", x.getName(), x.getTime());
                dataSeries1.getData().add(new XYChart.Data(i.getAndIncrement() + ": " + x.getName(), x.getTime()));
            });
            chart.getData().add(dataSeries1);


        });


    }
}
