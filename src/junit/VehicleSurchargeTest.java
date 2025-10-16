package junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import util.Date;
import fleet.*;

/**
 * This class tests the Surcharge methods of all the vehicle subclasses.
 *
 * @author Alec Cotler
 */

public class VehicleSurchargeTest {

    /**
     * Just an empty constructor.
     */
    public VehicleSurchargeTest() {
    }

    /**
     * Tests a sedan with no surcharge
     */
    @Test
    void testNoSurchargeSedan() {
        Sedan sedan = new Sedan("12345S", new Date("01/01/2020"), Make.TOYOTA, 1000, Campus.Busch);
        assertEquals(-1, sedan.surcharge(100, false));
    }

    /**
     * Tests a utility with no surcharge
     */
    @Test
    void testNoSurchargeUtility() {
        Utility utility = new Utility("54321D", new Date("01/01/2020"), Make.FORD, 2000, Campus.Cook);
        assertEquals(-1, utility.surcharge(100, false));
    }

    /**
     * Tests a truck with no surcharge
     */
    @Test
    void testNoSurchargeTruck() {
        Truck truck = new Truck("67890X", new Date("01/01/2020"), Make.CHEVY, 3000, Campus.Newark);
        assertEquals(-1, truck.surcharge(100, false));
    }

    /**
     * Tests a sedan with a surcharge underneath the maximum value.
     */
    @Test
    void testSurchargeUnderMaxSedan() {
        Sedan sedan = new Sedan("11111S", new Date("01/01/2020"), Make.HONDA, 500, Campus.Livingston);
        // .25 * 100 = 25 < 32.99
        assertEquals(25.0, sedan.surcharge(100, true));
    }

    /**
     * Tests a utility with a surcharge underneath the maximum value.
     */
    @Test
    void testSurchargeUnderMaxUtility() {
        Utility utility = new Utility("22222D", new Date("01/01/2020"), Make.FORD, 500, Campus.Cook);
        // .3 * 100 = 30 < 35.99
        assertEquals(30.0, utility.surcharge(100, true));
    }

    /**
     * Tests a sedan with a surcharge at the maximum value.
     */
    @Test
    void testSurchargeOverMaxSedan() {
        Sedan sedan = new Sedan("33333S", new Date("01/01/2020"), Make.TOYOTA, 1000, Campus.Busch);
        assertEquals(32.99, sedan.surcharge(200, true));
    }

    /**
     * Tests a utility with a surcharge at the maximum value.
     */
    @Test
    void testSurchargeOverMaxUtility() {
        Utility utility = new Utility("44444D", new Date("01/01/2020"), Make.FORD, 1000, Campus.Livingston);
        // .3 * 200 = 60 > 35.99, capped at 35.99
        assertEquals(35.99, utility.surcharge(200, true));
    }

    /**
     * Tests a truck with a surcharge.
     */
    @Test
    void testTruckSurcharge() {
        Truck truck = new Truck("55555X", new Date("01/01/2020"), Make.CHEVY, 1000, Campus.Camden);
        assertEquals(39.99, truck.surcharge(150, true));
    }
}
