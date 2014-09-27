package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import main.gui.Home;
import main.gui.NewProfile;
import manager.Manager;
import util.FrolfUtil;
import frolf.Scorecard;

public class Lawnchair {
	private final static Logger LOGGER = Logger.getLogger( FrolfUtil.class.getName() );

	public Lawnchair() {}
	
	/**
	 * If no profile have been defined, prompts user if they wish to create one.
	 * Otherwise, starts at the Home screen 
	 * @param args
	 */
	public static void main(String[] args) {
		new Lawnchair();
		
		LOGGER.log(Level.INFO, "INIT. Sitting in Lawnchair.");
		int reply = JOptionPane.NO_OPTION;
		
		saveScorecard();
		if (Manager.getInstance().getProfiles().size() == 2) {
			LOGGER.log(Level.INFO, "No profiles found. Prompting user to create one.");
			reply = 
				JOptionPane.showConfirmDialog(
					null, 
					"No saved profiles were detected. Would you like to create one?", 
					"Profile not found", 
					JOptionPane.YES_NO_OPTION
			);
		}
		
		if(reply == JOptionPane.YES_OPTION) {
			LOGGER.log(Level.INFO, "INIT. User selected yes. Opening new profile page.");
			java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new NewProfile().setVisible(true);
	            }
	        });
		}
		else {
			LOGGER.log(Level.INFO, "INIT. Starting app home.");
			java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new Home().setVisible(true);
	            }
	        });
		}
	}
	
	//TODO why dis here?
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
