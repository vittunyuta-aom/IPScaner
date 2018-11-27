package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import scanners.ScannedDevice;
import scanners.Scanner;

import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.org.apache.bcel.internal.generic.NEW;


public class ScannerController extends TimerTask implements Initializable{
	
	private Scanner scanner = Scanner.instance();
    private Timer timer;
	private boolean isScanning = false;
	private List<ScannedDevice> listIP;

    @FXML
    private AnchorPane localPane;
    
    @FXML
    private AnchorPane timePane;
    
    @FXML
    private Text timeStart;

    @FXML
    private Text timeStop;

	@FXML
	private Button stopButton;

	@FXML
	private Button startButton;

	@FXML
	private Button resetButton;

	@FXML
	private HBox hBoxData;
	
	@FXML
	private ScrollPane scroll;
	
	@FXML
	private ListView<String> ipBox;
	
	@FXML
	private ListView<String> macBox;
	
	@FXML
	private ListView<Long> durationBox;
	
	@FXML
	private ListView<String> firstBox;
	
	@FXML
	private ListView<String> lastBox;	    
    
    public void LocalDevicesPage(ActionEvent event) {
		localPane.setVisible(true);
		timePane.setVisible(false);
	}
    
    public void TimePage(ActionEvent event) {
    	localPane.setVisible(false);
		timePane.setVisible(true);
	}
    
    public void Start(ActionEvent event){
		System.out.println("Start");
		timer = new Timer();
    	stopButton.setDisable(false);
         if(!isScanning){
             isScanning = true;
             timer.schedule(this, 0,5000);
             timeStart.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));         	
         }
         else
             System.err.println("scanners.Scanner is scanning");  
    	}
    
    public void Stop(ActionEvent event){
		System.out.println("Stop");
    	timeStop.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    	isScanning = false;
        timer.cancel();
        timer.purge();
    }
    
    public void Reset(ActionEvent event){
		stopButton.setDisable(true);
    	timeStart.setText("Time Start");
    	timeStop.setText("Time Stop");
//    	timer.cancel();
    	resetTable();
    	
    }
    
    public void resetTable(){
		ipBox.getItems().clear();
		macBox.getItems().clear();
		durationBox.getItems().clear();
		firstBox.getItems().clear();
		lastBox.getItems().clear();
    }
    
    public void analyze(ActionEvent event){
    	CategoryAxis xAxis = new CategoryAxis();
    	xAxis.setLabel("Last seen");
    	NumberAxis yAxis = new NumberAxis();
    	yAxis.setLabel("IP addresses");
    	
    	BarChart barChart = new BarChart(xAxis, yAxis);
    	XYChart.Series series = new XYChart.Series();
    	
    	Map<String, Integer> timeAndNumber = scanner.getScannedTime();
    	List<String> timeKey = new ArrayList<String>(timeAndNumber.keySet());
    	for(int i = 0; i< timeAndNumber.size(); i++){
    		String key = timeKey.get(i);
    		System.out.println(key + " -> " + timeAndNumber.get(key) );
    		series.getData().add(new XYChart.Data<String, Integer>(key, timeAndNumber.get(key)));
    	}
    	 
    }

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stopButton.setDisable(true);
	}


    @Override
    public void run() {
//        scanner.scan();
    	
    	listIP = scanner.mockscan();
    	
    	System.out.println(Arrays.toString(listIP.toArray()));    	
    	
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	resetTable();
    	    	for(ScannedDevice listData : listIP){
    	    		ipBox.getItems().add(listData.getIpAddress());
        	    	macBox.getItems().add(listData.getMacAddress());
        	    	durationBox.getItems().add(listData.getDuration());
        	    	firstBox.getItems().add(listData.getFirstTimeSeen());
        	    	lastBox.getItems().add(listData.getLastTimeSeen());
    	    	}
    	    	
    	    }
    	});    	
    	
        System.out.println("-------------------------------");
    }
	
	
}
