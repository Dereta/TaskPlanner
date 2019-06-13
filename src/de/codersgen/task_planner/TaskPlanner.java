package de.codersgen.task_planner;

import java.awt.EventQueue;

public class TaskPlanner
{
    // Erstellen der Objekte der beiden
    // Fenster über das Schlüsselwort "new"
    private static MainGUI   mainGUI   = new MainGUI();
    private static CreateGUI createGUI = new CreateGUI();
    private static EditGUI   editGUI   = new EditGUI();
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

    public static MainGUI getMainGUI()
    {
        return mainGUI;
    }

    public static CreateGUI getCreateGUI()
    {
        return createGUI;
    }

    public static EditGUI getEditGUI()
    {
        return editGUI;
    }

    public static DBManager getDBManager()
    {
        return dbManager;
    }
}
