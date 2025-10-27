package com.example.software_meth_3_javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import com.example.software_meth_3_javafx.fleet.*;
import com.example.software_meth_3_javafx.util.*;
import javafx.scene.layout.GridPane;
import java.io.File;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The class implements the api calls for our Vehicle Management System.
 *
 * @author Carly Chick and Alec Cotler
 */
public class Controller {

    @FXML private TextField licensePlateInput;
    @FXML private DatePicker dateInput;
    @FXML private DatePicker startDateInput;
    @FXML private DatePicker endDateInput;

    @FXML private RadioButton fordButton;
    @FXML private RadioButton toyotaButton;
    @FXML private RadioButton hondaButton;
    @FXML private RadioButton chevyButton;
    @FXML private ComboBox<Campus> campusDropDown;
    @FXML private ComboBox<Campus> campusDropDown2;
    @FXML private ComboBox<Campus> returnCampus;
    @FXML private ComboBox<Vehicle> vehicleDropDownFromFleet;
    @FXML private ComboBox<Booking> vehicleDropDownFromReservation;
    @FXML private ComboBox<Employee> employeeDropDown;
    @FXML private TextField mileageTextField;
    @FXML private TextField mileageTextFieldReservation;

    @FXML private Button removeBtn;
    @FXML private Button bookButton;
    @FXML private Button cancelButton;
    @FXML private Button fileLoadButton;
    @FXML private Button completeTripButton;
    @FXML private TextArea outputTextAreaManagement;
    @FXML private TextArea outputTextAreaManagement2;
    @FXML private TextArea outputTextAreaManagement3;

    @FXML private GridPane addGrid;
    @FXML private ComboBox<String> printReservationsDropDown;
    @FXML private ComboBox<String> printCompletedTripsDropDown;
    @FXML private TextArea outputTextAreaReport;


    private ToggleGroup makeGroup;
    private Fleet fleet;
    private Reservation reservation;
    private TripList tripList;

    /**
     * Main method that initializes elements when UI first starts up.
     */
    public void initialize(){
        //initializing the lists
        initLists();

        //For Vehicle Management
        initVehicleManagement();

        //For Vehicle Reservation
        initVehicleReservation();
        //For Vehicle Return
        initVehicleReturn();
        //For Vehicle Report
        initVehicleReport();
    }

    /**
     * Method that initializes the lists that are used.
     */
    private void initLists(){
        fleet = new Fleet();
        reservation = new Reservation();
        tripList = new TripList();
    }

    /**
     * Method that initializes elements in the Vehicle Management tab.
     */
    @FXML
    private void initVehicleManagement(){
        removeBtn.getTooltip().setShowDelay(javafx.util.Duration.millis(100));
        initializeToggleGroupVehicleManagement();
        initializeCampusDropDown();
        dateInput.setEditable(false);
    }

    /**
     * Method that initializes elements in the Vehicle Report tab.
     */
    @FXML
    private void initVehicleReport(){
        initializePrintReservationsDropDown();
        initializePrintCompletedTrips();
        handlePrintReservationsDropDownClick();
        handlePrintCompletedTripsDropDownBtnClick();
    }

    /**
     * Method that checks if a text field is empty.
     *
     * @param input the text field
     * @return true if empty; otherwise false
     */
    @FXML
    private boolean checkIfEmptyTextField(TextField input){
        return input.getText() == null || input.getText().trim().isEmpty();
    }

    /**
     * Method that checks if a date picker is empty.
     *
     * @param date user input date
     * @return true if empty; otherwise false
     */
    @FXML
    private boolean checkIfEmptyDatePicker(DatePicker date){
        return date.getEditor().getText().isBlank();
    }

    /**
     * Method that checks if a radio button is selected in a toggle group.
     *
     * @return true if a button was selected; otherwise false
     */
    @FXML
    private boolean checkIfRadioBtnSelected(){
        return !fordButton.isSelected() && !toyotaButton.isSelected() && !hondaButton.isSelected()
                && !chevyButton.isSelected();
    }

    /**
     * Method that checks if anything in the dropdown was selected.
     *
     * @param choice the dropdown box
     * @return true if nothing; otherwise false
     * @param <E> combobox input type
     */
    @FXML
    private <E> boolean checkIfDropDownSelected(ComboBox<E> choice){
        return choice.getValue() == null;
    }

