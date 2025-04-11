public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { AVAILABLE, RESERVED, RENTED, MAINTENANCE, OUTOFSERVICE }

    // 3 parameter constructor
    public Vehicle(String make, String model, int year) {
    	// Checks if the given make & model are null / empty, if not assign them after properly capitalizing them using capitalize()
    	if (make == null || make.isEmpty())
    		this.make = null;
    	else
    		this.make = capitalize(make);
    	
    	if (model == null || model.isEmpty())
    		this.model = null;
    	else
    		this.model = capitalize(model);
    	
    	// Assigns the year, sets license plate to null & sets the vehicle to AVAILIBLE
        this.year = year;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = null;
    }

    // Helper method that returns the given string with only it's 1st letter capitalized
	private String capitalize(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

    public Vehicle() {
        this(null, null, 0);
    }

    // Setter for license plate
    public void setLicensePlate(String plate) {
    	// Sets the license plate if the given string is valid, else throw an IllegalArgumentException
    	if (isValidPlate(plate))
    		this.licensePlate = plate == null ? null : plate.toUpperCase();
    	else
    		throw new IllegalArgumentException("Invalid license plate. Must be 3 letetrs followed by 3 numbers.");
    }

    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status;
    }
    
    // Returns true if the given string has 3 letters at the beginning, immediately followed by 3 numbers, else return false
    private boolean isValidPlate(String plate)
    {
    	if (plate != null && plate != "" && plate.length() == 6 && !plate.substring(0, 2).matches(".*\\d.*")
    				&& plate.substring(3, 5).matches(".*\\d.*"))
    		return true;
    	else
    		return false;
    }
}
