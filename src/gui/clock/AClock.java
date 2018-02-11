package gui.clock;

import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import javax.swing.Timer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class AClock {
    @NotNull
    public static Timer launchTimer(final Text textOutput){
        return new Timer(500, e -> {
           final Date d = new Date();
           final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.dd.MM");
           final Text text = new Text();
           text.setText(sdf.format(d));
           final DateFormat df = new SimpleDateFormat("HH:mm");
           final Date times = Calendar.getInstance().getTime();
           final String reportDate = df.format(times);
           textOutput.setText(reportDate);
       });
    }
}
