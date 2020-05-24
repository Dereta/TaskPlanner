package de.codersgen.task_planner.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class Utils
{
    // Static values
    public static int               TITLE_MAX_LENGTH    = 22;
    public static int               DUE_DATE_MAX_LENGTH = 10;
    public static int               CONTENT_MAX_LENGTH  = 255;
    public static boolean           SHOW_DONE           = false;
    private static final String     DATE_PATTERN        = "([1-9]|0[1-9]|[12][0-9]|3[01])[-\\.]([0-9]|0[1-9]|1[012])[-\\.](2[0-9]{3})";
    private static SimpleDateFormat DATE_FORMAT         = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public final static Dimension TASK_SIZE = new Dimension(209, 160); 	// Task size
    public final static int       TASK_PADDING  = 13;                      // Task padding
    
    public final static int TIME_SOON_DUE = (1000 * 60 * 60 * 24);
    public final static Color COLOR_BORDER   = new Color(0, 0, 0);		// Color Border
    public final static Color COLOR_OK_DUE   = new Color(162, 205, 90); // Color Task due date is ok
    public final static Color COLOR_SOON_DUE = new Color(238, 238, 0);  // Color Task due date is soon
    public final static Color COLOR_OVER_DUE = new Color(238, 92, 66);  // Color Task due date is over

    // Check if datestring is a valid date
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

    // Split date array
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
       
        return new String[] {temp[2], temp[1], temp[0]};
    }

    // Format date array correcly for further ussage
    private static String getCorrectDateFormat(String[] dateArray)
    {
        return String.join("-",  dateArray) + " 00:00:00";
    }
    

    // Print error with message
	public static void showError(String message) 
	{
		showError(null, message);
	}
    
	// Print error with exception
	public static void showError(Exception ex) 
	{
		showError(ex, null);
	}
    
	// Print error with exception and message
    public static void showError(Exception ex, String message) 
    {
    	 if (ex != null) {
    		 ex.printStackTrace(); 
    	 }
    	 if (message != null) {
    		 JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.OK_OPTION); 
    	 }
    }
}
