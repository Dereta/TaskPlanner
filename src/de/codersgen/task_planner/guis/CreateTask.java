package de.codersgen.task_planner.guis;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import de.codersgen.task_planner.TaskPlanner;
import de.codersgen.task_planner.utils.Utils;

public class CreateTask extends JFrame
{
    private static final long serialVersionUID = 1L;

    private ActionListener buttonListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Button Eintragen gedrückt
            // Aufgabe in die Datenbank eintragen
            if (e.getSource().equals(buttonEnter))
            {
                // Title enthält keinen text
                if (textTitle.getText().equalsIgnoreCase("Title") && textTitle.getForeground() == Color.LIGHT_GRAY)
                {
                    JOptionPane.showMessageDialog(null, "Please fill out the title.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // DueDate enthält keinen Text
                if (textDueDate.getText().equalsIgnoreCase("Title") && textDueDate.getForeground() == Color.LIGHT_GRAY)
                {
                    JOptionPane.showMessageDialog(null, "Please fill out the due date.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Content enthält keinen Text
                if (textContent.getText().equalsIgnoreCase("Content") && textContent.getForeground() == Color.LIGHT_GRAY)
                {
                    JOptionPane.showMessageDialog(null, "Please fill out the content.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Inhalt Limitieren
                if (textTitle.getText().length() >= Utils.TITLE_MAX_LENGTH)
                    textTitle.setText(textTitle.getText().substring(0, Utils.TITLE_MAX_LENGTH));
                if (textDueDate.getText().length() >= Utils.DUE_DATE_MAX_LENGTH)
                    textDueDate.setText(textDueDate.getText().substring(0, Utils.DUE_DATE_MAX_LENGTH));
                if (textContent.getText().length() >= Utils.CONTENT_MAX_LENGTH)
                    textContent.setText(textContent.getText().substring(0, Utils.CONTENT_MAX_LENGTH));

                // DueDate überprüfen
                String correctDate = Utils.getValidDate(textDueDate.getText());
                if (correctDate == null)
                {
                    JOptionPane.showMessageDialog(null,
                            "Erroneous date format.\nPlease check the date.\ne.g. dd.mm.yyyy > 19.02.2019", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    textDueDate.requestFocus();
                    return;
                }

                TaskPlanner.getDatabaseManager().insertTask(textTitle.getText(), correctDate,
                        String.valueOf(comboBoxState.getSelectedItem()), textContent.getText());
                textTitle.setForeground(Color.LIGHT_GRAY);
                textTitle.setText("Title");
                textDueDate.setForeground(Color.LIGHT_GRAY);
                textDueDate.setText("dd.mm.yyyy");
                textContent.setForeground(Color.LIGHT_GRAY);
                textContent.setText("Content");
                setVisible(false);
            }
            // Button Abbrechen gedrückt
            // Inhalt der Eingabefelder zurücksetzen
            else if (e.getSource().equals(buttonAbort))
            {
                textTitle.setForeground(Color.LIGHT_GRAY);
                textTitle.setText("Title");
                textDueDate.setForeground(Color.LIGHT_GRAY);
                textDueDate.setText("dd.mm.yyyy");
                textContent.setForeground(Color.LIGHT_GRAY);
                textContent.setText("Content");
                setVisible(false);
            }
        }
    };

    private FocusListener fieldListener = new FocusListener()
    {
        @Override
        public void focusLost(FocusEvent e)
        {
            if (e.getSource().equals(textTitle))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty())
                {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText("Title");
                }
            }
            else if (e.getSource().equals(textDueDate))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty())
                {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText("dd.mm.yyyy");
                }
            }
            else if (e.getSource().equals(textContent))
            {
                JTextArea textArea = (JTextArea) e.getSource();
                if (textArea.getText().isEmpty())
                {
                    textArea.setForeground(Color.LIGHT_GRAY);
                    textArea.setText("Content");
                }
            }
        }

        @Override
        public void focusGained(FocusEvent e)
        {
        	Object object = e.getSource();
            if (object.equals(textTitle))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().equalsIgnoreCase("Title") && textField.getForeground() == Color.LIGHT_GRAY)
                {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }
            else if (object.equals(textDueDate))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().equalsIgnoreCase("dd.mm.yyyy") && textField.getForeground() == Color.LIGHT_GRAY)
                {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }
            else if (object.equals(textContent))
            {
                JTextArea textArea = (JTextArea) e.getSource();
                if (textArea.getText().equalsIgnoreCase("Content") && textArea.getForeground() == Color.LIGHT_GRAY)
                {
                    textArea.setForeground(Color.BLACK);
                    textArea.setText("");
                }
            }
        }
    };

    private JPanel panel;

    private JLabel labelTitle   = new JLabel("Title");
    private JLabel labelDueDate = new JLabel("Due date");
    private JLabel labelState   = new JLabel("State");
    private JLabel labelContent = new JLabel("Content");

    private JTextField  textTitle    = new JTextField("Title");
    private JTextField  textDueDate  = new JTextField("dd.mm.yyyy");
    private JScrollPane scrollPaneContent = new JScrollPane();
    private JTextArea   textContent  = new JTextArea("Content");

    private String[]          states  		= new String[] { "Todo", "In Work" };
    private JComboBox<String> comboBoxState = new JComboBox<String>(states);

    private JButton buttonEnter = new JButton("Enter");
    private JButton buttonAbort = new JButton("Abort");

    // constructor
    public CreateTask()
    {
        setTitle("Create Task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 253, 324);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(null);
        setContentPane(panel);

        labelTitle.setLocation(10, 11);
        labelTitle.setSize(227, 14);
        textTitle.setForeground(Color.LIGHT_GRAY);
        textTitle.setLocation(10, 36);
        textTitle.setSize(227, 20);
        textTitle.addFocusListener(fieldListener);
        textTitle.requestFocus();

        labelDueDate.setLocation(10, 67);
        labelDueDate.setSize(105, 14);
        textDueDate.setForeground(Color.LIGHT_GRAY);
        textDueDate.setLocation(10, 92);
        textDueDate.setSize(105, 20);
        textDueDate.addFocusListener(fieldListener);

        labelState.setLocation(132, 67);
        labelState.setSize(105, 14);
        comboBoxState.setLocation(132, 92);
        comboBoxState.setSize(105, 20);

        labelContent.setLocation(10, 123);
        labelContent.setSize(227, 14);
        textContent.setForeground(Color.LIGHT_GRAY);
        textContent.setLineWrap(true);
        textContent.setWrapStyleWord(true);
        textContent.addFocusListener(fieldListener);

        scrollPaneContent = new JScrollPane(textContent);
        scrollPaneContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneContent.setBounds(10, 148, 227, 100);

        buttonEnter.setLocation(10, 259);
        buttonEnter.setSize(105, 30);
        buttonEnter.addActionListener(buttonListener);

        buttonAbort.setLocation(132, 259);
        buttonAbort.setSize(105, 30);
        buttonAbort.addActionListener(buttonListener);

        panel.add(labelTitle);
        panel.add(textTitle);
        panel.add(labelDueDate);
        panel.add(textDueDate);
        panel.add(labelState);
        panel.add(comboBoxState);
        panel.add(labelContent);
        panel.add(scrollPaneContent);
        panel.add(buttonEnter);
        panel.add(buttonAbort);
    }
}
