package main;

import javax.swing.JOptionPane;

import main.gui.Home;
import main.gui.NewProfile;
import manager.Manager;

public class Lawnchair {
	/**
	 * If no profile have been defined, prompts user if they wish to create one.
	 * Otherwise, starts at the Home screen 
	 * @param args
	 */
	public static void main(String[] args) {
		new Lawnchair();
		
		int reply = JOptionPane.NO_OPTION;
		if (Manager.getInstance().getProfiles().size() == 2) {
			reply = 
				JOptionPane.showConfirmDialog(
					null, 
					"No saved profiles were detected. Would you like to create one?", 
					"Profile not found", 
					JOptionPane.YES_NO_OPTION
			);
		}
		
		if(reply == JOptionPane.YES_OPTION) {
			java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new NewProfile().setVisible(true);
	            }
	        });
		}
		else {
			java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new Home().setVisible(true);
	            }
	        });
		}
	}
	
	public Lawnchair() {}
}
