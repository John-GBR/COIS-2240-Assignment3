
package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

//Name: John Cooney
//Student ID: 0860205
//Date of submission: April 11, 2025


public class RentalSystemGUI extends Application {
	@Override
    public void start(Stage primaryStage) {
		// Creates a variable that references the RentalSystem instance
        RentalSystem rentalSystem = RentalSystem.getInstance();
        
        // The main pane that holds all other UI elements
        GridPane mainPane = new GridPane();
        // Panes the hold collections of UI elements pertaining to a specific task
        //		(example: addVehiclePane holds all the UI elements for adding a vehicle)
		VBox addvehiclePane = new VBox(), addCustomerPane = new VBox(), rentPane = new VBox(), returnPane = new VBox();
        // StackPane that holds the UI elements for vehicle specific fields when adding a vehicle
		//		(seatsField, hasSideCarHBox, cargoCapacityField)
		StackPane stackPane = new StackPane();
		
        // Button that attempts to add a vehicle when pressed
        Button addVehicleButton = new Button("Add Vehicle");
        // Fields for getting info from the user pertaining to adding a vehicle
        TextField licensePlateField = new TextField();
        TextField makeField = new TextField();
        TextField modelField = new TextField();
        TextField yearField = new TextField();
        TextField seatsField = new TextField();
        TextField cargoCapacityField = new TextField();
        // Sets the prompt text for the adding a vehicle fields
        licensePlateField.setPromptText("License Plate (Must be 3 letters followed by 3 numbers)");
        makeField.setPromptText("Vehicle Make");
        modelField.setPromptText("Vehicle Model");
        yearField.setPromptText("Vehicle Year (Must be a whole number)");
        seatsField.setPromptText("Number Of Seats (Must be a whole number)");
        cargoCapacityField.setPromptText("Cargo Capacity (Must be a number)");
        // Label to indicate the purpose of the vehicleTypeCBox to the user
        Label vehicleTypeLabel = new Label("Vehicle Type:");
        // ComboBox that allows the user to select the type of vehicle
        ComboBox<String> vehicleTypeCBox = new ComboBox<>();
        // Adds the values for vehicleTypeCBox & sets its default value
        vehicleTypeCBox.getItems().addAll("Car", "Motorcycle", "Truck");
        vehicleTypeCBox.setValue("Car");
        // ComboBox that allows the user to select the type of vehicle
        ComboBox<String> hasSideCarCBox = new ComboBox<>();
        // Adds the values for vehicleTypeCBox & sets its default value
        hasSideCarCBox.getItems().addAll("Yes", "No");
        hasSideCarCBox.setValue("Yes");
        // Creates & sets up an HBox to hold hasSideCarLabel & hasSideCarCBox
        HBox hasSideCarHBox = new HBox();
        Label hasSideCarLabel = new Label("Has A Side Car: ");
        hasSideCarHBox.getChildren().addAll(hasSideCarLabel, hasSideCarCBox);
        
        // Button that attempts to add a customer when pressed
        Button addCustomerButton = new Button("Add Customer");
        // Fields for getting customer's name & ID from the user
        TextField custIdField = new TextField();
        TextField custNameField = new TextField();
        // Sets the prompt text for the adding a customer fields
        custIdField.setPromptText("Customer ID (Must be a whole number)");
        custNameField.setPromptText("Customer Name");
        
        // Button that attempts to rent a vehicle when pressed
        Button rentVehicleButton = new Button("Rent Vehicle");
        // Fields for getting info from the user pertaining to renting a vehicle
        TextField rentLicensePlateField = new TextField();
        TextField rentCustNameField = new TextField();
        TextField rentAmountField = new TextField();
        // Sets the prompt text for the renting fields
        rentLicensePlateField.setPromptText("License Plate");
        rentCustNameField.setPromptText("Customer Name");
        rentAmountField.setPromptText("Remtal Amount (Must be a number)");
        
        // Button that attempts to return a vehicle when pressed
        Button returnVehicleButton = new Button("Return Vehicle");
        // Fields for getting info from the user pertaining to returning a vehicle
        TextField returnLicensePlateField = new TextField();
        TextField returnCustNameField = new TextField();
        TextField returnFeeField = new TextField();
        // Sets the prompt text for the returning fields
        returnLicensePlateField.setPromptText("License Plate");
        returnCustNameField.setPromptText("Customer Name");
        returnFeeField.setPromptText("Return Fee (Must be a number)");
        
        // Labels that display: all available vehicles, all customers, & rental history
        Label availibleVehiclesLabel = new Label("");
        Label customersLabel = new Label("");
        Label rentHistoryLabel = new Label("");
        // Sets the labels' text with info from the RentalSystem
        availibleVehiclesLabel.setText(RentalSystem.getInstance().getAvailableVehicles());
        customersLabel.setText(rentalSystem.getAllCustomers());
        rentHistoryLabel.setText(rentalSystem.getRentalHistory());
        // Lists to hold the corresponding Labels, allowing for scrolling when needed
     	ListView<Label> availibleVehiclesListView = new ListView<>();
     	ListView<Label> customersListView = new ListView<>();
     	ListView<Label> rentHistoryListView = new ListView<>();
        
        // The labels held in the 1st column
        Label addVehicleLabel = new Label("Add Vehicle");
        Label addCustomerLabel = new Label("Add Customer");
        Label rentVehicleLabel = new Label("Rent Vehicle");
        Label returnVehicleLabel = new Label("Return Vehicle");
        
        // Label to display any relevant error messages to the user
        Label errorLabel = new Label("");
        
     	// Button that exits the program when pressed
        Button exitButton = new Button("Exit");
        
        // Sets the matching field to visible when an option is picked from the vehicleTypeCBox
        vehicleTypeCBox.setOnAction(e -> {
        	// Sets all the UI elements for vehicle specific parameters to invisible
        	seatsField.setVisible(false);
        	hasSideCarHBox.setVisible(false);
        	cargoCapacityField.setVisible(false);
        	
        	// Sets the UI element for vehicle specific parameters for the selected vehicle type to visible
        	switch (vehicleTypeCBox.getValue()) {
        		case "Car":
        			seatsField.setVisible(true);
        			break;
        		case "Motorcycle":
        			hasSideCarHBox.setVisible(true);
        			break;
        		case "Truck":
                	cargoCapacityField.setVisible(true);
        			break;
        	}
        });
        
        // Attempts to add a vehicle to the rentalSystem when pressed
        addVehicleButton.setOnAction(e -> {
        	try {
        		// The vehicle to be added
        		Vehicle vehicle;
        		// Variables for the non-vehicle specific parameters for creating a vehicle
	        	String make, model, plate;
	        	int year, seats;
	        	boolean sidecar;
	        	double cargoCapacity;
	        	
	        	// Sets all of variables for the non-vehicle specific parameters for creating a vehicle from their respective fields
	        	make = makeField.getText();
	        	model = modelField.getText();
	        	plate = licensePlateField.getText();
	        	year = Integer.parseInt(yearField.getText().replaceAll("[^\\d]", ""));
	        	
	        	// Creates the vehicle with vehicle type based on the value of vehicleTypeCBox
	        	switch (vehicleTypeCBox.getValue()) {
	    		case "Car":
	    			// Sets the seats value using seatsField's value & creates the car
	    			seats = Integer.parseInt(seatsField.getText().replaceAll("[^\\d]", ""));
	    			vehicle = new Car(make, model, year, seats);
	    			break;
	    			
	    		case "Motorcycle":
	    			// Sets the sidecar value using hasSideCarCBox's value
	    			if (hasSideCarCBox.getValue() == "Yes")
	    				sidecar = true;
	    			else
	    				sidecar = false;

	    			// Creates the motorcycle
	    			vehicle = new Motorcycle(make, model, year, sidecar);
	    			break;
	    			
	    		case "Truck":
	    			// Sets the cargoCapacity value using cargoCapacityField's value & creates the truck
	    			cargoCapacity = Double.parseDouble(cargoCapacityField.getText().replaceAll("[^\\d\\.]", ""));
	    			vehicle = new Truck(make, model, year, cargoCapacity);
	    			break;
	    			
	    		default:
	    			vehicle = null;
	    			break;
	        	}
	            
	        	// Boolean indicating if the vehicle's license plate is properly set
	        	boolean lpSet = true;
	        	// Boolean indicating if the vehicle was successfully added
	        	boolean vehicleAdded;
	            if (vehicle != null){
	            	// Attempts to set the vehicle's license plate, else display an error message to the user
	            	try {
	            		vehicle.setLicensePlate(plate);
	            	}
	                catch (IllegalArgumentException ex) {
	                	lpSet = false;
	                	errorLabel.setText("*ERROR* Invalid license plate format");
	            	}
	            	
	            	// Attempts to add the vehicle to the RentalSystem, else display an error message to the user 
	            	if (lpSet)
	            	{
	            		vehicleAdded = rentalSystem.addVehicle(vehicle);
	            		if (vehicleAdded)
	            		{
	            			// Resets all fields pertaining to adding a vehicle
	            			makeField.setText("");
	            			modelField.setText("");
	            			yearField.setText("");
	            			licensePlateField.setText("");
	            			seatsField.setText("");
	            			cargoCapacityField.setText("");
	            			// Sets the error label to empty
	            			errorLabel.setText("");
	            		}
	            		else
	            			errorLabel.setText("*ERROR* Vehicle with matching license plate already exists in the system");
	            	}
	            }
	        	
	            // Updates the availibleVehiclesLabel
	            availibleVehiclesLabel.setText(RentalSystem.getInstance().getAvailableVehicles());
        	} catch(Exception ex) {
        		errorLabel.setText("*ERROR* One or more of the parameters for adding a vehicle are invalid");
        	}
        });
        

        // Attempts to add a customer to the rentalSystem when pressed
        addCustomerButton.setOnAction(e -> {
        	try {
        		// Sets all of variables for the parameters for creating a customer from their respective fields
	        	int cid = Integer.parseInt(custIdField.getText().replaceAll("[^\\d]", ""));
	        	String cname = custNameField.getText();
	        	// Boolean indicating if the customer was successfully added
	        	boolean custAdded;
	        	
	        	// Attempts to add the vehicle to the RentalSystem, if unsuccessful display an error message to the user 
            	custAdded = rentalSystem.addCustomer(new Customer(cid, cname));
	        	if (custAdded)
	        	{
        			// Resets all fields pertaining to adding a customer
	        		custIdField.setText("");
	        		custNameField.setText("");
        			// Sets the error label to empty
	        		errorLabel.setText("");
	        	}
	        	else
	        		errorLabel.setText("*ERROR* Customer with matching ID already exists in the system");

	            // Updates the customersLabel
	            customersLabel.setText(rentalSystem.getAllCustomers());
        	} catch(Exception ex) {
        		errorLabel.setText("*ERROR* One or more of the parameters for adding a customer are invalid");
        	}
        });
        
        // Attempts to rent a vehicle to the rentalSystem when pressed
        rentVehicleButton.setOnAction(e -> {
        	try {
        		// Sets all of variables for the parameters for renting a vehicle from their respective fields
	        	String rentPlate = rentLicensePlateField.getText(), cnameRent = rentCustNameField.getText();
	            double rentAmount = Double.parseDouble(rentAmountField.getText().replaceAll("[^\\d\\.]", ""));
	
	            // Gets the vehicle & customer from the RentalSystem using licensePlate & ID
	            Vehicle vehicleToRent = rentalSystem.findVehicleByPlate(rentPlate);
	            Customer customerToRent = rentalSystem.findCustomerByName(cnameRent);
	            
	            // Boolean indicating if the vehicle was successfully rented
	        	boolean vehicleRented;
	            
	            // Displays an error message to the user when the vehicle and/or customer aren't found in the RentalSystem
            	if (vehicleToRent == null || customerToRent == null) {
	            	errorLabel.setText("*ERROR* Vehicle or customer not found");
	            }
	            else {
	            	// Rents the vehicle using the rentalSystem
	            	vehicleRented = rentalSystem.rentVehicle(vehicleToRent, customerToRent, LocalDate.now(), rentAmount);
		            // Updates the availibleVehiclesLabel & rentHistoryLabel
	            	rentHistoryLabel.setText(rentalSystem.getRentalHistory());
	            	availibleVehiclesLabel.setText(RentalSystem.getInstance().getAvailableVehicles());

	            	if (vehicleRented) {
		            	// Resets all fields pertaining to renting a vehicle
		            	rentLicensePlateField.setText("");
		            	rentCustNameField.setText("");
		            	rentAmountField.setText("");
	        			// Sets the error label to empty
		        		errorLabel.setText("");
	            	}
	            	else {
	            		// Displays an error message to the user if the vehicle isn't available
	            		errorLabel.setText("*ERROR* Vehicle is not available for renting");
	            	}
	            }
        	} catch(Exception ex) {
        		errorLabel.setText("*ERROR* One or more of the parameters for renting a vehicle are invalid");
        	}
        });

        // Attempts to return a vehicle to the rentalSystem when pressed
        returnVehicleButton.setOnAction(e -> {
        	try {
        		// Sets all of variables for the parameters for returning a vehicle from their respective fields
	        	String returnPlate = returnLicensePlateField.getText(), cnameReturn = returnCustNameField.getText();
	            double returnFees = Double.parseDouble(returnFeeField.getText().replaceAll("[^\\d\\.]", ""));
	
	            // Boolean indicating if the vehicle was successfully rented
	        	boolean vehicleReturned;
	            
	            // Gets the vehicle & customer from the RentalSystem using licensePlate & ID
	            Vehicle vehicleToReturn = rentalSystem.findVehicleByPlate(returnPlate);
	            Customer customerToReturn = rentalSystem.findCustomerByName(cnameReturn);
	
	            // Displays an error message to the user when the vehicle and/or customer aren't found in the RentalSystem
            	if (vehicleToReturn == null || customerToReturn == null) {
	            	errorLabel.setText("*ERROR* Vehicle or customer not found");
	            }
	            else {
	            	// Returns the vehicle using the rentalSystem
	            	vehicleReturned = rentalSystem.returnVehicle(vehicleToReturn, customerToReturn, LocalDate.now(), returnFees);
	            	// Updates the availibleVehiclesLabel & rentHistoryLabel
	            	rentHistoryLabel.setText(rentalSystem.getRentalHistory());
	                availibleVehiclesLabel.setText(RentalSystem.getInstance().getAvailableVehicles());
	                
	                if (vehicleReturned) {
	                	// Resets all fields pertaining to returning a vehicle
		                returnLicensePlateField.setText("");
		                returnCustNameField.setText("");
		                returnFeeField.setText("");
	        			// Sets the error label to empty
		        		errorLabel.setText("");
	                }
	                else {
	            		// Displays an error message to the user if the vehicle isn't rented
	            		errorLabel.setText("*ERROR* Vehicle is not rented");
	            	}
	            }
        	} catch(Exception ex) {
        		errorLabel.setText("*ERROR* One or more of the parameters for returning a vehicle are invalid");
        	}
        });
        
        // Exits the program when the exit button is pressed
        exitButton.setOnAction(e -> System.exit(0));
        
        
        // Sets the horizontal & vertical gap for the mainPane
        mainPane.setHgap(30);
        mainPane.setVgap(30);
        
        // Sets the widths of certain UI elements to prevent the UI as a whole from being too squished & cutting off some info
        availibleVehiclesListView.setPrefWidth(600);
        exitButton.setPrefWidth(400);
        
        // Adds all of the vehicle-specific UI elements for adding a vehicle to the stack pane & sets all but the car's field to invisible
        stackPane.getChildren().addAll(seatsField, hasSideCarHBox, cargoCapacityField);
        hasSideCarHBox.setVisible(false);
    	cargoCapacityField.setVisible(false);
        
    	// Populates the panes for adding vehicles, adding customers, and renting & returning vehicles
        addvehiclePane.getChildren().addAll(licensePlateField, makeField, modelField, yearField, vehicleTypeLabel, vehicleTypeCBox, stackPane, addVehicleButton);
        addCustomerPane.getChildren().addAll(custIdField, custNameField, addCustomerButton);
        rentPane.getChildren().addAll(rentLicensePlateField, rentCustNameField, rentAmountField, rentVehicleButton);
        returnPane.getChildren().addAll(returnLicensePlateField, returnCustNameField, returnFeeField, returnVehicleButton);
        
        // Adds the availibleVehiclesLabel, customersLabel & rentHistoryLabel to their respective ListViews
        availibleVehiclesListView.getItems().addAll(availibleVehiclesLabel);
        customersListView.getItems().addAll(customersLabel);
        rentHistoryListView.getItems().addAll(rentHistoryLabel);

        // Populates the mainPane with UI elements
        mainPane.addColumn(0, addVehicleLabel, addCustomerLabel, rentVehicleLabel, returnVehicleLabel);
        mainPane.addColumn(1, addvehiclePane, addCustomerPane, rentPane, returnPane, exitButton);
        mainPane.addColumn(2, availibleVehiclesListView, customersListView, rentHistoryListView, errorLabel);        

        // Creates a new scene & shows it with the root bing the mainPane
        Scene scene = new Scene(mainPane, 1200, 800);
        primaryStage.setTitle("RentalSystemGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
        launch(args);
    }
}
