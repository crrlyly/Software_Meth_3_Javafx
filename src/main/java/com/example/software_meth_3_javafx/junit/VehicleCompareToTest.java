package junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import util.Date;
import fleet.*;


/**
 * This class tests the compareTo method of the vehicle super class with all of the vehicle subclasses.
 *
 * @author Alec Cotler
 */

public class VehicleCompareToTest {


    /**
     * Just empty constructor.
     */
    public VehicleCompareToTest() {
    }

    /**
     * Tests when the campus of vehicle 1 comes before the campus of vehicle 2.
     */
    @Test
    void testCompareToNegativeByCampus() {
        Vehicle v1 = new Sedan("12345S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Camden);
        Vehicle v2 = new Sedan("67890S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Newark);
        assertTrue(v1.compareTo(v2) < 0, "Expected Camden < Newark by city name");
    }

    /**
     * Tests when the make of vehicle 1 comes before the campus of vehicle 2.
     */
    @Test
    void testCompareToNegativeByMake() {
        Vehicle v1 = new Utility("11111D", new Date("01/01/2024"), Make.CHEVY, 2000, Campus.Busch);
        Vehicle v2 = new Utility("22222D", new Date("01/01/2024"), Make.TOYOTA, 2000, Campus.Cook);
        assertTrue(v1.compareTo(v2) < 0, "Expected CHEVY < TOYOTA by make name");
    }

    /**
     * Tests when the date of vehicle 1 comes before the campus of vehicle 2.
     */
    @Test
    void testCompareToNegativeByDate() {
        Vehicle v1 = new Truck("33333X", new Date("01/01/2020"), Make.HONDA, 3000, Campus.Newark);
        Vehicle v2 = new Truck("44444X", new Date("01/01/2024"), Make.HONDA, 3000, Campus.Newark);
        assertTrue(v1.compareTo(v2) < 0, "Expected earlier date < later date");
    }

    /**
     * Tests when there are two different sedans with the same date, make, and campus.
     */
    @Test
    void testCompareToEqualSedan() {
        Vehicle v1 = new Sedan("55445S", new Date("06/01/2024"), Make.HONDA, 4000, Campus.Camden);
        Vehicle v2 = new Sedan("55555S", new Date("06/01/2024"), Make.HONDA, 4000, Campus.Camden);
        assertEquals(0, v1.compareTo(v2), "Expected identical Sedans to compare equal");
    }

    /**
     * Tests when there are two different utilitys with the same date, make, and campus.
     */
    @Test
    void testCompareToEqualUtility() {
        Vehicle v1 = new Utility("66644D", new Date("07/01/2024"), Make.FORD, 4000, Campus.Newark);
        Vehicle v2 = new Utility("66666D", new Date("07/01/2024"), Make.FORD, 4000, Campus.Newark);
        assertEquals(0, v1.compareTo(v2), "Expected identical Utilities to compare equal");
    }

    /**
     * Tests when there are two different trucks with the same date, make, and campus.
     */
    @Test
    void testCompareToEqualTruck() {
        Vehicle v1 = new Truck("77744X", new Date("08/01/2024"), Make.CHEVY, 4000, Campus.Livingston);
        Vehicle v2 = new Truck("77777X", new Date("08/01/2024"), Make.CHEVY, 4000, Campus.Cook);
        assertEquals(0, v1.compareTo(v2), "Expected identical Trucks to compare equal");
    }



    /**
     * Tests when the campus of vehicle 1 comes after the campus of vehicle 2.
     */
    @Test
    void testCompareToPositiveByCampus() {
        Vehicle v1 = new Sedan("88888S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Newark);
        Vehicle v2 = new Sedan("99999S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Camden);
        assertTrue(v1.compareTo(v2) > 0, "Expected Newark > Camden by city name");
    }

    /**
     * Tests when the make of vehicle 1 comes after the campus of vehicle 2.
     */
    @Test
    void testCompareToPositiveByMake() {
        Vehicle v1 = new Utility("11112D", new Date("01/01/2024"), Make.TOYOTA, 2000, Campus.Cook);
        Vehicle v2 = new Utility("22223D", new Date("01/01/2024"), Make.CHEVY, 2000, Campus.Livingston);
        assertTrue(v1.compareTo(v2) > 0, "Expected TOYOTA > CHEVY by make name");
    }

    /**
     * Tests when the date of vehicle 1 comes after the campus of vehicle 2.
     */
    @Test
    void testCompareToPositiveByDate() {
        Vehicle v1 = new Truck("33334X", new Date("01/01/2025"), Make.HONDA, 3000, Campus.Newark);
        Vehicle v2 = new Truck("44445X", new Date("01/01/2020"), Make.HONDA, 3000, Campus.Newark);
        assertTrue(v1.compareTo(v2) > 0, "Expected later date > earlier date");
    }
}
