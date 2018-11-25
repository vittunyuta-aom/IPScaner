import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private static List<ScannedDevice> reachableAddresses = new ArrayList<ScannedDevice>();

    public List<ScannedDevice> startScan() {
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

    // MAC Address
    private String findMacAddress(String str){
        int startMacIndex = indexOfWord(str,"at") + 3;
        int stopMacIndex = indexOfWord(str,"on");
        return str.substring(startMacIndex,stopMacIndex);
    }

    // IP Address
    private String findIpAddress(String str){
        int startIpIndex = indexOfWord(str,"(") + 1;
        int stopIpIndex = indexOfWord(str,")");
        return str.substring(startIpIndex,stopIpIndex);
    }

    private int indexOfWord(String str,String needle){
        if (str.contains(needle)) return str.indexOf(needle);
        System.out.println("not found " + needle);
        return -1;
    }
}
