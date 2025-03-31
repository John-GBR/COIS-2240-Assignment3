import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedWriter;
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
    		instance = new RentalSystem();
    		
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
    
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
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