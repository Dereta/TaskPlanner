package de.codersgen.task_planner.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.codersgen.task_planner.utils.Utils;

public class MyTests
{
    @Test
    public void getValidDate_True_DateIsValid()
    {
    	// Valid Tests
        // test for dots
        assertNotNull(Utils.getValidDate("10.10.2019"), "10.10.2019");
        assertNotNull(Utils.getValidDate("01.01.2019"), "01.01.2019");
        assertNotNull(Utils.getValidDate("1.01.2019"), "1.01.2019");
        assertNotNull(Utils.getValidDate("01.1.2019"), "01.1.2019");
        assertNotNull(Utils.getValidDate("1.1.2019"), "1.1.2019");
        assertNotNull(Utils.getValidDate("29.02.2020"), "29.02.2020");
        assertNotNull(Utils.getValidDate("29.2.2020"), "29.2.2020");

        // test for dash
        assertNotNull(Utils.getValidDate("10-10-2019"), "10-10-2019");
        assertNotNull(Utils.getValidDate("01-01-2019"), "01-01-2019");
        assertNotNull(Utils.getValidDate("1-01-2019"), "1-01-2019");
        assertNotNull(Utils.getValidDate("01-1-2019"), "01-1-2019");
        assertNotNull(Utils.getValidDate("1-1-2019"), "1-1-2019");
        assertNotNull(Utils.getValidDate("29-02-2020"), "29-02-2020");
        assertNotNull(Utils.getValidDate("29-2-2020"), "29-2-2020");

    }
    
    @Test
    public void getValidDate_False_DateIsValid()
    {
    	// Invalid Tests
        // test for wrong date dots
        assertNull(Utils.getValidDate("0.10.2019"), "0.10.2019");
        assertNull(Utils.getValidDate("34.01.2019"), "34.01.2019");
        assertNull(Utils.getValidDate("29.02.2019"), "29.02.2019");
        assertNull(Utils.getValidDate("29.13.2019"), "29.02.2019");
        assertNull(Utils.getValidDate("29.13.1999"), "29.02.1999");
        assertNull(Utils.getValidDate("29.03.1999"), "29.03.1999");
        assertNull(Utils.getValidDate("29.3.1999"), "29.3.1999");
        assertNull(Utils.getValidDate("09.03.1999"), "09.03.1999");
        assertNull(Utils.getValidDate("9.03.1999"), "9.03.1999");
        assertNull(Utils.getValidDate("9.3.1999"), "9.3.1999");

        // test for wrong date dash
        assertNull(Utils.getValidDate("0-10-2019"), "0-10-2019");
        assertNull(Utils.getValidDate("34-01-2019"), "34-01-2019");
        assertNull(Utils.getValidDate("29-02-2019"), "29-02-2019");
        assertNull(Utils.getValidDate("29-13-2019"), "29-13-2019");
        assertNull(Utils.getValidDate("29-13-1999"), "29-13-1999");
        assertNull(Utils.getValidDate("29-03-1999"), "29-03-1999");
        assertNull(Utils.getValidDate("29-3-1999"), "29-3-1999");
        assertNull(Utils.getValidDate("09-03-1999"), "09-03-1999");
        assertNull(Utils.getValidDate("9-03-1999"), "9-03-1999");
        assertNull(Utils.getValidDate("9-3-1999"), "9-3-1999");
    }

    @Test
    public void testDateSplitting_True_SplittingIsValid()
    {
        // Test for years until 2099
        for (int year = 1970; year <= 2099; year++)
        {
            for (int month = 1; month <= 12; month++)
            {
                for (int day = 1; day <= 31; day++)
                {
                	// Without leading Zero
                    assertNotNull(Utils.splitDateString(day + "." + month + "." + year),
                            day + "." + month + "." + year);
                    assertNotNull(Utils.splitDateString(day + "-" + month + "-" + year),
                            day + "-" + month + "-" + year);
                    
                    // With leading Zero
                    assertNotNull(Utils.splitDateString(String.format("%02d", day) + "." + String.format("%02d", month) + "." + String.format("%02d", year)),
                    		String.format("%02d", day) + "." + String.format("%02d", month) + "." + String.format("%02d", year));
                    assertNotNull(Utils.splitDateString(String.format("%02d", day) + "-" + String.format("%02d", month) + "-" + String.format("%02d", year)),
                    		String.format("%02d", day) + "-" + String.format("%02d", month) + "-" + String.format("%02d", year));
                }
            }
        }
    }
}
