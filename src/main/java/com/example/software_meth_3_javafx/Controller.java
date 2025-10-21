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
    @FXML
    private ChoiceBox<String> printReservationsDropDown;
    @FXML
    private ChoiceBox<String> printCompletedTripsDropDown;

    public void initialize(){
        //For Vehicle Management
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

    /**
     * Checks for valid date for adding to fleet.
     *
     * @param parts commandline tokens
     * @param today today's date
     * @return true if all checks pass; otherwise false with an error message
     */
    private boolean addCheckDate(String[] parts, Date today){
        Date isValidDate = new Date(parts[2]);
        if(!isValidDate.isValid()){
            System.out.println(parts[2] + " - invalid calendar date.");
            return false;
        }
        else if(isValidDate.compareTo(today) >= 0){
            System.out.println(parts[2] + " - is today or a future date.");
            return false;
        }
        return true;
    }

    /**
     * Checks for valid make for adding to fleet.
     *
     * @param parts commandline tokens
     * @param make vehicle's make
     * @return true if all checks pass; otherwise false with an error message
     */
    private boolean addCheckMake(String[] parts, Make make){
        if(make == null){
            System.out.println(parts[3] + " - invalid make.");
            return false;
        }
        return true;
    }

    /**
     * Checks for valid mileage for adding to fleet.
     *
     * @param parts commandline tokens
     * @return true if all checks pass; otherwise false with an error message
     */
    private boolean addCheckMileage(String[] parts){
        int odo;
        try {
            odo = Integer.parseInt(parts[4]);
        } catch (NumberFormatException e) {
            System.out.println("For input string: \"" + parts[4] + "\" - not a valid mileage.");
            return false;
        }
        if (!isValidOdometer(odo)) {
            System.out.println(parts[4] + " - invalid mileage.");
            return false;
        }

        return true;

    }

    /**
     * Checks for valid tokens for adding to fleet.
     *
     * @param parts commandline tokens
     * @return true if all checks pass; otherwise false with an error message
     */
    private boolean addCheckTokens(String[] parts){
        if(parts[1].length() != 6){
            System.out.println(parts[1] + " - license plate number must be exactly 6 characters.");
            return false;
        }
        for (int i = 0; i < parts[1].length(); i++) {
            if (i < 5) {
                if (!Character.isDigit(parts[1].charAt(i))) {
                    System.out.println(parts[1] + " - first 5 characters must be numbers.");
                    return false;
                }
                continue;
            }
            char vehicleCheck = parts[1].charAt(5);
            if (!(vehicleCheck == 'D' || vehicleCheck == 'S' || vehicleCheck == 'X')) {
                System.out.println(parts[1] + " - last character is not a valid vehicle type.");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for valid campus for adding to fleet.
     *
     * @param parts commandline tokens
     * @param campus campus
     * @return true if all checks pass; otherwise false with an error message
     */
    private boolean addCheckCampus(String[] parts, Campus campus){
        if(campus == null){
            System.out.println(parts[5] + " - invalid location.");
            return false;
        }
        return true;
    }

    /**
     * Adds a vehicle to the fleet.
     *
     * @param parts tokenized input (must be length 5)
     */
    private void addToFleet(String[] parts){
        if(!checkCommandParse(parts, 6, "A")){
            return;
        }
        Date today = getToday();
        Make make = Make.parseMake(parts[3]);
        Campus campus = Campus.parseCampus(parts[5]);
        if(!addCheckDate(parts, today) || !addCheckMake(parts, make) || !addCheckMileage(parts)
                || !addCheckTokens(parts) || !addCheckCampus(parts, campus)){
            return;
        }
        Date isValidDate = new Date(parts[2]);
        int odo = Integer.parseInt(parts[4]);
        Vehicle vehicle = createVehicle(parts[1], isValidDate, make, odo, campus);
        fleet.add(vehicle);
        System.out.println(vehicle.toString() + " has been added to the fleet.");
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
