package de.codersgen.task_planner;

import java.awt.EventQueue;

import de.codersgen.task_planner.guis.CreateTask;
import de.codersgen.task_planner.guis.EditTask;
import de.codersgen.task_planner.guis.Main;
import de.codersgen.task_planner.utils.DatabaseManager;

public class TaskPlanner
{
    private static Main   mainGUI   = new Main();
    private static CreateTask createGUI = new CreateTask();
    private static EditTask   editGUI   = new EditTask();
    private static DatabaseManager databaseManager = new DatabaseManager();

    // Programm entry point
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                mainGUI.setVisible(true);
                createGUI.setVisible(false);
                editGUI.setVisible(false);
                databaseManager.getTasks();
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

    public static DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }
}
