package manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import player.Profile;
import util.FrolfUtil;
import bags.Bag;
import discs.Disc;
import frolf.Course;

public class Manager {
	private static Manager instance = null;     // Singleton instance
	private HashMap<String, Profile> profiles;	// Player profiles
	private Profile currentProfile;
	private HashMap<String, Course> courses;	// Courses
	private Bag discs;							// Disc catalog
	
	private final static Logger LOGGER = Logger.getLogger( Manager.class.getName() );

	/*
	 * Please don't look at my privates.
	 * Singelton constructor
	 */
	Manager() {
		LOGGER.log(Level.INFO, "Loading profiles.");
        profiles = FrolfUtil.loadProfiles();
        currentProfile = null;
        		
		try {
			LOGGER.log(Level.INFO, "Loading courses.");
			courses = FrolfUtil.loadCourseCatalog();

			LOGGER.log(Level.INFO, "Loading discs.");
			discs = FrolfUtil.loadDiscs();
		} catch (IOException e) { e.printStackTrace(); }
		
		// TODO discs should be a part of the profile definition
		LOGGER.log(Level.INFO, "Loading player bags.");
		for (String username : profiles.keySet()) { 
			LOGGER.log(Level.INFO, "Loading bag for " + username);

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
	
	/** GETTERS **/
	public HashMap<String, Profile> getProfiles() { return profiles; }
	public Profile getCurrentProfile() { return currentProfile; } 
	public HashMap<String, Course> getCourses() { return courses; }
	public Bag getDiscs() { return discs; }
	
	/** SETTERS **/
	public void setCurrentProfile(String username) {
		LOGGER.log(Level.INFO, "Setting current profile to: " + username);

		if (!profiles.containsKey(username)) {
			System.out.println("Tried to set current profile to one that doesn't exist.");
			return;
		}
		currentProfile = profiles.get(username);
	}
	
	/*
	 * Saves each profile loaded to JSON
	 */
	public void saveProfiles() {
		LOGGER.log(Level.INFO, "Saving all profiles to JSON.");

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
	
	public ArrayList<String> getDiscNames() {
		ArrayList<String> discNames = new ArrayList<String>();
		for (Disc disc : discs.getDiscs()) {
			discNames.add(disc.getName());
		}
		return discNames;
	}
}

/**
 * TODO features:
 * --Add a logger
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
