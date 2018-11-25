import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private static List<ScannedDevice> reachableAddresses = new ArrayList<ScannedDevice>();
    private static ArrayList<Thread> arrThreads = new ArrayList<Thread>(); //for thread waiting

    private static boolean isIPv4(String address) {
        return address.contains(".");
    }

    private static boolean isNotLocalAddress(String address) {
        return !address.equals("127.0.0.1");
    }

//    private static String currentIPv4() {
//        String currentIP = null;
//        try {
//            Enumeration e = NetworkInterface.getNetworkInterfaces();
//            while (e.hasMoreElements()) {
//                NetworkInterface n = (NetworkInterface) e.nextElement();
//                Enumeration ee = n.getInetAddresses();
//                while (ee.hasMoreElements()) {
//                    InetAddress i = (InetAddress) ee.nextElement();
//                    String address = i.getHostAddress();
//                    if (isIPv4(address) && isNotLocalAddress(address)) {
//                        System.out.println(">>>>" + address);
//                        currentIP = address;
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        return currentIP;
//    }
//
//    public static List<ScannedDevice> getNetworkIPs() {
//        final byte[] ip;
//        try {
//            ip = InetAddress.getByName(currentIPv4()).getAddress();
//        } catch (Exception e) {
//            System.out.println("ip might not have been initialized");
//            return null;
//        }
//        for (int i = 1; i <= 254; i++) {
//            final int j = i;  // i as non-final variable cannot be referenced from inner class
//            Thread t = new Thread(new Runnable() {   // new thread for parallel execution
//                public void run() {
//                    try {
//                        ip[3] = (byte) j;
//                        String reachableIp = getReachableIp(ip);
//                        if (reachableIp != null)
//                            reachableAddresses.add(new ScannedDevice(reachableIp,""));
//
//                    } catch (UnknownHostException e) {
//                        e.printStackTrace();
//                    } catch (SocketException e) {
//                        e.printStackTrace();
//                    }  catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            t.start();
//            arrThreads.add(t);
//        }
//
//        for (Thread t : arrThreads) {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return reachableAddresses;
//    }
//
//    private static String getReachableIp(byte[] ip) throws IOException {
//        InetAddress address = InetAddress.getByAddress(ip);
//        String output = address.toString().substring(1);
//        if (address.isReachable(5000)) {
//            System.out.println(output + " is on the network");
//            return output;
//        }
//        System.out.println("Not Reachable: " + output);
//        return null;
//    }
//
//    private static List<String> getMacAddress() throws SocketException {
//        List<String> macList = new ArrayList<String>();
//        Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
//        NetworkInterface inter;
//        while (networks.hasMoreElements()) {
//            StringBuilder macStr = new StringBuilder();
//            inter = networks.nextElement();
//            byte[] mac = inter.getHardwareAddress();
//            if (mac != null) {
//                for (int i = 0; i < mac.length; i++) {
//                    macStr.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//                }
//                System.out.println("macStr:"+macStr);
//                macList.add(macStr.toString());
//            }
//        }
//        return macList;
//    }
//
//    private static List<String> getMacAddressByIp(InetAddress ip) throws SocketException {
//        List<String> macList = new ArrayList<String>();
//        try {
//            System.out.println("Current IP address : " + ip.getHostAddress());
//            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
//            byte[] mac = network.getHardwareAddress();
//            System.out.print("Current MAC address : ");
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < mac.length; i++) {
//                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//            }
//            System.out.println(sb.toString());
//            macList.add(sb.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return macList;
//    }

    public List<ScannedDevice> startscan() {
        try {
            Process proc = Runtime.getRuntime().exec("arp -a ");

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String str;
            while ((str = stdInput.readLine()) != null) {
                System.out.println(">>>>"+str);
                reachableAddresses.add(new ScannedDevice(findIpAddress(str),findMacAddress(str)));

                // read any errors from the attempted command
                while ((str = stdError.readLine()) != null) {
                    System.err.println(str);
                }
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return reachableAddresses;
    }

    private String findMacAddress(String str){
        // MAC Address
        int startMacIndex = indexOfWord(str,"at") + 3;
        int stopMacIndex = indexOfWord(str,"on");
        String mac = str.substring(startMacIndex,stopMacIndex);
        System.out.println("mac>"+mac);
        return mac;
    }

    private String findIpAddress(String str){
        // IP Address
        int startIpIndex = indexOfWord(str,"(") + 1;
        int stopIpIndex = indexOfWord(str,")");
        String ip = str.substring(startIpIndex,stopIpIndex);
        System.out.println("ip>"+ip);
        return ip;
    }

    private int indexOfWord(String str,String needle){
        if (str.contains(needle)) return str.indexOf(needle);
        System.out.println("not found " + needle);
        return -1;
    }
}
