package com.sogeti.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.sogeti.task_planner.Utils;

public class MyTests
{
	@Test
	public void testDateParsing()
	{
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
	public void testDateSplitting()
	{
		// tests for dots
		assertNotNull(Utils.splitDateString("10.10.2019"), "10.10.2019");
		assertNotNull(Utils.splitDateString("01.01.2019"), "01.01.2019");
		assertNotNull(Utils.splitDateString("1.01.2019"), "1.01.2019");
		assertNotNull(Utils.splitDateString("01.1.2019"), "01.1.2019");
		assertNotNull(Utils.splitDateString("1.1.2019"), "1.1.2019");
		
		// tests for dash
		assertNotNull(Utils.splitDateString("10-10-2019"), "10-10-2019");
		assertNotNull(Utils.splitDateString("01-01-2019"), "01-01-2019");
		assertNotNull(Utils.splitDateString("1-01-2019"), "1-01-2019");
		assertNotNull(Utils.splitDateString("01-1-2019"), "01-1-2019");
		assertNotNull(Utils.splitDateString("1-1-2019"), "1-1-2019");
		
		// Test for years until 2099
		for (int year = 1970; year <= 2099; year++)
		{
			for (int month = 1; month <= 12; month++)
			{
				for (int day = 1; day <= 31; day++)
				{
					assertNotNull(Utils.splitDateString(day + "." + month + "." + year), day + "." + month + "." + year);
					assertNotNull(Utils.splitDateString(day + "-" + month + "-" + year), day + "." + month + "." + year);
				}
			}
		}
	}
}
