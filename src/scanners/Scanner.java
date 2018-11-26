package scanners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import application.ScannerController;

public class Scanner{
    private static List<String> scannedTime = new ArrayList<String>();
    private static List<ScannedDevice> reachableAddresses = new ArrayList<ScannedDevice>();
    private final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");
    
    private static Scanner scanner = new Scanner();

    private Scanner(){}

    public static Scanner instance(){
        return scanner;
    }
    public static List<String> getScannedTime() {
		return scannedTime;
	}


    public List<ScannedDevice> scan() {
        Date currentTimeScan = new Date();
        scannedTime.add(TIMEFORMAT.format(currentTimeScan));

        try {
            Process proc = Runtime.getRuntime().exec("arp -a");

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String str;
            while ((str = stdInput.readLine()) != null) {
                System.out.println(">>>>" + str);
                String ip = findIpAddress(str);
                int index = containsIpWithIndex(ip);
                if (index == -1)
                    reachableAddresses.add(new ScannedDevice(ip, findMacAddress(str)));
                else
                    reachableAddresses.get(index).updateData();

                // read any errors from the attempted command
                while ((str = stdError.readLine()) != null) {
                    System.err.println(str);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return reachableAddresses;
    }

    // MAC Address
    private String findMacAddress(String str) {
        int startMacIndex = indexOfWord(str, "at") + 3;
        int stopMacIndex = indexOfWord(str, "on");
        return str.substring(startMacIndex, stopMacIndex);
    }

    // IP Address
    private String findIpAddress(String str) {
        int startIpIndex = indexOfWord(str, "(") + 1;
        int stopIpIndex = indexOfWord(str, ")");
        return str.substring(startIpIndex, stopIpIndex);
    }

    private int indexOfWord(String str, String needle) {
        if (str.contains(needle)) return str.indexOf(needle);
        System.out.println("not found " + needle);
        return -1;
    }

    /**
     * @param ip is founded ip address
     * @return index of found object, otherwise return -1
     */
    private int containsIpWithIndex(String ip) {
        for (int i = 0; i < reachableAddresses.size(); i++) {
            if (reachableAddresses.get(i) != null && reachableAddresses.get(i).getIpAddress().equals(ip)) {
                return i;
            }
        }
        return -1;
    }

    public static List<ScannedDevice> getReachableAddresses() {
        return reachableAddresses;
    }

}
