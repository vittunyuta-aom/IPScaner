package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import scanners.ScannedDevice;
import scanners.Scanner;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ScannerController extends TimerTask implements Initializable{
	
	private Scanner scanner = Scanner.instance();
    private Timer timer = new Timer();
	private boolean isScanning = false;
	
	@FXML
    private Button localDeviceButton;

    @FXML
    private Button timeButton;

    @FXML
    private AnchorPane localPane;

    @FXML
    private Button startButton;
    
    @FXML
    private Button resetButton;

    @FXML
    private Button stopButton;

    @FXML
    private AnchorPane timePane;
    
    @FXML
    private Text timeStart;

    @FXML
    private Text timeStop;
    
    @FXML
	private ScrollPane scroll;

	@FXML
	private VBox vBoxScore;
	
	@FXML
    private AnchorPane data;
	

    
    
    public void LocalDevicesPage(ActionEvent event) {
		localPane.setVisible(true);
		timePane.setVisible(false);
	}
    
    public void TimePage(ActionEvent event) {
    	localPane.setVisible(false);
		timePane.setVisible(true);
	}
    
    public void Start(ActionEvent event){
    	
//    	 System.out.println(isScanning);
         if(!isScanning){
             isScanning = true;
             timer.schedule(this, 0,5000);
             timeStart.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
             
             List<ScannedDevice> listPlayer = new ArrayList<ScannedDevice>();
     		VBox vBox = new VBox(listPlayer.size());
     		
     		int index = 1;
     		for (ScannedDevice lists : listPlayer) {
//     			timeStart = new Text(String.format(" %2d. %-14s  %4d" );
//     			timeStart.setFont(Font.font("Monospace",FontWeight.BOLD,20));
//     			Background background = new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY));
//     			timeStart.setBackground(background);
//     			timeStart.setAlignment(Pos.TOP_CENTER);
//     			vBox.setBackground(background);
//     			vBox.getChildren().addAll(userLabel);
     			
     			index++;
     		}
     		vBoxScore.getChildren().addAll(vBox);
     		scroll.setContent(vBox);
         	
         }
         else
             System.err.println("scanners.Scanner is scanning");
         
    	
    	
    	
    }
    
    public void Stop(ActionEvent event){
    	timeStop.setText("14.30");
    	isScanning = false;
        timer.cancel();
        timer.purge();
    }
    
    public void Reset(ActionEvent event){
    	timeStart.setText("Time Start");
    	timeStop.setText("Time Stop");
    	timer.cancel();
    	
    }

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}


    @Override
    public void run() {
//        scanner.scan();
    	List<ScannedDevice> list = scanner.mockscan();
    	System.out.println(Arrays.toString(list.toArray()));
    	list.get(0).getIpAddress();
    	list.get(0).getMacAddress();
        System.out.println("-------------------------------");
    }
	
	
}
