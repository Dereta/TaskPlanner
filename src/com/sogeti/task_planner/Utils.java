package com.sogeti.task_planner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utils
{
	// Feste Variabelen
	public static int TITLE_MAX_LENGTH = 22;
	public static int DUE_DATE_MAX_LENGTH = 10;
	public static int CONTENT_MAX_LENGTH = 255;
	public static boolean SHOW_DONE = false;
	private static final String DATE_PATTERN = "([1-9]|0[1-9]|[12][0-9]|3[01])[-\\.]([0-9]|0[1-9]|1[012])[-\\.](2[0-9]{3})";
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// Funktion zum überprüfen ob das eingegebene Zeitformat richtig ist
	public static String getValidDate(String dateToValidate)
	{
		if (!dateToValidate.matches(DATE_PATTERN) || null == splitDateString(dateToValidate))
			return null;
			
		String dateCorrect = getCorrectDateFormat(splitDateString(dateToValidate));
		
		try
		{
			DATE_FORMAT.setLenient(false);
			DATE_FORMAT.parse(dateCorrect);
		}
		catch (ParseException e)
		{
			return null;
		}
		return dateCorrect;
	}
	
	public static String[] splitDateString(String dateString)
	{
		String[] temp = null;
		if (dateString.contains("."))
			temp = dateString.split("\\.");
		else if (dateString.contains("-"))
			temp = dateString.split("-");
		else
			return null;
		
		if (temp.length != 3)
			return null;
		
		return temp;
	}
	
	private static String getCorrectDateFormat(String[] dateArray)
	{		
		return dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0] + " 00:00:00";
	}
}
