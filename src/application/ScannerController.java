package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import scanners.ScannedDevice;
import scanners.Scanner;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class ScannerController implements Initializable{
	
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
	private Button analyzeButton;

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
	
	@FXML
	private BarChart<String,Integer> barChart;
    
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
		reTextTime();
		barChart.getData().clear();
    	stopButton.setDisable(false);
    	analyzeButton.setDisable(true);
    	scanner.resetTimeList();
    	 timer = new Timer();
         if(!isScanning){
             isScanning = true;
             timer.schedule(new myTimerTask(), 0,5000);
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
        scanner.resetAddressList();
        analyzeButton.setDisable(false);
    }
    
    public void Reset(ActionEvent event){
		stopButton.setDisable(true);
		Stop(event);
    	reTextTime();
    	resetTable();
    	
    }
    
    public void reTextTime(){
    	timeStart.setText("Time Start");
    	timeStop.setText("Time Stop");
    }
    
    public void resetTable(){
		ipBox.getItems().clear();
		macBox.getItems().clear();
		durationBox.getItems().clear();
		firstBox.getItems().clear();
		lastBox.getItems().clear();
    }
    
    public void analyze(ActionEvent event){
    	if(isScanning){
    		analyzeButton.setDisable(true);
    		System.out.println("disable");
    	}
    	System.out.println("analyze");
		barChart.getData().clear();

    	XYChart.Series<String,Integer> series = new XYChart.Series<String,Integer>();    	
    	Map<String, Integer> timeAndNumber = scanner.getScannedTime();
    	List<String> timeKey = new ArrayList<String>(timeAndNumber.keySet());
    	System.out.println(timeAndNumber.size());
    	for(int i = 0; i< timeAndNumber.size(); i++){
    		String key = timeKey.get(i);
    		System.out.println(key + " -> " + timeAndNumber.get(key) );
    		series.getData().add(new XYChart.Data<String, Integer>(key, timeAndNumber.get(key)));
    	}
    	barChart.getData().addAll(series);

        // IP vs Duration
//        XYChart.Series<String,Integer> series2 = new XYChart.Series<String,Integer>();
//        for(int i = 0; i< listIP.size(); i++){
//            ScannedDevice scd = listIP.get(i);
//            int duration = (int) scd.getDuration();
//            System.out.println(scd.getIpAddress() + " -> " + duration );
//            series.getData().add(new XYChart.Data<String, Integer>(scd.getIpAddress(), duration));
//        }
//        barChart.getData().addAll(series2);



    }

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stopButton.setDisable(true);
		analyzeButton.setDisable(true);
		
	}

	class myTimerTask extends TimerTask{
		@Override
		public void run() {
        listIP = scanner.scan();

//			listIP = scanner.mockscan();

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
	
}
