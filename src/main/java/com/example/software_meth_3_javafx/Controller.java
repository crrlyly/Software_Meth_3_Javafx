package com.example.software_meth_3_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import com.example.software_meth_3_javafx.fleet.*;
import com.example.software_meth_3_javafx.util.*;


public class Controller {

    @FXML
    private ChoiceBox<Employee> employeeDropDown;
    @FXML
    private ChoiceBox<Campus> campusDropDown;

    public void initialize(){
        initializeEmployeeDropDown();
        initializeCampusDropDown();
    }

    @FXML
    private void initializeEmployeeDropDown(){
        employeeDropDown.getItems().addAll(Employee.values());
        employeeDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected: " + newVal.name() + " (" + newVal.getDept() + ")");
            }
        });
    }

    @FXML
    private void initializeCampusDropDown(){
        campusDropDown.getItems().addAll(Campus.values());
        campusDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected: " + newVal.name() + " (" + newVal.getCity() + ")");
            }
        });
    }

    //add the importFileChooser method she has in modules
}
