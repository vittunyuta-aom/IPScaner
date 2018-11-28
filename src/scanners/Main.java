package scanners;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends TimerTask {

    private static Scanner sc;
    private static Timer timer = new Timer();

    public static void main(String[] args) {
        sc = Scanner.instance();
        Main main = new Main();
        timer.schedule(main, 0,5000);
        // schedule every 5 second
    }

    @Override
    public void run() {
        List<ScannedDevice> list = sc.scan();
        System.out.println(Arrays.toString(list.toArray()));
        System.out.println("-------------------");
    }
}
