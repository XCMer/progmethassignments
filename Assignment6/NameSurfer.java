/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
    
    private JTextField nameField;
    private JButton graphButton;
    private JButton clearButton;
    
    private NameSurferDataBase database;
    
    private NameSurferGraph graph;
    
    public static void main(String[] args) {
        new NameSurfer().start(args);
    }

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
    public void init() {
        // You fill this in, along with any helper methods //
        add(new JLabel("Name: "), SOUTH);
        
        add(nameField = new JTextField(20), SOUTH);
        nameField.setActionCommand("Graph");
        
        add(graphButton = new JButton("Graph"), SOUTH);
        add(clearButton = new JButton("Clear"), SOUTH);
        
        addActionListeners();
        nameField.addActionListener(this);
        
        database = new NameSurferDataBase("names-data.txt");
        
        graph = new NameSurferGraph();
        add(graph);
        
    }

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
    public void actionPerformed(ActionEvent e) {
        // You fill this in //
        String cmd = e.getActionCommand();
        if (cmd.equals("Graph"))
        {
            NameSurferEntry entry = database.findEntry(nameField.getText());
            if (entry != null)
            {
                graph.addEntry(entry);
            }
        }
        else if (cmd.equals("Clear"))
        {
            graph.clear();
        }
    }
}
