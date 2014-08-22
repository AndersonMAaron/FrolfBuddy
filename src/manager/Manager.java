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
		
		// TODO discs should be a part of the profile definition
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
	
	/*
	 * Saves each profile loaded to JSON
	 */
	public void saveProfiles() {
		for (String username : profiles.keySet()) {
			Profile profile = profiles.get(username);
			try {
				profile.save();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	/*
	 * Returns a list of the loaded courses' names
	 */
	public ArrayList<String> getCourseNames() {
		ArrayList<String> courseNames = new ArrayList<String>();
		for (String name : courses.keySet()) { courseNames.add(name); }
		return courseNames;
	}
	
	/*
	 * Returns a list of the loaded profiles' names
	 */
	public ArrayList<String> getProfileNames() {
		ArrayList<String> profileNames = new ArrayList<String>();
		for (String name : profiles.keySet()) { profileNames.add(name); }
		return profileNames;
	}
}

/**
 * TODO features:
 * --Add a logger
 * --Save each round 
 * --Hole by hole mode
 * --Update disc for hole recommendation
 * --Update disc for bag recommendation
 * --Add profile pictures to profile and JSON
 * --Load Latitude64 discs
 * --Load discraft discs
 * --Load Discmania discs
 * --Create interface for Frolf printable (show()s and getSummary()s)
 * --Update the function comments to include @returns and such
 */
