package de.codersggen.task_planner.guis;

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

import de.codersgen.task_planner.TaskPlanner;
import de.codersgen.task_planner.TaskTemplate;
import de.codersgen.task_planner.Utils;

import javax.swing.JScrollPane;

public class EditTask extends JFrame
{
    private static final long serialVersionUID = 1L;

    private ActionListener buttonListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Button Speichern gedrückt
            // Aufgabe überarbeitet Speichern
            if (e.getSource().equals(btnSave))
            {
                // Title enthält keinen text
                if (tfTitle.getText().equalsIgnoreCase("Title") && tfTitle.getForeground() == Color.LIGHT_GRAY
                        || tfDueDate.getText().equalsIgnoreCase("dd.mm.yyyy")
                                && tfDueDate.getForeground() == Color.LIGHT_GRAY
                        || taContent.getText().equalsIgnoreCase("Content")
                                && taContent.getForeground() == Color.LIGHT_GRAY)
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Inhalte Limitieren
                if (tfTitle.getText().length() >= Utils.TITLE_MAX_LENGTH)
                    tfTitle.setText(tfTitle.getText().substring(0, Utils.TITLE_MAX_LENGTH));

                if (tfDueDate.getText().length() >= Utils.DUE_DATE_MAX_LENGTH)
                    tfDueDate.setText(tfDueDate.getText().substring(0, Utils.DUE_DATE_MAX_LENGTH));

                if (taContent.getText().length() >= Utils.CONTENT_MAX_LENGTH)
                    taContent.setText(taContent.getText().substring(0, Utils.CONTENT_MAX_LENGTH));

                String correctDate = Utils.getValidDate(tfDueDate.getText());
                if (correctDate == null)
                {
                    JOptionPane.showMessageDialog(null,
                            "Erroneous date format.\nPlease check the date.\ne.g. dd.mm.yyyy > 19.02.2019", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    tfDueDate.requestFocus();
                    return;
                }

                if (String.valueOf(cbState.getSelectedItem()).equalsIgnoreCase("done"))
                {
                    String options[] =
                    { "Save", "Abort" };
                    int selection = JOptionPane.showOptionDialog(null,
                            "Do you want to set status to 'done'?\nThe Task will no longer be visible!", "Update Task",
                            0, JOptionPane.WARNING_MESSAGE, null, options, null);
                    if (selection == 1)
                    {
                        System.out.println("Abort");
                        return;
                    }
                    System.out.println("Save");
                }

                // Update den Task in der Datenbank
                TaskPlanner.getDBManager().updateTask(task.getID(), tfTitle.getText(), correctDate,
                        String.valueOf(cbState.getSelectedItem()), taContent.getText());

                // Setze GUI zurück
                resetGUI();
            }
            // Button Abbrechen gedrückt
            else if (e.getSource().equals(btnAbort))
            {
                // Setze GUI zurück
                resetGUI();
                System.out.println("Button Abort");
            }
        }
    };

    private FocusListener fieldListener = new FocusListener()
    {
        @Override
        public void focusLost(FocusEvent e)
        {
            // Eingabefelder wenn der Fokus verloren
            // geht mit einem Hint füllen
            if (e.getSource().equals(tfTitle))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty())
                {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText("Title");
                }
            }
            else if (e.getSource().equals(tfDueDate))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty())
                {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText("dd.mm.yyyy");
                }
            }
            else if (e.getSource().equals(taContent))
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
            // Eingabefelder hint löschen
            // wenn sie Fokus bekommen
            if (e.getSource().equals(tfTitle))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().equalsIgnoreCase("Title") && textField.getForeground() == Color.LIGHT_GRAY)
                {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }
            else if (e.getSource().equals(tfDueDate))
            {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().equalsIgnoreCase("dd.mm.yyyy") && textField.getForeground() == Color.LIGHT_GRAY)
                {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }
            else if (e.getSource().equals(taContent))
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

    private JPanel contentPane;

    // Deklarierung der nötigen Elemente
    private TaskTemplate task = null;

    // 4 Beschriftungen
    private JLabel lblTitle   = new JLabel("Title");
    private JLabel lblDueDate = new JLabel("Due date");
    private JLabel lblState   = new JLabel("State");
    private JLabel lblContent = new JLabel("Content");

    // 3 Eingabe Felder
    private JTextField  tfTitle   = new JTextField("Title");
    private JTextField  tfDueDate = new JTextField("dd.mm.yyyy");
    private JScrollPane spContent = new JScrollPane();
    private JTextArea   taContent = new JTextArea("Content");

    // 1 Combobox
    private String[]          states  = new String[]
    { "Todo", "In Work", "Done" };
    private JComboBox<String> cbState = new JComboBox<String>(states);

    // 2 Buttons
    private JButton btnSave  = new JButton("Save");
    private JButton btnAbort = new JButton("Abort");

    public EditTask()
    {
        // Eigenschaften des Fensters festlegen
        setTitle("Edit Task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 253, 324);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Bearbeiten der Eigenschaften der Elemente
        // Titel
        lblTitle.setLocation(10, 11);
        lblTitle.setSize(227, 14);
        tfTitle.setForeground(Color.LIGHT_GRAY);
        tfTitle.setLocation(10, 36);
        tfTitle.setSize(227, 20);
        tfTitle.addFocusListener(fieldListener);

        // Fälligkeitsdatum
        lblDueDate.setLocation(10, 67);
        lblDueDate.setSize(105, 14);
        tfDueDate.setForeground(Color.LIGHT_GRAY);
        tfDueDate.setLocation(10, 92);
        tfDueDate.setSize(105, 20);
        tfDueDate.addFocusListener(fieldListener);

        // Status
        lblState.setLocation(132, 67);
        lblState.setSize(105, 14);
        cbState.setLocation(132, 92);
        cbState.setSize(105, 20);

        // Inhalt
        lblContent.setLocation(10, 123);
        lblContent.setSize(227, 14);
        taContent.setForeground(Color.LIGHT_GRAY);
        taContent.setLineWrap(true);
        taContent.setWrapStyleWord(true);
        taContent.addFocusListener(fieldListener);

        spContent = new JScrollPane(taContent);
        spContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spContent.setBounds(10, 148, 227, 100);

        // Eintragen
        btnSave.setLocation(10, 259);
        btnSave.setSize(105, 30);
        btnSave.addActionListener(buttonListener);

        // Abbrechen
        btnAbort.setLocation(132, 259);
        btnAbort.setSize(105, 30);
        btnAbort.addActionListener(buttonListener);

        // Hinzufügen der Elemente in den Hauptcontainer
        contentPane.add(lblTitle);
        contentPane.add(tfTitle);
        contentPane.add(lblDueDate);
        contentPane.add(tfDueDate);
        contentPane.add(lblState);
        contentPane.add(cbState);
        contentPane.add(lblContent);
        contentPane.add(spContent);
        contentPane.add(btnSave);
        contentPane.add(btnAbort);
    }

    // Deklarierung aller zusätzlich benötigten Methoden

    // Task setzen, den man bearbeiten möchte
    public void setTask(TaskTemplate taskTemplate)
    {
        this.task = taskTemplate;

        tfTitle.setForeground(Color.BLACK);
        tfTitle.setText(taskTemplate.getTitle());

        tfDueDate.setForeground(Color.BLACK);
        tfDueDate.setText(taskTemplate.getDueDate());

        cbState.setSelectedItem(taskTemplate.getStatus());

        taContent.setForeground(Color.BLACK);
        taContent.setText(taskTemplate.getContent());

        System.out.println("Set Task");
    }

    private void resetGUI()
    {
        tfTitle.setForeground(Color.LIGHT_GRAY);
        tfTitle.setText("Title");
        tfDueDate.setForeground(Color.LIGHT_GRAY);
        tfDueDate.setText("dd.mm.yyyy");
        taContent.setForeground(Color.LIGHT_GRAY);
        taContent.setText("Content");
        setVisible(false);
    }
}
