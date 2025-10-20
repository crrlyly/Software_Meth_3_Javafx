package com.example.software_meth_3_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;



//For all the event calls

public class Controller {
    //will run automatically once loaded
    public void initialize(){}

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //add the importFileChooser method she has in modules
}
