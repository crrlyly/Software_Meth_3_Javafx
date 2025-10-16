package junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import util.Date;

/**
 * This class tests the isValid method in the date class.
 *
 * @author Alec Cotler
 */

class DateTest {

    /**
     * Just empty constructor.
     */
    public DateTest() {
    }

    /**
     * Test for when the year is too small
     */
    @Test
    void testYearSmall() {
        Date date = new Date("01/23/999");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 1: Year too small");
    }

    /**
     * test for when the month is not in 01-12
     */
    @Test
    void testMonthNotInRange() {
        Date date = new Date("13/22/2025");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 2: Month not in range");
    }

    /**
     * test for when a day is outside the maximum range for the month
     */
    @Test
    void testDayNotInRange() {
        Date date = new Date("01/34/2024");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 3: Day not in range");
    }

    /**
     * test for february 29th on a non leap year
     */
    @Test
    void testNotALeapYear() {
        Date date = new Date("02/29/2100");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 4: Not a leap year");
    }

    /**
     * test a perfectly valid date
     */
    @Test
    void testValidDate() {
        Date date = new Date("12/31/2024");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 5: Valid date");
    }

    /**
     * test february 29th on a leap year
     */
    @Test
    void testValidLeapYear() {
        Date date = new Date("02/29/2400");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 6: Valid leap year");
    }
}