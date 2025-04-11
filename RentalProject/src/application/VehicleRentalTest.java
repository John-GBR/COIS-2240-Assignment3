import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class VehicleRentalTest {
	
	// Tests that the vehicle class' isValidPlate method functions properly
	@Test
	void testLicensePlateValidation() {
		// Create a new vehicle object
		Vehicle v1 = new Car("Honda", "Civic", 2019, 5);

		// Tests valid plates
		v1.setLicensePlate("AAA100");
		assertTrue(v1.getLicensePlate() == "AAA100");
		v1.setLicensePlate("ABC567");
		assertTrue(v1.getLicensePlate() == "ABC567");
		v1.setLicensePlate("ZZZ999");
		assertTrue(v1.getLicensePlate() == "ZZZ999");
		
		// Tests invalid plates
		assertThrows(IllegalArgumentException.class, () -> v1.setLicensePlate(""));
		assertThrows(IllegalArgumentException.class, () -> v1.setLicensePlate(null));
		assertThrows(IllegalArgumentException.class, () -> v1.setLicensePlate("AAA1000"));
		assertThrows(IllegalArgumentException.class, () -> v1.setLicensePlate("ZZZ99"));
	}
	
	// Tests that renting & returning vehicles functions properly
	@Test
	void testRentAndReturnVehicle() {
		// Create a new vehicle & customer objects, setting the vehicle's plate
		Vehicle v1 = new Car("Honda", "Civic", 2019, 5);
		v1.setLicensePlate("MAN237");
		Customer c1 = new Customer(004, "Manny");
		
		// Tests that the vehicle is initially Available
		assertTrue(v1.getStatus() == Vehicle.VehicleStatus.AVAILABLE);
		
		// Gets the single rentalSystem instance
        RentalSystem rentalSys = RentalSystem.getInstance();
        
        // Adds the v1 & c1 to the rentalSystem
        rentalSys.addVehicle(v1);
        rentalSys.addCustomer(c1);
        
        // Tests that the vehicle can be successfully rented
        assertTrue(rentalSys.rentVehicle(v1, c1, LocalDate.now(), 0));
        
        // Tests to see that the vehicle is now Rented
        assertTrue(v1.getStatus() == Vehicle.VehicleStatus.RENTED);
        
        // Tests to see that v1 now cannot be rented since it's already rented
        assertFalse(rentalSys.rentVehicle(v1, c1, LocalDate.now(), 0));
        
        // Tests that the vehicle can be successfully returned
        assertTrue(rentalSys.returnVehicle(v1, c1, LocalDate.now(), 0));
        
        // Tests to see that the vehicle is now Available
        assertTrue(v1.getStatus() == Vehicle.VehicleStatus.AVAILABLE);

        // Tests to see that v1 now cannot be returned since it's already been returned
        assertFalse(rentalSys.returnVehicle(v1, c1, LocalDate.now(), 0));
	}
	
	
	// Tests that the RentalSystem class enforces Singleton behavior
	@Test
	void testSingletonRentalSystem() {
		try {
			// Gets the RentalSystem's constructor & tests that it's private
			Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
			assertEquals(constructor.getModifiers(), Modifier.PRIVATE);
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Verifies that getInstance doesn't return null
		assertFalse(RentalSystem.getInstance() == null);
	}
	
}

