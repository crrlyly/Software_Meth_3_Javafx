package com.example.software_meth_3_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import com.example.software_meth_3_javafx.fleet.*;
import com.example.software_meth_3_javafx.util.*;


public class Controller {

    @FXML private TextField licensePlateInput;
    @FXML private DatePicker dateInput;
    @FXML private RadioButton fordButton;
    @FXML private RadioButton toyotaButton;
    @FXML private RadioButton hondaButton;
    @FXML private RadioButton chevyButton;
    @FXML private ChoiceBox<Employee> employeeDropDown;
    @FXML private ChoiceBox<Campus> campusDropDown;
    @FXML private Button AddBtn;
    @FXML private ChoiceBox<String> printReservationsDropDown;
    @FXML private ChoiceBox<String> printCompletedTripsDropDown;
    private ToggleGroup vehicleManagementGroup;

    public void initialize(){
        //For Vehicle Management
        setToggleGroupVehicleManagement();
        initializeEmployeeDropDown();
        initializeCampusDropDown();

        //For Vehicle Reservation

        //For Vehicle Return

        //For Vehicle Report
        initializePrintReservationsDropDown();
        initializePrintCompletedTrips();
    }


    // VEHICLE MANAGEMENT -----------------------------------------------------------------------------
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
                System.out.println("Selected: " + newVal);
            }
        });
    }

    @FXML
    private void setToggleGroupVehicleManagement(){
        vehicleManagementGroup = new ToggleGroup();
        fordButton.setToggleGroup(vehicleManagementGroup);
        toyotaButton.setToggleGroup(vehicleManagementGroup);
        hondaButton.setToggleGroup(vehicleManagementGroup);
        chevyButton.setToggleGroup(vehicleManagementGroup);
    }



    //add the importFileChooser method she has in modules

    // VEHICLE RESERVATION -----------------------------------------------------------------------------

    // VEHICLE RETURN -----------------------------------------------------------------------------

    // VEHICLE REPORT -----------------------------------------------------------------------------
    @FXML
    private void initializePrintReservationsDropDown(){
        String[] options = {"by department/employee (PD)", "by location/plate/beginning date (PR)"};
        printReservationsDropDown.getItems().addAll(options);
        printReservationsDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected: " + newVal);

//                switch (newVal) {
//                    case "by department/employee (PD)":
//                        handlePrintByDepartment();
//                        break;
//
//                    case "by location/plate/beginning date (PR)":
//                        handlePrintByLocation();
//                        break;
//
//                    default:
//                        System.out.println("Unknown selection");
//                }
            }
        });
    }

    @FXML
    private void initializePrintCompletedTrips(){
        String[] options = {"by plate/ending date (PT)", "cost report by department (PC)"};
        printCompletedTripsDropDown.getItems().addAll(options);
        printCompletedTripsDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected: " + newVal);

//                switch (newVal) {
//                    case "by department/employee (PD)":
//                        handlePrintByDepartment();
//                        break;
//
//                    case "by location/plate/beginning date (PR)":
//                        handlePrintByLocation();
//                        break;
//
//                    default:
//                        System.out.println("Unknown selection");
//                }
            }
        });
    }
}
