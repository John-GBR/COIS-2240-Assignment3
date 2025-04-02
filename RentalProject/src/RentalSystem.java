import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    // Singleton instance of this class
    private static RentalSystem instance;

    // Returns the singleton instance of this class, if instance hasn't been set yet, do so
    public static RentalSystem getInstance()
    {
    	if (instance == null)
    	{
    		instance = new RentalSystem();
    		instance.loadData();
    	}
    		
    	return instance;
    }
    
    // Adds vehicle details to vehicles.txt. Called inside addVehicle()
    private void saveVehicle(Vehicle vehicle)
    {
    	try {
    		// Creates a BufferedWriter object
			BufferedWriter writer = new BufferedWriter(new FileWriter("vehicles.txt", true));
			
			// String that holds the output of the vehicle's getInfo() method, plus the vehicle's type
			String vehicleInfo = vehicle.getInfo();
			
			// Adds to the beginning of the vehicleInfo string the type of vehicle
			if (vehicle instanceof SportCar)
				vehicleInfo = "SportCar" + vehicleInfo;
			else if (vehicle instanceof Car)
				vehicleInfo = "Car" + vehicleInfo;
			else if (vehicle instanceof Motorcycle)
				vehicleInfo = "Motorcycle" + vehicleInfo;
			else if (vehicle instanceof Truck)
				vehicleInfo = "Truck" + vehicleInfo;

			// Writes the vehicleInfo to the file then closes the writer
			writer.write(vehicleInfo + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Adds customer details to customers.txt. Called inside addCustomer()
    private void saveCustomer(Customer customer)
    {
    	try {
    		// Creates a BufferedWriter object
			BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true));

			// Writes the customer's getInfo() output to the file then closes the writer
			writer.write(customer.toString() + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Adds rental record details to rental_records.txt. Called at the end of rentVehicle()
    //		and returnVehicle() after a record is added to the rental history
    private void saveRecord(RentalRecord record)
    {
    	try {
    		// Creates a BufferedWriter object
			BufferedWriter writer = new BufferedWriter(new FileWriter("rental_records.txt", true));

			// Writes the record's getInfo() output to the file then closes the writer
			writer.write(record.toString() + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Loads data from vehicles.txt, customers.txt & rental_records.txt
    public void loadData()
    {
    	// Loads vehicles from vehicles.txt
    	try {
    		// Creates a BufferedReader object
			BufferedReader reader = new BufferedReader(new FileReader("vehicles.txt"));
			
			// Parses through each line in vehicles.txt, each line representing a different Vehicle object
			String line;
			while ((line = reader.readLine()) != null)
			{
				// Splits the line into individual words
				String[] words = line.split("[|]");
				// Variable that will hold newly created vehicle objects from vehicles.txt
				Vehicle newVehicle;
				
				// Removes all spaces from all words
				for (int i = 0; i < words.length; i++)
					words[i] = words[i].replaceAll("\\s", "");
				
				// Using the 1st word in words, representing the vehicle type, creates a new vehicle of that type & then adds it to vehicles
				switch (words[0]) {
					case "Car":
						// Properly formats the number of seats to an integer
						int newNumSeats = Integer.parseInt(words[6].replaceAll("[^\\d]", ""));
						// Creates the car
						newVehicle = new Car(words[2], words[3], Integer.parseInt(words[4].replaceAll("[^\\d]", "")), newNumSeats);
						
						//Sets license plate & status, properly formatting status, then adds it to vehicles
						newVehicle.setLicensePlate(words[1]);
						newVehicle.setStatus(Vehicle.VehicleStatus.valueOf(words[5]));
						vehicles.add(newVehicle);
						break;
						
					case "Motorcycle":
						// Properly formats if the motorcycle has a side car to a boolean
						boolean newHasSideCar;
						if (words[6].equals("Sidecar:Yes"))
							newHasSideCar = true;
						else
							newHasSideCar = false;
						// Creates the motorcycle
						newVehicle = new Motorcycle(words[2], words[3], Integer.parseInt(words[4].replaceAll("[^\\d]", "")), newHasSideCar);

						//Sets license plate & status, properly formatting status, then adds it to vehicles
						newVehicle.setLicensePlate(words[1]);
						newVehicle.setStatus(Vehicle.VehicleStatus.valueOf(words[5]));
						vehicles.add(newVehicle);
						break;
						
					case "Truck":
						// Properly formats the cargoCapacity of seats to a double
						double newCargoCapacity = Double.parseDouble(words[6].replaceAll("[^\\d\\.]", ""));
						// Creates the truck
						newVehicle = new Truck(words[2], words[3], Integer.parseInt(words[4].replaceAll("[^\\d]", "")), newCargoCapacity);

						//Sets license plate & status, properly formatting status, then adds it to vehicles
						newVehicle.setLicensePlate(words[1]);
						newVehicle.setStatus(Vehicle.VehicleStatus.valueOf(words[5]));
						vehicles.add(newVehicle);
						break;
						
					case "SportCar":
						// Properly formats the number of seats to an integer
						newNumSeats = Integer.parseInt(words[6].replaceAll("[^\\d]", ""));
						// Properly formats the horsepower to an integer
						int newHorsePower = Integer.parseInt(words[7].replaceAll("[^\\d]", ""));
						// Properly formats if the sport car has turbo to a boolean
						boolean newHasTurbo;
						if (words[8].equals("Turbo:Yes"))
							newHasTurbo = true;
						else
							newHasTurbo = false;
						// Creates the sport car
						newVehicle = new SportCar(words[2], words[3], Integer.parseInt(words[4].replaceAll("[^\\d]", "")), newNumSeats, newHorsePower, newHasTurbo);

						//Sets license plate & status, properly formatting status, then adds it to vehicles
						newVehicle.setLicensePlate(words[1]);
						newVehicle.setStatus(Vehicle.VehicleStatus.valueOf(words[5]));
						vehicles.add(newVehicle);
						break;
				}
			}
				
			// Closes the reader
			reader.close();
		} catch (FileNotFoundException e) {
			// Creates the missing file
			new File("vehicles.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Loads customers from customers.txt
    	try {
    		// Creates a BufferedReader object
			BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
			
			// Parses through each line in customers.txt, each line representing a different customer object
			String line;
			while ((line = reader.readLine()) != null)
			{
				// Splits the line into individual words
				String[] words = line.split("[|]");
				
				// Removes any instances of " Name: " from all words
				for (int i = 0; i < words.length; i++)
					words[i] = words[i].replaceAll(" Name: ", "");
				
				// Creates a new customer object, properly formatting all arguments & adds it to customers
				Customer newCustomer = new Customer(Integer.parseInt(words[0].replaceAll("[^\\d]", "")), words[1]);
				customers.add(newCustomer);
			}

			// Closes the reader
			reader.close();
		} catch (FileNotFoundException e) {
			// Creates the missing file
			new File("customers.txt");
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Loads rental records from rental_records.txt
    	try {
    		// Creates a BufferedReader object
			BufferedReader reader = new BufferedReader(new FileReader("rental_records.txt"));
			
			// Parses through each line in rental_records.txt, each line representing a different rentalRecord object
			String line;
			while ((line = reader.readLine()) != null)
			{
				// Splits the line into individual words
				String[] words = line.split("[|]");
				
				// Removes labels for some of the stored values in the line
				for (int i = 0; i < words.length; i++)
				{
					words[i] = words[i].replaceAll("\\s", "");
					words[i] = words[i].replaceAll("Plate:", "");
					words[i] = words[i].replaceAll("Customer:", "");
					words[i] = words[i].replaceAll("Date:", "");
				}
				
				// Properly formats the argument's for RentalRecord's constructor as needed
				Vehicle newVehicle = findVehicleByPlate(words[1]);
				Customer newCustomer = findCustomerByName(words[2]);
				LocalDate newDate = LocalDate.parse(words[3]);
				double newAmount = Double.parseDouble(words[4].replaceAll("[^\\d\\.]", ""));

				// Creates a new RentalRecord object & adds it to rentalHistory
				RentalRecord newRecord = new RentalRecord(newVehicle, newCustomer, newDate, newAmount, words[0]);
				rentalHistory.addRecord(newRecord);
			}

			// Closes the reader
			reader.close();
		} catch (FileNotFoundException e) {
			// Creates the missing file
			new File("rental_records.txt");
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Adds a vehicle to the rental system if no other vehicle already in the system has the same license plate
    // Returns a boolean value representing if the vehicle has been successfully added or not
    public boolean addVehicle(Vehicle vehicle) {
    	// Checks if a vehicle with a matching license plate already exists in the rental system, if so display an error message & return false
    	if (findVehicleByPlate(vehicle.getLicensePlate()) != null)
    	{
    		System.out.println("*ERROR* Vehicle with matching license plate already exists in the system.");
    		return false;
    	}
    	
    	// Adds & saves the vehicle, and returns true
        vehicles.add(vehicle);
        saveVehicle(vehicle);
        return true;
    }
	
	// Adds a customer to the rental system if no other customer already in the system has the same ID
    // Returns a boolean value representing if the customer has been successfully added or not
    public boolean addCustomer(Customer customer) {
    	// Checks if a customer with a matching ID already exists in the rental system, if so display an error message & return false
    	if (findCustomerById(customer.getCustomerId()) != null)
    	{
    		System.out.println("*ERROR* Customer with matching ID already exists in the system.\n");
    		return false;
    	}
    	
    	// Adds & saves the customer, and returns true
        customers.add(customer);
        saveCustomer(customer);
        return true;
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            
            RentalRecord rentalRecord = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(rentalRecord);
            saveRecord(rentalRecord);
            
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            
            RentalRecord rentalRecord = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(rentalRecord);
            saveRecord(rentalRecord);
            
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayAvailableVehicles() {
    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllVehicles() {
        for (Vehicle v : vehicles) {
            System.out.println("  " + v.getInfo());
        }
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }

    public Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getCustomerName().equalsIgnoreCase(name))
                return c;
        return null;
    }
}