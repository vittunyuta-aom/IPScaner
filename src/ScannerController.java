import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ScannerController implements Initializable{

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
