/* 	Name: Tyler Johnson, Justin Dang, Branigan Geoates
	Username: group1
	Problem Set: Final Project
	Due Date: December 9th, 2021
*/

package tools;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageCreator {
    public static void createErrorMessage(String message){
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void createSuccessMessage(String message){
        JOptionPane.showMessageDialog(new JFrame(), message, "Success", JOptionPane.PLAIN_MESSAGE);
    }
}
