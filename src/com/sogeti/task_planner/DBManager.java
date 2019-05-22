package com.sogeti.task_planner;

import java.sql.Statement;

import javax.swing.JOptionPane;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager
{
    // Treiber Name, Speicherort und Datenbankname festlegen
    String driver   = "jdbc:sqlite:";
    String location = "./res/";
    String database = "taskPlanner.db";

    // Connection die während der Laufzeit des Programms offen gehalten wird
    Connection connection = null;

    // Konstruktor indem die Verbindung zur Datenbank aufgebaut wird
    // Sollte dies nicht möglich sein, wird eine Fehlermeldung ausgegeben
    // und das Programm wird beendet
    public DBManager()
    {
        try
        {
            // Ordner res erstellen und Verbindung zur Datenbank aufbauen
            new File("res").mkdir();
            connection = DriverManager.getConnection(driver + location + database);
            System.out.println("Connection to Database successfully");
        }
        catch (SQLException ex)
        {
            // Verbindung Fehlgeschlagen
            System.out.println("Connection to Database failed");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not connect to the database.", "Error", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        // Methode aufrufen
        createDatabase();
    }

    // Schließe alles, sobald die Datenbank nicht mehr benötigt wird
    public void closeDatebase()
    {
        try
        {
            // Schließen der Datenbank erfolgreich
            connection.close();
        }
        catch (SQLException ex)
        {
            // Schließen der Datenbank fehlgeschlagen
            System.out.println("Error while closen connection");
            ex.printStackTrace();
        }
    }

    // Erstellen der Tabelle "tasks", insofern diese nicht vorhanden ist
    public void createDatabase()
    {
        // Aufbau der Tabelle
        String statementQuery = "CREATE TABLE IF NOT EXISTS tasks (id integer PRIMARY KEY,\n"
                + "	title text NOT NULL, dueDate text NOT NULL, status text NOT NULL, content text NOT NULL);";
        try
        {
            // Erstellen der Tabelle erfolgreich
            Statement statement = connection.createStatement();
            statement.execute(statementQuery);
            System.out.println("Table created successfully");
        }
        catch (SQLException ex)
        {
            // Erstellen der Tabelle fehlgeschlagen
            System.out.println("The table could not be create");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "The table could not be created.", "Error", JOptionPane.OK_OPTION);
            System.exit(0);
        }
    }

    // Einfügen eines Eintrages in die Datenbank
    public void insertTask(String title, String dueDate, String status, String content)
    {
        // Aufbau der Eintragung
        String statementQuery = "INSERT INTO tasks\n" + " (title, dueDate, status, content)" + " VALUES"
                + " (?, ?, ?, ?)";
        try
        {
            // Eintragen der Daten erfolgreich
            System.out.println("insert data successfully");
            PreparedStatement statement = connection.prepareStatement(statementQuery);
            statement.setString(1, title);
            statement.setString(2, dueDate);
            statement.setString(3, status);
            statement.setString(4, content);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            // Eintragen der Daten fehlgeschlagen
            System.out.println("Cannot insert data into table");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot insert data into table", "Error", JOptionPane.OK_OPTION);
        }
        getTasks();
    }

    // Updaten vorhandener Einträge der Datenbank
    public void updateTask(int id, String title, String dueDate, String status, String content)
    {
        // Aufbau des Updates
        String statementQuery = "UPDATE tasks SET\n" + "title = ?, dueDate = ?, status = ?, content = ?"
                + " WHERE id = ?";
        try
        {
            // Updaten der Daten erfolgreich
            System.out.println("update data successfully");
            PreparedStatement statement = connection.prepareStatement(statementQuery);
            statement.setString(1, title);
            statement.setString(2, dueDate);
            statement.setString(3, status);
            statement.setString(4, content);
            statement.setInt(5, id);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            // Updaten der Daten fehlgeschlagen
            System.out.println("cannot update data");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "cannot update data", "Error", JOptionPane.OK_OPTION);
        }
        getTasks();
    }

    // Löschen eines Eintrages aus der Datenbank
    public void finishTask(int id)
    {
        // Aufbau der Fertigstellung
        String statementQuery = "UPDATE tasks SET status = 'done' WHERE id = ?";

        try
        {
            // Fertigstellung der Daten erfolgreich
            System.out.println("finish entry successfully");
            PreparedStatement statement = connection.prepareStatement(statementQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            // Fertigstellung der Daten fehlgeschlagen
            System.out.println("cannot finish data");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "cannot finish data", "Error", JOptionPane.OK_OPTION);
        }
        getTasks();
    }

    // Auslesen aller Datensätze
    public void getTasks()
    {
        // Aufbau des Auslesens
        // Sortierung anhand des Datums und des Titles (Alphanumerisch)
        String statementQuery = "SELECT * FROM tasks WHERE status NOT LIKE '%done%' ORDER BY DATE(dueDate) ASC, title ASC";
        if (Utils.SHOW_DONE)
            // Ausgabe von allen Texten, auch den fertiggestellten
            statementQuery = "SELECT * FROM tasks ORDER BY DATE(dueDate) ASC, title ASC";

        try
        {
            // Auslesen der Daten erfolgreich
            System.out.println("select entrys successfully");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(statementQuery);
            TaskPlanner.getMainGUI().removeTasks();
            System.out.println("remove task");
            while (result.next())
            {
                // Ausgelesene Einträge in das Hauptfenster einfügen
                TaskPlanner.getMainGUI().addTask(result.getInt("id"), result.getString("title"),
                        result.getString("dueDate"), result.getString("status"), result.getString("content"));
                System.out.println("add task");
            }
            TaskPlanner.getMainGUI().reorderTasks();
            System.out.println("reorder task");
        }
        catch (SQLException ex)
        {
            // Auslesen der Daten fehlgeschlagen
            System.out.println("cannot get data");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "cannot get data", "Error", JOptionPane.OK_OPTION);
        }
    }
}
