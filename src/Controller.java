import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private ComboBox<String> connectionsComboBox;

    @FXML
    private Button refreshButton;

    @FXML
    private Button connectButton;

    @FXML
    private Button onButton;

    @FXML
    private Button offButton;

    @FXML
    private Text connectedLabel;

    Arduino arduino;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectedLabel.setVisible(false);
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        for (SerialPort serialPort : serialPorts) {
            connectionsComboBox.getItems().add(serialPort.getSystemPortName());
        }

        connectButton.setOnAction(e -> {
            arduino = new Arduino(connectionsComboBox.getSelectionModel().getSelectedItem(), 9600);
            if(arduino.openConnection()) {
                connectedLabel.setVisible(true);
                connectButton.setDisable(true);
                refreshButton.setDisable(true);
                System.out.println("Successfully connected!!");
            }
        });

        onButton.setOnAction(e -> {
            arduino.serialWrite('1');
        });

        offButton.setOnAction(e -> {
            arduino.serialWrite('0');
        });
    }
}
