package tank;

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tank.gui.TankPanel;
import tank.listener.DirectionListener;


public class Main{
	
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Ì¹¿Ë´óÕ½");
		TankPanel panel = new TankPanel();
		panel.setFrame(frame);
		Container contentPane = frame.getContentPane();	
		contentPane.add(panel);
		
		frame.setLocation(50, 100);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// add listener
		
		frame.addKeyListener(new DirectionListener(panel));
		
		frame.setFocusable(true); //this line is necessary
		
	}


}

