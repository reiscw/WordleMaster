import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class WordleMasterGUI {
	
	public static void main(String[] args) {
        JFrame frame = new JFrame ("WordleMaster Version 3.0 by Christopher Reis");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new WordleMasterPanel());
        frame.pack();
        frame.setVisible(true);
	}
	
}
