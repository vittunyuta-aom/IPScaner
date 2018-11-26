package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import scanners.Scanner;


public class ScannerController extends TimerTask implements Initializable{
	
	private static Scanner scanner = Scanner.instance();
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
//             timeStart.setText("12.22");
             timeStart.setText(scanner.getScannedTime().get(0));
         }
         else
             System.err.println("scanners.Scanner is scanning");
         
    	
    	
//    	List<DataTable> listPlayer = new ArrayList<DataTable>(data.pullAllUserdata());
//		VBox vBox = new VBox(listPlayer.size());
//		sortScore(listPlayer);
//		Label userLabel = null;
//		int index = 1;
//		for (DataTable dataTable : listPlayer) {
//			userLabel = new Label(String.format(" %2d. %-14s  %4d", index,dataTable.getUsername(),dataTable.getHighScore()));
//			userLabel.setFont(Font.font("Monospace",FontWeight.BOLD,20));
//			Background background = new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY));
//			userLabel.setBackground(background);
//			userLabel.setAlignment(Pos.TOP_CENTER);
//			vBox.setBackground(background);
//			vBox.getChildren().addAll(userLabel);
//			
//			index++;
//		}
//		vBoxScore.getChildren().addAll(vBox);
//		scroll.setContent(vBox);
//    	
    }
    
    public void Stop(ActionEvent event){
    	timeStop.setText("14.30");
//    	isScanning = false;
//        timer.cancel();
//        timer.purge();
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
        System.out.println("-------------------------------");
    }
	
	
}
