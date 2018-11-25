import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Scanner {

    private static List<String> reachableAddresses = new ArrayList<String>();
    private static ArrayList<Thread> arrThreads = new ArrayList<Thread>(); //for thread waiting

    private static boolean isIPv4(String address) {
        return address.contains(".");
    }

    private static boolean isNotLocalAddress(String address) {
        return !address.equals("127.0.0.1");
    }

    private static String currentIPv4() {
        String currentIP = null;
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    String address = i.getHostAddress();
                    if (isIPv4(address) && isNotLocalAddress(address)) {
                        System.out.println(">>>>" + address);
                        currentIP = address;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return currentIP;
    }

    public static List<String> getNetworkIPs() {
        final byte[] ip;
        try {
            ip = InetAddress.getByName(currentIPv4()).getAddress();
        } catch (Exception e) {
            System.out.println("ip might not have been initialized");
            return null;
        }
        for (int i = 1; i <= 254; i++) {
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            Thread t = new Thread(new Runnable() {   // new thread for parallel execution
                public void run() {
                    try {
                        ip[3] = (byte) j;
                        InetAddress address = InetAddress.getByAddress(ip);
                        String output = address.toString().substring(1);
                        if (address.isReachable(5000)) {
                            reachableAddresses.add(output);
                            System.out.println(output + " is on the network");
                        } else {
                            System.out.println("Not Reachable: " + output);
                        }
                    } catch (Exception e) {
                        System.out.println("InetAddress.getByAddress(ip) fail");
//                        e.printStackTrace();
                    }
                }

            });
            t.start();
            arrThreads.add(t);
        }

        for (Thread t: arrThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return reachableAddresses;
    }

}
