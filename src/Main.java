import java.util.Arrays;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Scanner sc = new Scanner();
        System.out.println(Arrays.toString(sc.startScan().toArray()));
        Date d = new Date();
        System.out.println(d.getTime());
    }
}
