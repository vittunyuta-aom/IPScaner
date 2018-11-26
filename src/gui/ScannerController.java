<<<<<<< HEAD:src/ScannerController.java
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
=======
package gui;

>>>>>>> 58a63d56729acec0fdc07569805d976ad6730695:src/gui/ScannerController.java
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

<<<<<<< HEAD:src/ScannerController.java
public class ScannerController implements Initializable{
=======

public class ScannerController {
>>>>>>> 58a63d56729acec0fdc07569805d976ad6730695:src/gui/ScannerController.java

    public ScannerController(){
    }

	@FXML
    private Button localDeviceButton;

    @FXML
    private Button timeButton;

    @FXML
    private AnchorPane localPane;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private AnchorPane timePane;
    
    @FXML
    private Text timeStart;

    @FXML
    private Text timeStop;

    
    
    public void LocalDevicesPage(ActionEvent event) {
		localPane.setVisible(true);
		timePane.setVisible(false);
	}
    
    public void TimePage(ActionEvent event) {
    	localPane.setVisible(false);
		timePane.setVisible(true);
	}
    
    public void Start(ActionEvent event){
    	timeStart.setText("12.22");
    	
    }
    
    public void Stop(ActionEvent event){
    	timeStop.setText("14.30");
    }
    
    public void Reset(ActionEvent event){
    	timeStart.setText("Time Start");
    	timeStop.setText("Time Stop");
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
