package manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import player.Profile;
import util.FrolfUtil;
import bags.Bag;
import frolf.Course;

public class Manager {
	private static Manager instance = null;     // Singleton instance
	private HashMap<String, Profile> profiles;	// Player profiles
	private HashMap<String, Course> courses;	// Courses
	private Bag discs;							// Disc catalog
	
	/*
	 * Please don't look at my privates.
	 * Singelton constructor
	 */
	Manager() {
        profiles = FrolfUtil.loadProfiles();
                
		try {
			courses = FrolfUtil.loadCourseCatalog();
			discs = FrolfUtil.loadDiscs();
		} catch (IOException e) { e.printStackTrace(); }
		
		for (String username : profiles.keySet()) { 
			ArrayList<String> discNames = FrolfUtil.readDiscsForUser(username);
			for (String discName : discNames) {
				profiles.get(username).addDiscToBag(discs.getDisc(discName));
			}
		}
	}
	
	/* Singel-ton of fun! */
	public static Manager getInstance() {
		if (instance == null) {
			instance = new Manager();
		}
		
		return instance;
	}
	
	/* GETTERS */
	public HashMap<String, Profile> getProfiles() { return profiles; }
	public HashMap<String, Course> getCourses() { return courses; }
	public Bag getDiscs() { return discs; }
}
