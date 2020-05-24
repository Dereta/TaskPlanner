package de.codersggen.task_planner.guis;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import de.codersgen.task_planner.TaskPlanner;
import de.codersgen.task_planner.TaskTemplate;
import de.codersgen.task_planner.Utils;

import javax.swing.JCheckBox;

public class Main extends JFrame
{
    private static final long serialVersionUID = 1L;

    private ActionListener actionListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Aktion wenn "NewTask" Button gedrückt wird
            if (e.getSource().equals(btnNewTask))
            {
                TaskPlanner.getCreateGUI().setVisible(true);
                return;
            }
            // Aktion "Show Finished tasks" CheckBox gedrückt
            else if (e.getSource().equals(cbShowDone))
            {
                Utils.SHOW_DONE = cbShowDone.isSelected();
                TaskPlanner.getDBManager().getTasks();
                return;
            }
        }
    };

    private JPanel    contentPane;
    private JButton   btnNewTask;
    private JCheckBox cbShowDone;

    private JScrollPane   taskContainer = new JScrollPane();
    private static JPanel taskView      = new JPanel();

    public Main()
    {
        // Windows Size
        Rectangle windowsSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        // Eigenschaften des Fensters festlegen
        setTitle("TaskPlanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds((int) windowsSize.getWidth() - 253, 0, 253, (int) windowsSize.getHeight());
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Bearbeiten der Eigenschaften der Elemente
        // Neue Aufgabe
        btnNewTask = new JButton("New task");
        btnNewTask.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnNewTask.setBounds(10, 11, 229, 30);
        btnNewTask.addActionListener(actionListener);

        // Erledigte Aufgaben einblenden
        cbShowDone = new JCheckBox("Show finished tasks");
        cbShowDone.setBounds(6, 48, 235, 23);
        cbShowDone.addActionListener(actionListener);

        // Aufgaben Container
        taskView.setLayout(null);
        taskContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        taskContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        taskContainer.setBounds(10, 78, 227, 921);
        taskContainer.setViewportView(taskView);

        // Hinzufügen der Elemente in den Hauptcontainer
        contentPane.add(btnNewTask);
        contentPane.add(cbShowDone);
        contentPane.add(taskContainer);
    }

    // Neue Aufgaben hinzufügen
    public void addTask(int id, String title, String dueDate, String status, String content)
    {
        // Datum und Uhrzeit aufteilen
        String dueDateSplit[] = dueDate.split(" ");
        if (dueDateSplit.length != 2)
            return;

        // Datum in seine bestandteile, teilen
        String dueDateTemp[] = dueDateSplit[0].split("-");
        if (dueDateTemp.length != 3)
            return;

        // Zeitunterschied berechen um zu überprüfen
        // welche Einträge überfällig sind
        GregorianCalendar dateToday = new GregorianCalendar();
        GregorianCalendar dateTask = new GregorianCalendar(Integer.parseInt(dueDateTemp[0]),
                Integer.parseInt(dueDateTemp[1]) - 1, Integer.parseInt(dueDateTemp[2]));
        long dateDifference = (dateTask.getTimeInMillis() - dateToday.getTimeInMillis());

        // Neue Aufgabe erstellen und mit Inhalt füllen
        TaskTemplate taskTemplate = new TaskTemplate();
        taskTemplate.setID(id);
        taskTemplate.setTitle(title);
        taskTemplate.setDueDate(dueDateTemp[2] + "." + dueDateTemp[1] + "." + dueDateTemp[0]);
        taskTemplate.setStatus(status);
        taskTemplate.setContent(content);

        // Farbe Anhand des Zeitunterschiedes festlegen
        if (dateDifference <= 0)
            taskTemplate.setDueColor(TaskTemplate.COLOR_OVER_DUE);
        else if (dateDifference > 0 && dateDifference < (1000 * 60 * 60 * 24))
            taskTemplate.setDueColor(TaskTemplate.COLOR_SOON_DUE);
        else
            taskTemplate.setDueColor(TaskTemplate.COLOR_OK_DUE);

        // Aufgabe in das Hauptfenster hinzufügen
        taskView.add(taskTemplate);
    }

    // Aufgaben Löschen
    public void removeTasks()
    {
        // Alle vorhandenen Aufgaben aus dem Hauptfenster löschen
        taskView.removeAll();
    }

    // Aufgaben ausrichten
    public void reorderTasks()
    {
        // Gehe durch jede einzelne Aufgabe die vorhanden ist
        for (int i = 0; i <= taskView.getComponentCount() - 1; i++)
        {
            TaskTemplate taskTemplate = (TaskTemplate) taskView.getComponent(i);
            // Richte Aufgabe an Position n aus
            taskTemplate.setBounds(0, (i * taskTemplate.getHeight()) + (i * TaskTemplate.getSpaceBetween()),
                    taskTemplate.getWidth(), taskTemplate.getHeight());
        }
        // Setze das Fenster so, dass die Scrollbar richtig scrollt
        taskView.setPreferredSize(new Dimension(taskView.getWidth(),
                taskView.getComponentCount() * (int) TaskTemplate.getTaskDimensionHeight()
                        + (taskView.getComponentCount() - 1) * TaskTemplate.getSpaceBetween()));
        revalidate();
        repaint();
    }
}
