package de.codersgen.task_planner.utils;

import java.sql.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.codersgen.task_planner.TaskPlanner;

public class DatabaseManager
{
    // Default Values
    String driver   = "jdbc:sqlite:";
    String location = "./res/";
    String database = "taskPlanner.db";

    // SQL Statements#
    String stmCreateDatabase = "CREATE TABLE IF NOT EXISTS tasks (id integer PRIMARY KEY, "
    		+ "title text NOT NULL, dueDate text NOT NULL, status text NOT NULL, content text NOT NULL);";
    String stmInsertTask = "INSERT INTO tasks (title, dueDate, status, content) VALUES (?, ?, ?, ?)";
    String stmUpdateTask = "UPDATE tasks SET title = ?, dueDate = ?, status = ?, content = ? WHERE id = ?";
    String stmFinishTask = "UPDATE tasks SET status = 'done' WHERE id = ?";
    String stmGetTasks = "SELECT * FROM tasks WHERE status NOT LIKE '%done%' ORDER BY DATE(dueDate) ASC, title ASC";
    String stmGetAllTasks = "SELECT * FROM tasks ORDER BY DATE(dueDate) ASC, title ASC";
    
    // Database connection
    Connection connection = null;

    // Connect to database
    public DatabaseManager()
    {
        try
        {
            File directory = new File(location);
            if (!directory.exists())
            {
            	directory.mkdir();
            }
            connection = DriverManager.getConnection(driver + location + database);
        }
        catch (SQLException ex)
        {
            Utils.showError(ex, "error while open connection");
            System.exit(0);
        }

        createDatabase();
    }

    // Close database
    public void closeDatebase()
    {
        try
        {
        	if (connection.isValid(1))
        	{
        		connection.close();
        	}
        }
        catch (SQLException ex)
        {
            Utils.showError(ex, "error while close connection");
        }
    }

    private boolean isConnectionValid() 
    {
    	 try 
    	 {
    		 return connection.isValid(1);
    	 } catch (SQLException ex) {
			 Utils.showError(ex, "error while checking connection");
			 return false;
    	 }
    }
    
    // Create task table
    public void createDatabase()
    {
    	if (!isConnectionValid()) {
    		Utils.showError("createDatabase : connection is not valid");
    		return;
    	}
    	
        try
        {
            Statement statement = connection.createStatement();
            statement.execute(stmCreateDatabase);
        }
        catch (SQLException ex)
        {
            Utils.showError(ex, "error while create task table");
            System.exit(0);
        }
    }    
    
    // Add task
    public void insertTask(String title, String dueDate, String status, String content)
    {
    	if (!isConnectionValid()) {
    		Utils.showError("insertTask : connection is not valid");
    		return;
    	}
    	
        try
        {
            PreparedStatement statement = connection.prepareStatement(stmInsertTask);
            statement.setString(1, title);
            statement.setString(2, dueDate);
            statement.setString(3, status);
            statement.setString(4, content);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Utils.showError(ex, "error while insert tasks");
        }
        getTasks();
    }

    // Update task
    public void updateTask(int id, String title, String dueDate, String status, String content)
    {
    	if (!isConnectionValid()) {
    		Utils.showError("updateTask : connection is not valid");
    		return;
    	}
    	
        try
        {
            PreparedStatement statement = connection.prepareStatement(stmUpdateTask);
            statement.setString(1, title);
            statement.setString(2, dueDate);
            statement.setString(3, status);
            statement.setString(4, content);
            statement.setInt(5, id);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Utils.showError(ex, "error while updating tasks");
        }
        getTasks();
    }

    // Finish task
    public void finishTask(int id)
    {
    	if (!isConnectionValid()) {
    		Utils.showError("finishTask : connection is not valid");
    		return;
    	}
    	
        try
        {
            PreparedStatement statement = connection.prepareStatement(stmFinishTask);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Utils.showError(ex, "error while finish task");
        }
        getTasks();
    }

    // Get all tasks
    public void getTasks()
    {
    	if (!isConnectionValid()) {
    		Utils.showError("getTasks : connection is not valid");
    		return;
    	}
    	
        try
        {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery((Utils.SHOW_DONE ? stmGetAllTasks : stmGetTasks));
            TaskPlanner.getMainGUI().removeTasks();
            while (result.next())
            {
                TaskPlanner.getMainGUI().addTask(result.getInt("id"), result.getString("title"),
                        result.getString("dueDate"), result.getString("status"), result.getString("content"));
            }
            TaskPlanner.getMainGUI().reorderTasks();
        }
        catch (SQLException ex)
        {
        	Utils.showError(ex, "error while getting tasks");
        }
    }
}
