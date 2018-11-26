import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScannedDevice {
    private final DateFormat DATETIMEFORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");
    private String ipAddress;
    private String macAddress;
    private Date firstSeen;
    private Date lastSeen;
    private String firstTimeSeen;
    private String lastTimeSeen;
    private long duration;


    public ScannedDevice(String ipAddress, String macAddress){
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.firstSeen = new Date();
        this.firstTimeSeen = TIMEFORMAT.format(firstSeen);
        setLastSeenToNow();
        this.duration = 0;
    }

    public void updateData(){
        setLastSeenToNow();
        calculateCurrentDuration();
    }

    private void setLastSeenToNow(){
        this.lastSeen = new Date();
        this.lastTimeSeen = TIMEFORMAT.format(lastSeen);
    }

    private void calculateCurrentDuration(){
        duration = Math.abs(lastSeen.getTime() - firstSeen.getTime());
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public String getFirstTimeSeen() {
        return firstTimeSeen;
    }

    public String getLastTimeSeen() {
        return lastTimeSeen;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("ip address: %s, mac address: %s, first seen: %s, last seen: %s, first time seen: %s, last time seen: %s, duration: %s \n",this.ipAddress,this.macAddress, DATETIMEFORMAT.format(this.firstSeen), DATETIMEFORMAT.format(this.lastSeen), firstTimeSeen, lastTimeSeen, duration);
    }
}
