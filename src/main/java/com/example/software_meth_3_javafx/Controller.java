package com.example.software_meth_3_javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import com.example.software_meth_3_javafx.fleet.*;
import com.example.software_meth_3_javafx.util.*;
import javafx.scene.layout.GridPane;

//test branching

//SHOW UP

public class Controller {

    @FXML private TextField licensePlateInput;
    @FXML private DatePicker dateInput;
    @FXML private RadioButton fordButton;
    @FXML private RadioButton toyotaButton;
    @FXML private RadioButton hondaButton;
    @FXML private RadioButton chevyButton;
    @FXML private ComboBox<Campus> campusDropDown;
    @FXML private TextField mileageTextField;
    @FXML private Button removeBtn;
    @FXML private TextArea outputTextAreaManagement;
    @FXML private GridPane addGrid;

    @FXML private ComboBox<String> printReservationsDropDown;
    @FXML private ComboBox<String> printCompletedTripsDropDown;
    @FXML private TextArea outputTextAreaReport;

    private ToggleGroup makeGroup;

    private Fleet fleet;
    private Reservation reservation;
    private TripList tripList;

    public void initialize(){
        //initializing the lists
        initLists();

        //For Vehicle Management
        initVehicleManagement();

        //For Vehicle Reservation

        //For Vehicle Return

        //For Vehicle Report
        initVehicleReport();
    }

    private void initLists(){
        fleet = new Fleet();
        reservation = new Reservation();
        tripList = new TripList();
    }

    @FXML
    private void initVehicleManagement(){
        removeBtn.getTooltip().setShowDelay(javafx.util.Duration.millis(100));
        initializeToggleGroupVehicleManagement();
        initializeCampusDropDown();
        dateInput.setEditable(false);
    }

    @FXML
    private void initVehicleReport(){
        initializePrintReservationsDropDown();
        initializePrintCompletedTrips();
        handlePrintReservationsDropDownClick();
        handlePrintCompletedTripsDropDownBtnClick();
    }

    //general methods to check elements if empty
    @FXML
    private boolean checkIfEmptyTextField(TextField input){
        return input.getText() == null || input.getText().trim().isEmpty();
    }

    @FXML
    private boolean checkIfEmptyDatePicker(DatePicker date){
        return date.getEditor().getText().isBlank();
    }

    @FXML
    private boolean checkIfRadioBtnSelected(){
        return !fordButton.isSelected() && !toyotaButton.isSelected() && !hondaButton.isSelected()
                && !chevyButton.isSelected();
    }

    @FXML
    private <E> boolean checkIfDropDownSelected(ComboBox<E> choice){
        return choice.getValue() == null;
    }

    @FXML
    private void clearInputs() {
        for (Node node : addGrid.getChildren()) {
            if (node instanceof TextField textField) {
                textField.clear();
            }
            else if (node instanceof DatePicker datePicker) {
                if (datePicker.valueProperty().isBound()) datePicker.valueProperty().unbind();
                datePicker.setValue(null);
                datePicker.getEditor().clear();
            }
            else if (node instanceof ComboBox<?> comboBox) {
                comboBox.setValue(null);
            }
            else if (node instanceof RadioButton radioButton) {
                radioButton.setSelected(false);
            }
            else if (node instanceof TextArea textArea) {
                textArea.clear();
            }
        }
        if (makeGroup != null) makeGroup.selectToggle(null);
    }


    // VEHICLE MANAGEMENT -----------------------------------------------------------------------------
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
    private void initializeToggleGroupVehicleManagement(){
        makeGroup = new ToggleGroup();
        fordButton.setToggleGroup(makeGroup);
        toyotaButton.setToggleGroup(makeGroup);
        hondaButton.setToggleGroup(makeGroup);
        chevyButton.setToggleGroup(makeGroup);
    }

