package main;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import main.gui.Home;
import main.gui.NewProfile;
import manager.Manager;
import frolf.Scorecard;

public class Lawnchair {
	
	public Lawnchair() {}
	
	/**
	 * If no profile have been defined, prompts user if they wish to create one.
	 * Otherwise, starts at the Home screen 
	 * @param args
	 */
	public static void main(String[] args) {
		new Lawnchair();
		
		int reply = JOptionPane.NO_OPTION;
		
		saveScorecard();
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
	
	public static void saveScorecard() { 
		ArrayList<Integer> aaron = new ArrayList<Integer>();
		for (int i = 0; i < 18; i++) { aaron.add(3); }
		ArrayList<Integer> pars = new ArrayList<Integer>();
		for (int i = 0; i < 18; i++) { pars.add(4); }
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		map.put("andersonmaaron", aaron);
		Scorecard scorecard = new Scorecard("Griggs Reservoir Park",
				map, pars );
		
		scorecard.save();
	}
}
