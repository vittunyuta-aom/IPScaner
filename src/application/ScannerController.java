package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
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
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ScannerController extends TimerTask implements Initializable{
	
	private Scanner scanner = Scanner.instance();
    private Timer timer = new Timer();
	private boolean isScanning = false;
	private List<ScannedDevice> listIP = scanner.mockscan();

    @FXML
    private AnchorPane localPane;
    
    @FXML
    private AnchorPane timePane;
    
    @FXML
    private Text timeStart;

    @FXML
    private Text timeStop;    

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
	
	@FXML
	private PieChart pieChart;
	    
    
    public void LocalDevicesPage(ActionEvent event) {
		localPane.setVisible(true);
		timePane.setVisible(false);
	}
    
    public void TimePage(ActionEvent event) {
    	localPane.setVisible(false);
		timePane.setVisible(true);
	}
    
    public void Start(ActionEvent event){
    	
         if(!isScanning){
             isScanning = true;
             timer.schedule(this, 0,5000);
             timeStart.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));         	
         }
         else
             System.err.println("scanners.Scanner is scanning");  
    	}
    
    public void Stop(ActionEvent event){
//    	timeStop.setText("14.30");
    	timeStop.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    	isScanning = false;
        timer.cancel();
        timer.purge();
    }
    
    public void Reset(ActionEvent event){
    	timeStart.setText("Time Start");
    	timeStop.setText("Time Stop");
    	timer.cancel();
    	ipBox.getItems().clear();
    	macBox.getItems().clear();
    	durationBox.getItems().clear();
    	firstBox.getItems().clear();
    	lastBox.getItems().clear();
    	
    }
    
    public void analyze(ActionEvent event){
    	   	ObservableList<Data> listData = FXCollections.observableArrayList(
    	   			new PieChart.Data(listIP.get(0).getLastTimeSeen(), 80),
    	   			new PieChart.Data("", 80)
    	   			);
    	   	pieChart.setData(listData);
    }

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}


    @Override
    public void run() {
//        scanner.scan();
    	
    	System.out.println(Arrays.toString(listIP.toArray()));    	
    	
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	ipBox.getItems().add(listIP.get(0).getIpAddress());
    	    	macBox.getItems().add(listIP.get(0).getMacAddress());
    	    	durationBox.getItems().add(listIP.get(0).getDuration());
    	    	firstBox.getItems().add(listIP.get(0).getFirstTimeSeen());
    	    	lastBox.getItems().add(listIP.get(0).getLastTimeSeen());
    	    }
    	});    	
    	
        System.out.println("-------------------------------");
    }
	
	
}
