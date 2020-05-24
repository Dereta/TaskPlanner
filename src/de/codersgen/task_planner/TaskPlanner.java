package de.codersgen.task_planner;

import java.awt.EventQueue;

import de.codersggen.task_planner.guis.CreateTask;
import de.codersggen.task_planner.guis.EditTask;
import de.codersggen.task_planner.guis.Main;

public class TaskPlanner
{
    // Erstellen der Objekte der beiden
    // Fenster über das Schlüsselwort "new"
    private static Main   mainGUI   = new Main();
    private static CreateTask createGUI = new CreateTask();
    private static EditTask   editGUI   = new EditTask();
    private static DBManager dbManager = new DBManager();

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                // Anzeigen des Hauptfensters und verstecken des erstellen Fensters
                mainGUI.setVisible(true);
                createGUI.setVisible(false);
                editGUI.setVisible(false);
                dbManager.getTasks();
            }
        });
    }

    public static Main getMainGUI()
    {
        return mainGUI;
    }

    public static CreateTask getCreateGUI()
    {
        return createGUI;
    }

    public static EditTask getEditGUI()
    {
        return editGUI;
    }

    public static DBManager getDBManager()
    {
        return dbManager;
    }
}