    /**
     * Method that clears all inputs on a tab.
     */
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
        campusDropDown2.setItems(campusDropDown.getItems());
        returnCampus.setItems(campusDropDown.getItems());
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
            return;
        }

        Date isValidDate = new Date(dateInput.getEditor().getText());
        int odo = Integer.parseInt(mileageTextField.getText());
        Vehicle vehicle = createVehicle(licensePlateInput.getText(), isValidDate, make, odo, campus);
        if (fleet.contains(vehicle)) {
            outputTextAreaManagement.setText("Vehicle already in fleet.");
            return;
        }
        fleet.add(vehicle);
        vehicleDropDownFromFleet.getItems().add(vehicle);
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
        vehicleDropDownFromFleet.getItems().remove(vehicle);
    }


    // VEHICLE RESERVATION -----------------------------------------------------------------------------

    //add the importFileChooser method she has in modules to load in vehicles.txt
    @FXML
    private void initVehicleReservation(){
        initializeEmployeeDropDown();
        initializeVehicleDropDownFromFleet();
        initializeBookButton();
        initializeCancelButton();
        initializeFileLoadButton();
        startDateInput.setEditable(false);
        endDateInput.setEditable(false);

    }

    @FXML
    private void initializeEmployeeDropDown(){
        employeeDropDown.getItems().addAll(Employee.values());
        employeeDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected: " + newVal);
            }
        });
    }
    @FXML
    private void initializeVehicleDropDownFromFleet(){
        vehicleDropDownFromFleet.getItems().setAll(fleet.getFleet());
        vehicleDropDownFromFleet.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected vehicle: " + newVal);
            }
        });
    }
    @FXML
    private void initializeBookButton() {
        bookButton.setOnAction(e -> {
            Vehicle selectedVehicle = vehicleDropDownFromFleet.getValue();
            Employee selectedEmployee = employeeDropDown.getValue();
            Campus selectedCampus = campusDropDown2.getValue();
            if (selectedVehicle == null || selectedEmployee == null || selectedCampus == null ||
                    startDateInput.getValue() == null || endDateInput.getValue() == null) {
                outputTextAreaManagement2.setText("Please fill out all fields before booking.\n");
                return;
            }
            String startingDateStr = (startDateInput.getValue().getMonthValue()) + "/" + startDateInput.getValue().getDayOfMonth()
                    + "/" + startDateInput.getValue().getYear();
            String endingDateStr = (endDateInput.getValue().getMonthValue()) + "/" + endDateInput.getValue().getDayOfMonth()
                    + "/" + endDateInput.getValue().getYear();
            Date startingDate = new Date(startingDateStr);
            Date endingDate = new Date(endingDateStr);
            Date today = Date.getToday();
            Date threeMonthsFromToday = Date.getThreeMonthsFromToday();
            if (startingDate.compareTo(today) < 0) {
                outputTextAreaManagement2.setText("Cannot book a vehicle in the past.\n");
                return;
            }
            if (endingDate.compareTo(startingDate) < 0) {
                outputTextAreaManagement2.setText("Ending date cannot be before starting date.\n");
                return;
            }
            if (startingDate.compareTo(threeMonthsFromToday) > 0) {
                outputTextAreaManagement2.setText("Cannot book more than three months in advance.\n");
                return;
            }
            Vehicle vehicleFromFleet = fleet.getVehicle(selectedVehicle);
            if (vehicleFromFleet == null) {
                outputTextAreaManagement2.setText("Selected vehicle does not exist in the fleet.\n");
                return;
            }
            Booking booking = new Booking(startingDate, endingDate, selectedEmployee, vehicleFromFleet, selectedCampus);
            reservation.add(booking);
            if (!vehicleDropDownFromReservation.getItems().contains(booking)) {
                vehicleDropDownFromReservation.getItems().add(booking);
            }
            outputTextAreaManagement2.setText(booking.toString() + " has been booked.\n");
        });
    }
    @FXML
    private void initializeCancelButton(){
        cancelButton.setOnAction(e -> {
            Vehicle selectedVehicle = vehicleDropDownFromFleet.getValue();
            Employee selectedEmployee = employeeDropDown.getValue();
            Campus selectedCampus = campusDropDown2.getValue();
            if (selectedVehicle == null || selectedEmployee == null || selectedCampus == null) {
                outputTextAreaManagement2.appendText("Please fill out all fields before removing.\n");
                return;
            }
            String startingDateStr = (startDateInput.getValue().getMonthValue()) + "/" + startDateInput.getValue().getDayOfMonth()
                    + "/" + startDateInput.getValue().getYear();
            String endingDateStr = (endDateInput.getValue().getMonthValue()) + "/" + endDateInput.getValue().getDayOfMonth() + "/"
                    + endDateInput.getValue().getYear();
            Date startingDate = new Date(startingDateStr);
            Date endingDate = new Date(endingDateStr);
            Booking temp = new Booking(startingDate, endingDate, selectedEmployee, selectedVehicle, selectedCampus);
            Booking found = reservation.bookingLookup(temp);
            if (found == null) {
                outputTextAreaManagement2.setText("No matching booking found.\n");
                return;
            }
            reservation.remove(found);
            vehicleDropDownFromReservation.getItems().remove(found);
            outputTextAreaManagement2.setText("Booking removed: " + found.toString() + "\n");
        });
    }
    @FXML
    private void initializeFileLoadButton() {
        fileLoadButton.setOnAction(e ->{
                FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for Import");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);
        if (sourceFile == null) {
            outputTextAreaManagement2.setText("No file selected.\n");
            return;
        }

        try (Scanner scanner = new Scanner(sourceFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 5) {
                    outputTextAreaManagement2.setText("Invalid line format: " + line + "\n");
                    continue;
                }

                String plate = parts[0];
                Date date = new Date(parts[1]);
                Make make = Make.valueOf(parts[2].toUpperCase());
                int mileage = Integer.parseInt(parts[3]);
                Campus campus = Campus.valueOf(parts[4]);

                Vehicle vehicle = createVehicle(plate, date, make, mileage, campus);
                if (vehicle != null) {
                    fleet.add(vehicle);
                }
            }
            vehicleDropDownFromFleet.getItems().setAll(fleet.getFleet());
            outputTextAreaManagement2.setText("Vehicles successfully loaded into the fleet.\n");
        } catch (Exception T) {
            outputTextAreaManagement2.appendText("Error loading file: " + T.getMessage() + "\n");
        }
        });
    }
    // VEHICLE RETURN -----------------------------------------------------------------------------
    @FXML
    private void initVehicleReturn(){
        initializeVehicleDropDownFromReservation();
        initializeCompleteTripButton();
    }

    private void initializeVehicleDropDownFromReservation(){
        vehicleDropDownFromReservation.getItems().clear();

        // Add each booking from the reservation list
        for (int i = 0; i < reservation.Size(); i++) {
            Booking b = reservation.get(i);
            if (!vehicleDropDownFromReservation.getItems().contains(b)) {
                vehicleDropDownFromReservation.getItems().add(b);
            }
        }

        // Listener for when a booking is selected
        vehicleDropDownFromReservation.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected booking: " + newVal);
            }
        });
    }

    @FXML
    private void initializeCompleteTripButton() {
        completeTripButton.setOnAction(e -> {
            Booking selectedBooking = vehicleDropDownFromReservation.getValue();
            Campus selectedDropoff = returnCampus.getValue();
            String mileageText = mileageTextFieldReservation.getText();

            if (selectedBooking == null) {
                outputTextAreaManagement3.appendText("Please select a booking to complete.\n");
                return;
            }
            if (selectedDropoff == null) {
                outputTextAreaManagement3.appendText("Please select a drop-off campus.\n");
                return;
            }
            if (mileageText == null || mileageText.isBlank()) {
                outputTextAreaManagement3.appendText("Please enter the new mileage.\n");
                return;
            }

            int newMileage;
            try {
                newMileage = Integer.parseInt(mileageText.trim());
            } catch (NumberFormatException ex) {
                outputTextAreaManagement3.appendText("Mileage must be a numeric value.\n");
                return;
            }

            Vehicle vehicle = selectedBooking.getVehicle();
            if (vehicle == null) {
                outputTextAreaManagement3.appendText("Booking does not have a vehicle.\n");
                return;
            }

            int oldMileage = vehicle.getMileage(); // or getMileage(), depending on your Vehicle class
            if (newMileage < oldMileage) {
                outputTextAreaManagement3.appendText("New mileage cannot be less than old mileage.\n");
                return;
            }

            // --- Determine surcharge and update campus if needed ---
            boolean surcharge = false;
            if (!vehicle.getCampus().equals(selectedDropoff)) {
                surcharge = true;
                vehicle.setCampus(selectedDropoff);
            }

            // --- Update vehicle mileage ---
            vehicle.setMileage(newMileage);

            // --- Create and add trip ---
            Trip newTrip = new Trip(selectedBooking, oldMileage, newMileage, surcharge);
            tripList.add(newTrip);

            // --- Remove the completed booking ---
            reservation.remove(selectedBooking);
            vehicleDropDownFromReservation.getItems().remove(selectedBooking);

            // --- Output success ---
            String msg = "Trip completed for vehicle " + vehicle.getPlate() +
                    (surcharge ? " (surcharge applied)" : "") +
                    ". New mileage: " + newMileage + "\n";
            outputTextAreaManagement3.appendText(msg);

            // --- Clear fields ---
            vehicleDropDownFromReservation.getSelectionModel().clearSelection();
            returnCampus.getSelectionModel().clearSelection();
            mileageTextFieldReservation.clear();
        });
    }
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
                printCompletedTripsDropDown.getSelectionModel().clearSelection();
            }
            else if(choice.equals("by location/plate/beginning date (PR)")){
                displayBookings1();
                printCompletedTripsDropDown.getSelectionModel().clearSelection();
            }
        });
//        printReservationsDropDown.setValue(null);
//        printCompletedTripsDropDown.getSelectionModel().clearSelection();
    }

    @FXML
    private void handlePrintFleetBtnClick(){
        displayVehicles();
        printReservationsDropDown.getSelectionModel().clearSelection();
        printCompletedTripsDropDown.getSelectionModel().clearSelection();
    }

    @FXML
    private void handlePrintCompletedTripsDropDownBtnClick(){
        printCompletedTripsDropDown.setOnAction(e -> {
            String choice = printCompletedTripsDropDown.getValue();
            if (choice.equals("by plate/ending date (PT)")){
                displayCompletedTrips();
                printReservationsDropDown.getSelectionModel().clearSelection();

            }
            else if(choice.equals("cost report by department (PC)")){
                displayCostReport();
                printReservationsDropDown.getSelectionModel().clearSelection();

            }

        });
//        printCompletedTripsDropDown.setValue(null);
//        printReservationsDropDown.getSelectionModel().clearSelection();
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
