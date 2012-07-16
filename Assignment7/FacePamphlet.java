/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
                    implements FacePamphletConstants {

    /**
     * This method has the responsibility for initializing the 
     * interactors in the application, and taking care of any other 
     * initialization that needs to be performed.
     */
    private JTextField nameField;
    private JTextField statusField;
    private JTextField pictureField;
    private JTextField friendField;
    
    private FacePamphletDatabase db = new FacePamphletDatabase();
    private FacePamphletProfile current = null;
    
    private FacePamphletCanvas canvas = new FacePamphletCanvas();
    
    private static final String PATH = "F:/Real Courses/Stanford Programming/Programming Methodology/ProgrammingMethodology/materials/icspmcs106a/Assignment7/images/";
    
    public static void main(String[] args)
    {
        new FacePamphlet().start(args);
    }
    
    public void init() {
        // You fill this in
        add(new JLabel("Name: "), NORTH);
        add(nameField = new JTextField(TEXT_FIELD_SIZE), NORTH);
        add(new JButton("Add"), NORTH);
        add(new JButton("Delete"), NORTH);
        add(new JButton("Lookup"), NORTH);
        
        add(statusField = new JTextField(TEXT_FIELD_SIZE), WEST);
        add(new JButton("Change Status"), WEST);
        statusField.setActionCommand("Change Status");
        statusField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
        
        add(pictureField = new JTextField(TEXT_FIELD_SIZE), WEST);
        add(new JButton("Change Picture"), WEST);
        pictureField.setActionCommand("Change Picture");
        pictureField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
        
        add(friendField = new JTextField(TEXT_FIELD_SIZE), WEST);
        add(new JButton("Add Friend"), WEST);
        friendField.setActionCommand("Add Friend");
        friendField.addActionListener(this);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
        
        addActionListeners();
        
        add(canvas);
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
        // You fill this in as well as add any additional methods
        String cmd = e.getActionCommand();
        if (cmd.equals("Add"))
        {
            String name = nameField.getText();
            if (!name.isEmpty())
            {
                if (db.containsProfile(name))
                {
                    current = db.getProfile(name);
                    canvas.showMessage("Profile " + name + " already exists.");
                    canvas.displayProfile(current);
                }
                else
                {
                    FacePamphletProfile profile = new FacePamphletProfile(name);
                    db.addProfile(profile);
                    current = profile;
                    canvas.showMessage("Profile " + name + " added.");
                    canvas.displayProfile(current);
                    
                }
            }
        }
        else if (cmd.equals("Delete"))
        {
            String name = nameField.getText();
            if (!name.isEmpty())
            {
                if (db.containsProfile(name))
                {
                    db.deleteProfile(name);
                    canvas.showMessage("Profile " + name + " deleted.");
                }
                else
                {
                    canvas.showMessage("Profile " + name + " not found for deletion.");
                }
                current = null;
                canvas.displayProfile(current);
            }
        }
        else if (cmd.equals("Lookup"))
        {
            String name = nameField.getText();
            if (!name.isEmpty())
            {
                if (db.containsProfile(name))
                {
                    current = db.getProfile(name); 
                }
                else
                {
                    canvas.showMessage("Profile " + name + " not found for lookup.");
                    current = null;
                }
            }
            canvas.displayProfile(current);
        }
        else if (cmd.equals("Change Status"))
        {
            String status = statusField.getText();
            if (!status.isEmpty())
            {
                if (current != null)
                {
                    current.setStatus(status);  
                    canvas.showMessage("Updated status.");
                    canvas.displayProfile(current);
                }
                else
                {
                    canvas.showMessage("No profile selected.");
                }
            }
        }
        else if (cmd.equals("Change Picture"))
        {
            String filename = pictureField.getText();
            if (!filename.isEmpty())
            {
                if (current != null)
                {
                    GImage img = null;
                    try
                    {
                        img = new GImage(PATH + filename);
                        current.setImage(img);
                        canvas.showMessage("Image updated.");
                        canvas.displayProfile(current);
                    } catch (ErrorException ex) {
                        canvas.showMessage("Could not open image file.");
                    }
                }
                else
                {
                    canvas.showMessage("No profile selected.");
                }
            }
        }
        else if (cmd.equals("Add Friend"))
        {
            String name = friendField.getText();
            if (!name.isEmpty())
            {
                if (!db.containsProfile(name))
                {
                    canvas.showMessage("Friend doesn't exist.");
                    return;
                }
                if (current == null)
                {
                    canvas.showMessage("No profile selected.");
                    return;
                }

                if (current.addFriend(name))
                {
                    db.getProfile(name).addFriend(current.getName());
                    canvas.showMessage("Friend was added.");
                    canvas.displayProfile(current);
                }
                else
                {
                    canvas.showMessage("Friend already exists.");
                }
            }
        }
    }

}
