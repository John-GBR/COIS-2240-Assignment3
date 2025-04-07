import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
	
	
}

