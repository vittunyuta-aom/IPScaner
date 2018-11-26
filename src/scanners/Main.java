package scanners;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Scanner sc = Scanner.instance();
        sc.scan();
        // schedule every 5 second
//        sc.startScan();
//        sc.stopScan();
    }
}
