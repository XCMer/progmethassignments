/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
                    implements FacePamphletConstants {
    
    private GLabel message;
    /** 
     * Constructor
     * This method takes care of any initialization needed for 
     * the display
     */
    public FacePamphletCanvas() {
        // You fill this in
        message = new GLabel("Click 'Add' to add a new profile.");
        message.setFont(MESSAGE_FONT);
        add(message, (getWidth()-message.getWidth()/2), (getHeight()-BOTTOM_MESSAGE_MARGIN));
    }

    
    /** 
     * This method displays a message string near the bottom of the 
     * canvas.  Every time this method is called, the previously 
     * displayed message (if any) is replaced by the new message text 
     * passed in.
     */
    public void showMessage(String msg) {
        // You fill this in
        message.setLabel(msg);
        message.setLocation((getWidth()-message.getWidth())/2, getHeight());
    }
    
    
    /** 
     * This method displays the given profile on the canvas.  The 
     * canvas is first cleared of all existing items (including 
     * messages displayed near the bottom of the screen) and then the 
     * given profile is displayed.  The profile display includes the 
     * name of the user from the profile, the corresponding image 
     * (or an indication that an image does not exist), the status of
     * the user, and a list of the user's friends in the social network.
     */
    public void displayProfile(FacePamphletProfile profile) {
        // You fill this in
        removeAll();
        add(message);
        
        if (profile == null)
        {
            return;
        }
        
        //Name
        GLabel name = new GLabel(profile.getName());
        name.setColor(Color.BLUE);
        name.setFont(PROFILE_NAME_FONT);
        name.setLocation(LEFT_MARGIN, TOP_MARGIN + name.getAscent());
        add(name);
        
        //Profile Image
        GImage image = profile.getImage();
        double imgy = 0;
        double imgtop = 0;
        if (image == null)
        {
            GRect rect = new GRect(LEFT_MARGIN, name.getY()+IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
            GLabel noimage = new GLabel("No Image");
            noimage.setFont(PROFILE_IMAGE_FONT);
            noimage.setLocation(rect.getX() + (rect.getWidth() - noimage.getWidth())/2,
                    rect.getY() + (rect.getHeight() - noimage.getHeight())/2);
            add(rect);
            add(noimage);
            imgy = rect.getY() + rect.getHeight();
            imgtop = rect.getY();
        }
        else
        {
            image.scale((double)IMAGE_WIDTH/image.getWidth(), (double)IMAGE_HEIGHT/image.getHeight());
            image.setLocation(LEFT_MARGIN, name.getY()+IMAGE_MARGIN);
            imgy = image.getY() + image.getHeight();
            imgtop = image.getY();
            add(image);
        }
        
        //Status
        GLabel status = new GLabel(profile.getStatus());
        status.setFont(PROFILE_STATUS_FONT);
        status.setLocation(LEFT_MARGIN, imgy+STATUS_MARGIN+status.getAscent());
        add(status);
        
        //Friends
        GLabel friendLabel = new GLabel("Friends: ");
        friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
        double x = (getWidth()-friendLabel.getWidth())/2;
        double y = imgtop;
        friendLabel.setLocation(x,y);
        add(friendLabel);
        
        y = y+friendLabel.getAscent();
        Iterator<String> it = profile.getFriends();
        while (it.hasNext())
        {
            GLabel f = new GLabel(it.next());
            f.setFont(PROFILE_FRIEND_FONT);
            f.setLocation(x,y);
            add(f);
            y += f.getAscent();
        }
    }

    
}
