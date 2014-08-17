package main;

import main.gui.App;
import manager.Manager;

public class Lawnchair {
	Manager manager;
	
	public static void main(String[] args) {
		new Lawnchair();
		
		if (Manager.getInstance().getProfiles().size() == 0) {
			//TODO Run new profile page
		} else {
			App.main(null);
		}
	}
	
	public Lawnchair() {}
}
