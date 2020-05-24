package de.codersgen.task_planner.guis;

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
import javax.swing.JCheckBox;

import de.codersgen.task_planner.TaskPlanner;
import de.codersgen.task_planner.TaskTemplate;
import de.codersgen.task_planner.utils.Utils;

public class Main extends JFrame
{
    private static final long serialVersionUID = 1L;

    private ActionListener actionListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        	
        	Object object = e.getSource();
            if (object.equals(buttonNewTask))
            {
                TaskPlanner.getCreateGUI().setVisible(true);
                return;
            }
            else if (object.equals(checkBoxShowFinishedTasks))
            {
                Utils.SHOW_DONE = checkBoxShowFinishedTasks.isSelected();
                TaskPlanner.getDatabaseManager().getTasks();
                return;
            }
        }
    };

    private JPanel    contentPanel;
    private JButton   buttonNewTask;
    private JCheckBox checkBoxShowFinishedTasks;

    private JScrollPane   taskContainer = new JScrollPane();
    private static JPanel taskView      = new JPanel();

    // Constructor
    public Main()
    {
        Rectangle windowsSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        setTitle("TaskPlanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds((int) windowsSize.getWidth() - 253, 0, 253, (int) windowsSize.getHeight());
        setResizable(false);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        buttonNewTask = new JButton("New task");
        buttonNewTask.setFont(new Font("Tahoma", Font.PLAIN, 11));
        buttonNewTask.setBounds(10, 11, 229, 30);
        buttonNewTask.addActionListener(actionListener);

        checkBoxShowFinishedTasks = new JCheckBox("Show finished tasks");
        checkBoxShowFinishedTasks.setBounds(6, 48, 235, 23);
        checkBoxShowFinishedTasks.addActionListener(actionListener);

        taskView.setLayout(null);
        taskContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        taskContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        taskContainer.setBounds(10, 78, 227, 921);
        taskContainer.setViewportView(taskView);

        contentPanel.add(buttonNewTask);
        contentPanel.add(checkBoxShowFinishedTasks);
        contentPanel.add(taskContainer);
    }

    // Add Task
    public void addTask(int id, String title, String dueDate, String status, String content)
    {
    	// Split time and date
        String dueDateSplit[] = dueDate.split(" ");
        if (dueDateSplit.length != 2)
            return;

        String dueDateTemp[] = dueDateSplit[0].split("-");
        if (dueDateTemp.length != 3)
            return;

        GregorianCalendar dateToday = new GregorianCalendar();
        GregorianCalendar dateTask = new GregorianCalendar(Integer.parseInt(dueDateTemp[0]),
                Integer.parseInt(dueDateTemp[1]) - 1, Integer.parseInt(dueDateTemp[2]));
        long dateDifference = (dateTask.getTimeInMillis() - dateToday.getTimeInMillis());

        TaskTemplate taskTemplate = new TaskTemplate();
        taskTemplate.setID(id);
        taskTemplate.setTitle(title);
        taskTemplate.setDueDate(dueDateTemp[2] + "." + dueDateTemp[1] + "." + dueDateTemp[0]);
        taskTemplate.setStatus(status);
        taskTemplate.setContent(content);

        if (dateDifference <= 0) 
        {
        	taskTemplate.setDueColor(Utils.COLOR_OVER_DUE);
        }
        else if (dateDifference < Utils.TIME_SOON_DUE) 
        {
        	taskTemplate.setDueColor(Utils.COLOR_SOON_DUE);
        }
        else
        {
        	taskTemplate.setDueColor(Utils.COLOR_OK_DUE);
        }
        taskView.add(taskTemplate);
    }

    // Delete all tasks
    public void removeTasks()
    {
        taskView.removeAll();
    }

    // Reorder all tasks
    public void reorderTasks()
    {
        for (int i = 0; i <= taskView.getComponentCount() - 1; i++)
        {
            TaskTemplate taskTemplate = (TaskTemplate) taskView.getComponent(i);
            int positionX = 0;
            int positionY = (i * taskTemplate.getHeight()) + (i * Utils.TASK_PADDING);
            int width = taskTemplate.getWidth();
            int height = taskTemplate.getHeight();
            taskTemplate.setBounds(positionX, positionY, width, height);
        }
        int viewWidth = taskView.getWidth();
        int viewHeight = taskView.getComponentCount() * (int) Utils.TASK_SIZE.height + (taskView.getComponentCount() - 1) * Utils.TASK_PADDING;
        taskView.setPreferredSize(new Dimension(viewWidth, viewHeight));
        revalidate();
        repaint();
    }
}
