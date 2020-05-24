package de.codersgen.task_planner;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import de.codersgen.task_planner.utils.Utils;

public class TaskTemplate extends JPanel
{
    private static final long serialVersionUID = 1L;

    private MouseListener taskListener = new MouseListener()
    {

        @Override
        public void mouseReleased(MouseEvent e)
        {
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            String options[] =
            { "Edit", "Done", "Abort" };
            int selection = JOptionPane.showOptionDialog(null, "What do you want to do with the task?", "Task", 0,
                    JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (selection == 0)
            {
                TaskPlanner.getEditGUI().setVisible(true);
                TaskPlanner.getEditGUI().setTask(task);
            }
            else if (selection == 1)
            {
                String optionsDone[] =
                { "Done", "Abort" };
                int selectionDelete = JOptionPane.showOptionDialog(null,
                        "Do you want to set the Task to done?\nThe task will no longer be visible!", "Delete Task", 0,
                        JOptionPane.WARNING_MESSAGE, null, optionsDone, null);
                if (selectionDelete == 1)
                {
                    return;
                }
                TaskPlanner.getDatabaseManager().finishTask(nID);
                System.out.println("Done");
            }
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
        }
    };

    // values
    private TaskTemplate task;
    private int          nID = 0;
    
    // declare ui elements
    private JLabel      labelTitle   = new JLabel("Title");
    private JLabel      labelDueDate = new JLabel("Due date");
    private JLabel      labelStatus  = new JLabel("Status");
    private JTextArea   textContent  = new JTextArea("Content");
    private JScrollPane panelContent  = new JScrollPane(textContent);

    // Constructor
    public TaskTemplate()
    {
        task = this;
        setBounds(0, 0, (int) Utils.TASK_SIZE.getWidth(), (int) Utils.TASK_SIZE.getHeight());
        setBorder(new LineBorder(Utils.COLOR_BORDER));
        setLayout(null);
        addMouseListener(taskListener);

        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setBounds(10, 6, 189, 18);

        labelDueDate.setBounds(10, 30, 189, 14);

        labelStatus.setHorizontalAlignment(SwingConstants.TRAILING);
        labelStatus.setBounds(109, 30, 90, 14);

        textContent.setEditable(false);
        textContent.setLineWrap(true);
        textContent.setWrapStyleWord(true);
        textContent.setBorder(new EmptyBorder(5, 5, 5, 5));
        textContent.addMouseListener(taskListener);

        panelContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelContent.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelContent.setBounds(0, 50, 209, 110);

        add(labelTitle);
        add(labelDueDate);
        add(labelStatus);
        add(panelContent);
    }
    public void setID(int id)
    {
        nID = id;
    }

    public int getID()
    {
        return nID;
    }

    public void setDueColor(Color color)
    {
        setBackground(color);
    }

    public void setTitle(String title)
    {
        labelTitle.setText(title.substring(0, Math.min(title.length(), Utils.TITLE_MAX_LENGTH - 1)));
    }

    public String getTitle()
    {
        return labelTitle.getText();
    }

    public void setDueDate(String dueDate)
    {
        labelDueDate.setText(dueDate);
    }

    public String getDueDate()
    {
        return labelDueDate.getText();
    }

    public void setStatus(String status)
    {
        labelStatus.setText(status);
    }

    public String getStatus()
    {
        return labelStatus.getText();
    }

    public void setContent(String content)
    {
        textContent.setText(content.substring(0, Math.min(content.length(), Utils.CONTENT_MAX_LENGTH - 1)));
    }

    public String getContent()
    {
        return textContent.getText();
    }

    public void finsihTask()
    {
        TaskPlanner.getDatabaseManager().finishTask(nID);
    }
}
