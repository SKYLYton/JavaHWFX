package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;

    public void onClickBtn(ActionEvent actionEvent) {
        printMessage("Яков", textField.getText());
    }

    private void printMessage(String name, String message){
        if(!message.isEmpty() && !name.isEmpty()){

            textArea.appendText(name + ": " +textField.getText() + " " + getTime() + "\n");
            textField.setText("");
            textField.requestFocus();

        }
    }

    private String getTime(){
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return "(" + formatForDateNow.format(date) + ")";
    }

    public void onKeyPrsd(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            printMessage("Яков", textField.getText());
        }
    }
}
