import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScannedDevice {
    private final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String ipAddress;
    private String macAddress;
    private Date lastScan;

    public ScannedDevice(String ipAddress, String macAddress){
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.lastScan = new Date();
    }

    @Override
    public String toString() {
        return String.format("ip address: %s, mac address: %s, last scan: %s",this.ipAddress,this.macAddress, DATEFORMAT.format(this.lastScan));
    }
}