    @FXML
    private boolean checkFieldsEmpty(){
        return checkIfEmptyTextField(licensePlateInput) ||
                checkIfEmptyDatePicker(dateInput) ||
                checkIfRadioBtnSelected() ||
                checkIfDropDownSelected(campusDropDown) ||
                checkIfEmptyTextField(mileageTextField);
    }

    @FXML
    private boolean addCheckDate(String date, Date today){
        Date isValidDate = new Date(date);
        if(!isValidDate.isValid()){
            outputTextAreaManagement.setText(date + " - invalid calendar date.");
            return false;
        }
        else if(isValidDate.compareTo(today) >= 0){
            outputTextAreaManagement.setText(date + " - is today or a future date.");
            return false;
        }
        return true;
    }

    @FXML
    private boolean addCheckMileage(String mileage){
        int odo;
        try {
            odo = Integer.parseInt(mileage);
        } catch (NumberFormatException e) {
            outputTextAreaManagement.setText("For input string: \"" + mileage + "\" - not a valid mileage.");
            return false;
        }
        if (!isValidOdometer(odo)) {
            outputTextAreaManagement.setText(mileage + " - invalid mileage.");
            return false;
        }
        return true;
    }

    private boolean isValidOdometer(int odo) {
        try {
            return odo > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private boolean addCheckTokens(String licensePlate){
        if(licensePlate.length() != 6){
            outputTextAreaManagement.setText(licensePlate + " - license plate number must be exactly 6 characters.");
            return false;
        }
        for (int i = 0; i < licensePlate.length(); i++) {
            if (i < 5) {
                if (!Character.isDigit(licensePlate.charAt(i))) {
                    outputTextAreaManagement.setText(licensePlate + " - first 5 characters must be numbers.");
                    return false;
                }
                continue;
            }
            char vehicleCheck = licensePlate.charAt(5);
            if (!(vehicleCheck == 'D' || vehicleCheck == 'S' || vehicleCheck == 'X')) {
                outputTextAreaManagement.setText(licensePlate + " - last character is not a valid vehicle type.");
                return false;
            }
        }
        return true;
    }

    @FXML
    private Vehicle createVehicle(String plate, Date date, Make make, Integer odo, Campus campus){
        switch(plate.charAt(plate.length() - 1)){
            case 'D':
                return odo != null ? new Utility(plate,date,make,odo,campus) : new Utility(plate);
            case 'X':
                return odo != null ? new Truck(plate,date,make,odo,campus) : new Truck(plate);
            case 'S':
                return odo != null ? new Sedan(plate,date,make,odo,campus) : new Sedan(plate);
            default:
                outputTextAreaManagement.setText("Unknown vehicle type: " + plate);
                return null;
        }
    }

    @FXML
    private void handleClearBtnClick(){
        clearInputs();
    }

    @FXML
    private void handleAddBtnClick(){
        outputTextAreaManagement.clear();
        if (checkFieldsEmpty()) {
            outputTextAreaManagement.setText("Missing input. Please try again.");
            return;
        }
        Date today = Date.getToday();
        RadioButton selected = (RadioButton) makeGroup.getSelectedToggle();
        Make make = Make.parseMake(selected.getText());
        Campus campus = Campus.parseCampus(campusDropDown.getValue().name());
        if(!addCheckDate(dateInput.getEditor().getText(), today) || !addCheckMileage(mileageTextField.getText())
                || !addCheckTokens(licensePlateInput.getText())){
            //clearInputs();
            return;
        }

        Date isValidDate = new Date(dateInput.getEditor().getText());
        int odo = Integer.parseInt(mileageTextField.getText());
        Vehicle vehicle = createVehicle(licensePlateInput.getText(), isValidDate, make, odo, campus);
        fleet.add(vehicle);
        outputTextAreaManagement.setText(vehicle.toString() + " has been added to the fleet.");
    }

    @FXML
    private boolean removeChecks(Vehicle vehicle, Booking booking){
        if(!fleet.contains(vehicle)){
            outputTextAreaManagement.setText(vehicle.getPlate() + " is not in the fleet.");
            return false;
        }
        if(reservation.findInstanceOfVehicleOnlyPlate(booking)){
            outputTextAreaManagement.setText(vehicle.getPlate() + " - has existing bookings; cannot be removed.");
            return false;
        }
        return true;
    }

    @FXML
    private void handleRemoveBtnClick(){
        outputTextAreaManagement.clear();
        if (checkIfEmptyTextField(licensePlateInput)) {
            outputTextAreaManagement.setText("Missing license plate. Please try again.");
            return;
        }
        Vehicle vehicle = createVehicle(licensePlateInput.getText(), null, null, null, null);
        Booking booking = new Booking(vehicle);
        if(!removeChecks(vehicle, booking)){
            return;
        }
        Vehicle getOriginalVehicle = fleet.getVehicle(vehicle);
        outputTextAreaManagement.setText(getOriginalVehicle.getPlate() + " has been removed from the fleet.");
        fleet.remove(vehicle);
    }


    // VEHICLE RESERVATION -----------------------------------------------------------------------------

    //add the importFileChooser method she has in modules to load in vehicles.txt

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

    @FXML
    private void handlePrintReservationsDropDownClick(){
        printReservationsDropDown.setOnAction(e -> {
            String choice = printReservationsDropDown.getValue();
            if (choice.equals("by department/employee (PD)")){
                displayBookings2();
            }
            else if(choice.equals("by location/plate/beginning date (PR)")){
                displayBookings1();
            }
        });
        printReservationsDropDown.setValue(null);
    }

    @FXML
    private void handlePrintFleetBtnClick(){
        displayVehicles();
    }

    @FXML
    private void handlePrintCompletedTripsDropDownBtnClick(){
        printCompletedTripsDropDown.setOnAction(e -> {
            String choice = printCompletedTripsDropDown.getValue();
            if (choice.equals("by plate/ending date (PT)")){
                displayCompletedTrips();
            }
            else if(choice.equals("cost report by department (PC)")){
                displayCostReport();
            }
        });
        printCompletedTripsDropDown.setValue(null);
    }

    @FXML
    private void displayVehicles(){
        if(fleet.Size() == 0){
            outputTextAreaReport.setText("There is no vehicle in the fleet.");
            return;
        }
        outputTextAreaReport.setText("*List of vehicles in the fleet, ordered by location/make/date obtained.\n");
        outputTextAreaReport.appendText(fleet.printByMake());
        outputTextAreaReport.appendText("*end of list.");
    }

    @FXML
    private void displayBookings1(){
        if(reservation.Size() == 0){
            outputTextAreaReport.setText("There is no booking record.");
            return;
        }
        outputTextAreaReport.setText("*List of reservations ordered by location/license plate/beginning date.\n");
        outputTextAreaReport.appendText(reservation.printByVehicle());
        outputTextAreaReport.appendText("*end of list.");
    }

    @FXML
    private void displayBookings2(){
        if(reservation.Size() == 0){
            outputTextAreaReport.setText("There is no booking record.");
            return;
        }
        outputTextAreaReport.setText("*List of reservations ordered by department and employee.\n");
        outputTextAreaReport.appendText(reservation.printByDept());
        outputTextAreaReport.appendText("*end of list.");
    }

    @FXML
    private void displayCompletedTrips(){
        if(tripList.getLast() == null){
            outputTextAreaReport.setText("There is no completed trips.");
            return;
        }
        outputTextAreaReport.setText("*List of completed trips ordered by license plate and ending date.\n");
        tripList.print();
        outputTextAreaReport.appendText("*end of list.");
    }

    @FXML
    private void displayCostReport(){
        if(tripList.getLast() == null){
            outputTextAreaReport.setText("There is no archived trips for the cost report.");
            return;
        }
        outputTextAreaReport.setText(tripList.printCharges());
    }
}
