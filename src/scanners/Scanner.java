package scanners;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Scanner{
    private static Map<String, Integer> scannedTime = new LinkedHashMap<String, Integer>();
    private static List<ScannedDevice> reachableAddresses = new ArrayList<ScannedDevice>();
    private final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");
    
    private static Scanner scanner = new Scanner();

    private Scanner(){}

    public static Scanner instance(){
        return scanner;
    }
    public Map<String, Integer> getScannedTime() {
		return scannedTime;
	}

    public List<ScannedDevice> mockscan() {
        Date now = new Date();
        
    	int index = containsIpWithIndex("12.0.3.45");
        if (index == -1)
        	reachableAddresses.add(new ScannedDevice("12.0.3.45", "as:sd:df:fg:gh:gg", now));
        else
            reachableAddresses.get(index).updateData(now);
        scannedTime.put(TIMEFORMAT.format(now),reachableAddresses.size());
        System.out.println(scannedTime.size());
    	return reachableAddresses;
    }
    
    public List<ScannedDevice> scan() {
        Date currentTimeScan = new Date();

        try {
//        	ProcessBuilder builder = new ProcessBuilder(
//                    "cmd.exe", "/c", "arp -a");
//            builder.redirectErrorStream(true);
//            Process proc = builder.start();
            Process proc = Runtime.getRuntime().exec("arp -a");

//            Thread.sleep(5000);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String err;
            while ((err = stdError.readLine()) != null) {
            	System.out.println(err);
            }
            // read the output from the command
            String str;
            while ((str = stdInput.readLine()) != null) {
                System.out.println(">>>>" + str);
                
             // Window
                if (str.contains("Interface") || str.contains("Internet")) continue;
                String ip = findIpAddressWin(str);
                if(ip == null) continue;
                int index = containsIpWithIndex(ip);
	            if (index == -1)
	            	reachableAddresses.add(new ScannedDevice(ip, findMacAddressWin(str), currentTimeScan));
	            else
	                reachableAddresses.get(index).updateData(currentTimeScan);
                
                // Mac
//                String ip = findIpAddress(str);
//                int index = containsIpWithIndex(ip);
//                if (index == -1)
//                    reachableAddresses.add(new ScannedDevice(ip, findMacAddress(str), currentTimeScan));
//                else
//                    reachableAddresses.get(index).updateData(currentTimeScan);

                // read any errors from the attempted command
                while ((str = stdError.readLine()) != null) {
                    System.err.println(str);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        scannedTime.put(TIMEFORMAT.format(currentTimeScan),reachableAddresses.size());

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
    
 // MAC Address
    private String findMacAddressWin(String str) {
        int startMacIndex =  24;
        int stopMacIndex = 41;
        return str.substring(startMacIndex, stopMacIndex);
    }

    // IP Address
    private String findIpAddressWin(String str) {
    	
        int startIpIndex =  2;
        int stopIpIndex = -1;
        for (int i = 1; i <= 3; i++){
        	stopIpIndex = str.indexOf(" ", stopIpIndex + 1);
        }
        if(stopIpIndex < 0 || startIpIndex > stopIpIndex)
        	return null;
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

    public void resetAddressList(){
        reachableAddresses = new ArrayList<ScannedDevice>();
    }

    public void resetTimeList(){
        scannedTime = new LinkedHashMap<String, Integer>();
    }

}
