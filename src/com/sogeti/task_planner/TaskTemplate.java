package com.sogeti.task_planner;

import java.awt.Color;
import java.awt.Dimension;
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
				System.out.println("Edit");
			}
			else if (selection == 1)
			{
				String optionsDone[] = {"Done", "Abort"};
				int selectionDelete = JOptionPane.showOptionDialog(null, "Do you want to set the Task to done?\nThe task will no longer be visible!", "Delete Task", 0, JOptionPane.WARNING_MESSAGE, null, optionsDone, null);
				if (selectionDelete == 1)
				{
					System.out.println("Abort Done");
					return;
				}
				TaskPlanner.getDBManager().finishTask(nID);
				System.out.println("Done");
			}
			else if (selection == 2)
			{
				System.out.println("Abort");
			}
			System.out.println("Pressed");
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

	// Eindeutige ID, welche in der Datenbank zugewiesen wird
	private TaskTemplate	task;
	private int				nID	= 0;

	// Deklarierung der nötigen Elemente
	private JLabel		lblTitle	= new JLabel("Title");
	private JLabel		lblDueDate	= new JLabel("Due date");
	private JLabel		lblStatus	= new JLabel("Status");
	private JTextArea	taContent	= new JTextArea("Content");
	private JScrollPane	spContent	= new JScrollPane(taContent);

	// Variabelen die allgemein benötigt werden
	private final static Dimension	DIMENSION_TASK		= new Dimension(209, 160);	// Anzeige größe der Aufgabe
	private final static int		SPACE_BETWEEN		= 13;						// Abstand zwischen 2 Aufgaben

	public final static Color	COLOR_OK_DUE	= new Color(162, 205, 90);	// Farbe ok
	public final static Color	COLOR_SOON_DUE	= new Color(238, 238, 0);	// Farbe bald fällig
	public final static Color	COLOR_OVER_DUE	= new Color(238, 92, 66);	// Farbe überfällig

	public TaskTemplate()
	{
		// Eigenschaften des Fensters festlegen
		task = this;
		setBounds(0, 0, (int) DIMENSION_TASK.getWidth(), (int) DIMENSION_TASK.getHeight());
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		addMouseListener(taskListener);

		// Bearbeiten der Eigenschaften der Elemente
		// Titel
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 6, 189, 18);

		// Fälligskeitsdatum
		lblDueDate.setBounds(10, 30, 189, 14);

		// Status
		lblStatus.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStatus.setBounds(109, 30, 90, 14);

		// Inhalt
		taContent.setEditable(false);
		taContent.setLineWrap(true);
		taContent.setWrapStyleWord(true);
		taContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		taContent.addMouseListener(taskListener);

		spContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spContent.setBorder(new LineBorder(new Color(0, 0, 0)));
		spContent.setBounds(0, 50, 209, 110);

		// Hinzufügen der Elemente in den Hauptcontainer
		add(lblTitle);
		add(lblDueDate);
		add(lblStatus);
		add(spContent);
	}

	// Deklarierung aller benötigten Methoden

	public static Dimension getTaskDimension()
	{
		return DIMENSION_TASK;
	}

	public static int getTaskDimensionWidth()
	{
		return (int) DIMENSION_TASK.getWidth();
	}

	public static int getTaskDimensionHeight()
	{
		return (int) DIMENSION_TASK.getHeight();
	}

	public static int getSpaceBetween()
	{
		return SPACE_BETWEEN;
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
		lblTitle.setText(title.substring(0, Math.min(title.length(), Utils.TITLE_MAX_LENGTH - 1)));
	}

	public String getTitle()
	{
		return lblTitle.getText();
	}

	public void setDueDate(String dueDate)
	{
		lblDueDate.setText(dueDate);
	}

	public String getDueDate()
	{
		return lblDueDate.getText();
	}

	public void setStatus(String status)
	{
		lblStatus.setText(status);
	}

	public String getStatus()
	{
		return lblStatus.getText();
	}

	public void setContent(String content)
	{
		taContent.setText(content.substring(0, Math.min(content.length(), Utils.CONTENT_MAX_LENGTH - 1)));
	}

	public String getContent()
	{
		return taContent.getText();
	}

	public void deleteTask()
	{
		TaskPlanner.getDBManager().finishTask(nID);
	}
}
