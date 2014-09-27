package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import player.Profile;
import bags.Bag;
import discs.Disc;
import discs.DiscType;
import discs.Manufacturer;
import discs.PlasticType;
import discs.Stability;
import frolf.Course;
import frolf.Hole;
import frolf.HoleType;
import frolf.Scorecard;

public class FrolfUtil {
	final static String COURSE_ROOT = "courses/"; 
	public final static int SECOND_PUTTER_THRESHOLD = 15; // In number of discs
	
	public final static int STABILITY_MAX_PCT = 45;
	public final static int ACCEPT_DIST_DELTA = 10;
	public final static int PUTT_MAX_DIST = 150;
	public final static int MID_MAX_DIST = 250;
	public final static int FAIR_MAX_DIST = 320;
	
	private final static Logger LOGGER = Logger.getLogger( FrolfUtil.class.getName() );
	
	/*
	 * Forms and returns a Disc data model from a specified JSON   
	 */
	public static Disc readDiscFromFile(String filename) throws org.json.simple.parser.ParseException {
		LOGGER.log(Level.INFO, "Reading disc JSON from file '" + filename + "'");
		
		JSONObject json = new JSONObject();
		try {
			json = (JSONObject) new JSONParser().parse(new FileReader(filename));
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		String manufacturerName = (String) json.get("manufacturer");
		
		DiscType discType = null;
		Manufacturer manufacturer = null;
		switch(((String) json.get("type")).toLowerCase()) {
			case "putter/approach":
			case "putter":
			case "approach":
			case "putter approach":
			case "putter-approach":
			case "approach-putter":
			case "approach/putter":
			case "approach putter":
			case "putt & approach disc":
				discType = DiscType.PUTTAPPROACH;
				break;
			case "midrange":
			case "mid-range":
			case "mid range":
			case "midrange disc":
				discType = DiscType.MIDRANGE;
				break;
			case "distance driver":
			case "distance-driver":
			case "distancedriver":
				discType = DiscType.DISTANCEDRIVER;
				break;
			case "fairway driver":
			case "fairway-driver":
			case "fairwaydriver":
				discType = DiscType.FAIRWAYDRIVER;
				break;
			default:
				discType = DiscType.UNKNOWN;
		}
		
		Stability stability = null;
		switch(((String) json.get("stability")).toLowerCase()) {
			case "stable":
				stability = Stability.STABLE;
				break;
			case "overstable":
				stability = Stability.OVERSTABLE;
				break;
			case "understable":
				stability = Stability.UNDERSTABLE;
				break;
		}
		
		Disc disc = null;
		switch (manufacturerName.toLowerCase()) {
			case "innova":
				manufacturer = Manufacturer.INNOVA;
				PlasticType plasticType = null;
				switch(((String) json.get("plastic")).toLowerCase()) {
					case "dx":
						plasticType = PlasticType.DX;
						break;
					case "champion":
						plasticType = PlasticType.CHAMPION;
						break;
					case "star": 
						plasticType = PlasticType.STAR;
						break;
					case "pro": 
						plasticType = PlasticType.PRO;
						break;
					default:
						plasticType = PlasticType.DX;
						break;
				}
				
				int speed;
				String speedS = (String) json.get("speed");
				if (speedS.compareTo("O") == 0 || speedS.compareTo("-O") == 0 || speedS.compareTo("") == 0){
					speed = 0;
				} else {
					speed = Integer.parseInt(speedS);
				}				
				
				int glide;
				String glideS = (String) json.get("glide");
				if (glideS.compareTo("O") == 0 || glideS.compareTo("-O") == 0 || glideS.compareTo("") == 0){
					glide = 0;
				} else {
					glide = Integer.parseInt(glideS);
				}
				
				int turn;
				String turnS = (String) json.get("turn");
				if (turnS.compareTo("O") == 0 || turnS.compareTo("-O") == 0 || turnS.compareTo("") == 0){
					turn = 0;
				} else {
					turn = Integer.parseInt(turnS);
				}
				
				int fade;
				String fadeS = (String) json.get("fade");
				if (fadeS.compareTo("O") == 0 || fadeS.compareTo("-O") == 0 || fadeS.compareTo("") == 0){
					fade = 0;
				} else {
					fade = Integer.parseInt(fadeS);
				}
				
				disc = new Disc(
					(String) json.get("name"),
					manufacturer,
					discType,
					stability,
					speed,
					glide,
					turn,
					fade
				);
				break;
		}
		
		LOGGER.log(Level.INFO, "Created disc '" + disc.getName() + "'");
		return disc;
	}
	
	/*
	 * Forms and returns a Scorecard data model from a specified JSON   
	 */
	public static Scorecard readRoundFromFile(File filename) {
		JSONObject json = new JSONObject();
		try {
			LOGGER.log(Level.INFO, "Reading round JSON from file '" + filename + "'");
			json = (JSONObject) new JSONParser().parse(new FileReader(filename));
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); 
		} catch (ParseException e) { e.printStackTrace(); }
		
		Set<String> players = json.keySet();
		players.remove("pars"); players.remove("course");
		HashMap<String, ArrayList<Integer>> scores = new HashMap<String, ArrayList<Integer>>();
		for (String player : players) { 
			LOGGER.log(Level.INFO, "Reading scores for player '" + player + "'");
			scores.put(player, (ArrayList<Integer>)json.get(player));
		}
		
		LOGGER.log(Level.INFO, "Finished reading disc JSON '" + filename + "'");
		return new Scorecard(
			(String)json.get("course"),
			scores,
			(ArrayList<Integer>)json.get("pars")
		);
	}
    
	/*
	 * Forms and returns Profile data models from a specified directory   
	 */
    public static HashMap<String, Profile> loadProfiles() {
		LOGGER.log(Level.INFO, "Loading player profiles..");

        HashMap<String, Profile> profiles = new HashMap<String, Profile>();
        File[] proFiles = new File("profiles").listFiles();
        
        for (File file : proFiles) {
            try {
            	if (!file.getCanonicalPath().endsWith(".json")) { continue; }
                Profile profile = new Profile((JSONObject) new JSONParser().parse(new FileReader(file)));
    			LOGGER.log(Level.INFO, "Loaded player '" + profile.getUsername() + "'");
                profiles.put(profile.getUsername(), profile);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            } catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
        }
        
		LOGGER.log(Level.INFO, "Finished loading profiles");
        return profiles;
    }
	
	public static Bag loadDiscs() throws IOException {
		LOGGER.log(Level.INFO, "Loading disc catalog..");

		Bag allDisks = new Bag();
		
		File[] discFiles = new File("discs/innova").listFiles();
		for (File file : discFiles) {
			Disc disc = null;
			try {
				disc = readDiscFromFile("discs/innova/" + file.getName());
			} catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
			
			if (disc != null) { 
				allDisks.addDisc(disc);
				LOGGER.log(Level.INFO, "Loaded disc '" + disc.getName() + "'");
			}
		}
		
		LOGGER.log(Level.INFO, "Finished loading discs");
		return allDisks;
	}
	
	public static final HashMap<String, Course> loadCourseCatalog() throws IOException {
		LOGGER.log(Level.INFO, "Loading course catalog..");
		
		HashMap<String, Course> courses = new HashMap<String, Course>();
		JSONParser parser = new JSONParser();

		File[] files = new File(COURSE_ROOT).listFiles();
		for (File file : files) {
	        if (file.isDirectory()) {
        		Course course = readCourse(new File(file + "/course.json"));

	        	for (File definitionFile : file.listFiles()){
	        		
	        		try { // Course definition
						if (!definitionFile.getCanonicalPath().contains("course.json")) {
							LOGGER.log(Level.INFO, "Adding hole to course '" + course.getName() + "'");
							course.addHole(readHole(definitionFile));
						} else { // Hole definition
													}
					} catch (IOException e) {e.printStackTrace();}
	        	}
	        	
    			courses.put(course.getName(), course);
    			LOGGER.log(Level.INFO, "Loaded course '" + course.getName() + "'");
	        } else {/* Do nothing */}
	    }
		LOGGER.log(Level.INFO, "Loaded course catalog");
		return courses;
	}
	
	public static final Course readCourse(File definitionFile){
		LOGGER.log(Level.INFO, "Reading course from file '" + definitionFile + "'");
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(definitionFile));
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		
		Course course = new Course(
			(String)jsonObject.get("name"),
			(String)jsonObject.get("address"),
			(int)((long)jsonObject.get("bestScore")),
			(int)((long)jsonObject.get("averageScore")),
			(int)((long)jsonObject.get("worstScore")),
			(int)((long)jsonObject.get("timesPlayed")),
			new ArrayList<Hole>(),
			(String)jsonObject.get("userWithBestScore"),
			(String)jsonObject.get("description")
		);
		LOGGER.log(Level.INFO, "Created course '" + course.getName() + "'");
		
		return course;
	}
	
	public static final Hole readHole(File definitionFile) {
		LOGGER.log(Level.INFO, "Reading hole JSON from file '" + definitionFile + "'");
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(definitionFile));
		} catch (IOException e) { e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }

		HoleType holeType = HoleType.STRAIGHT;
		switch( ((String)(jsonObject.get("holeType"))).toLowerCase() ){
			case "hard left":
			case "hard-left":
			case "hardleft":
				holeType = HoleType.HARD_LEFT;
				break;
			case "slight left":
			case "slight-left":
			case "slightleft":
				holeType = HoleType.SLIGHT_LEFT;
				break;
			case "straight":
				holeType = HoleType.STRAIGHT;
				break;
			case "slight right":
			case "slight-right":
			case "slightright":
				holeType = HoleType.SLIGHT_RIGHT;
				break;
			case "hard right":
			case "hard-right":
			case "hardright":
				holeType = HoleType.HARD_RIGHT;
				break;
			default: 
				System.out.println("Unknown hole type");
		}
		
		Hole hole = new Hole(
			(int)((long)jsonObject.get("holeNumber")),
			holeType,
			(int)((long)jsonObject.get("distance")),
			(int)((long)jsonObject.get("par")),
			(int)((long)jsonObject.get("timesPlayed")),
			(int)((long)jsonObject.get("holesInOne")),
			(int)((long)jsonObject.get("bestScore")),
			(int)((long)jsonObject.get("averageScore")),
			(int)((long)jsonObject.get("worstScore")),
			(String)jsonObject.get("description")
		);
		
		LOGGER.log(Level.INFO, "Created hole '" + hole.getHoleNumber() + "'");
		return hole;
	}
	
	public static ArrayList<String> readDiscsForUser(String username) {
		LOGGER.log(Level.INFO, "Loading discs for user '" + username + "'");
		ArrayList<String> discNames = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("bags/" + username + "/bag.txt"));
			while(reader.ready()) {
				discNames.add(reader.readLine());
			}
			reader.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
		
		LOGGER.log(Level.INFO, "Loaded " + discNames.size() + " discs for user '" + username + "'");
		return discNames;
	}
	
	public static Disc recommendDiscForBag(Bag bag) {
		LOGGER.log(Level.INFO, "Selecting criteria for disc to complement bag. Here be dragons..");

		Map<String, Object> criteria = new HashMap<String, Object>();
		DiscType suggestDiscType = bag.getLackingDiscType();

		if (suggestDiscType != DiscType.UNKNOWN) {
			criteria.put("discType", suggestDiscType);
			criteria.put("stability", bag.getLackingStabilityForType(suggestDiscType));
		} else { // They aren't lacking a disc type
			//TODO will this even get called? At least one disc type will be selected
			Stability suggestStability = bag.getLackingStabilityForType(DiscType.MIDRANGE);
			
			if (suggestStability == Stability.UNKNOWN) { // Then check fairway drivers
				suggestStability = bag.getLackingStabilityForType(DiscType.FAIRWAYDRIVER);
				
				if (suggestStability == Stability.UNKNOWN) { // Then check distance drivers
					suggestStability = bag.getLackingStabilityForType(DiscType.DISTANCEDRIVER);
					
					if (suggestStability != Stability.UNKNOWN) {
						criteria.put("discType", DiscType.DISTANCEDRIVER);
						criteria.put("stability", suggestStability);
					} // else They have acceptable disc type and stability ratios
				} else {
					criteria.put("discType", DiscType.FAIRWAYDRIVER);
					criteria.put("stability", suggestStability);
				}
			} else {
				criteria.put("discType", DiscType.MIDRANGE);
				criteria.put("stability", suggestStability);
			}
			System.out.println("REC stability: " + suggestStability);
		}
		
		LOGGER.log(Level.INFO, "Criteria for disc recommendation: " + criteria.toString());
		return getDiscByCriteria(criteria);
	}

	public static Disc getDiscByCriteria(Map<String, Object> criteria) {
		LOGGER.log(Level.INFO, "Selecting a disc with criteria: '" + criteria.toString() + "'");
		
		// Arbitrary outside of not being a possible disc definition value
		final int INT_NOT_DEFINED = 100; 
		
		Manufacturer manufacturer = (criteria.containsKey("manufacturer")) ? 
				(Manufacturer) criteria.get("manufacturer") : null;
				
		DiscType discType = (criteria.containsKey("discType")) ? 
				(DiscType) criteria.get("discType") : null;
		
		Stability stability = (criteria.containsKey("stability")) ? 
				(Stability) criteria.get("stability") : null;
		
		int speed = (criteria.containsKey("speed")) ? 
				(int) criteria.get("speed") : INT_NOT_DEFINED;
				
		int glide = (criteria.containsKey("glide")) ? 
				(int) criteria.get("glide") : INT_NOT_DEFINED;
				
		int turn = (criteria.containsKey("turn")) ? 
				(int) criteria.get("turn") : INT_NOT_DEFINED;
				
		int fade = (criteria.containsKey("fade")) ? 
				(int) criteria.get("fade") : INT_NOT_DEFINED;
		
		Bag excludedDiscs = (criteria.containsKey("excluding")) ?
				(Bag) criteria.get("excluding") : new Bag();
				
		// Method chain on Bag containing disc catalog if the
		// criteria is set. The resulting bag will meet all criteria.
		Bag filteredDiscs = new Bag();
		try {
			filteredDiscs = FrolfUtil.loadDiscs().minus(excludedDiscs);
		} catch (IOException e) { e.printStackTrace(); }


		if (manufacturer != null) {
			LOGGER.log(Level.INFO, "Filtering to discs only manufactured by '" + manufacturer + "'");
			filteredDiscs = filteredDiscs.getDiscsFromManufacturer(manufacturer);
		}
		
		if (discType != null) { 
			LOGGER.log(Level.INFO, "Filtering to discs of type '" + discType + "'");
			filteredDiscs = filteredDiscs.getDiscsByType(discType); 
		}
		
		if (stability != null) {
			LOGGER.log(Level.INFO, "Filtering to discs with stability '" + stability + "'");
			filteredDiscs = filteredDiscs.getDiscsWithStability(stability);
		}
		
		if (speed != INT_NOT_DEFINED) {
			LOGGER.log(Level.INFO, "Filtering to discs with speed '" + speed + "'");
			filteredDiscs = filteredDiscs.getDiscsWithSpeed(speed);
		}
		
		if (glide != INT_NOT_DEFINED) {
			LOGGER.log(Level.INFO, "Filtering to discs with glide '" + glide + "'");
			filteredDiscs = filteredDiscs.getDiscsWithGlide(glide);
		}
		
		if (turn != INT_NOT_DEFINED) {
			LOGGER.log(Level.INFO, "Filtering to discs with turn '" + turn + "'");
			filteredDiscs = filteredDiscs.getDiscsWithTurn(turn);
		}
		
		if (fade != INT_NOT_DEFINED) {
			LOGGER.log(Level.INFO, "Filtering to discs with fade '" + fade + "'");
			filteredDiscs = filteredDiscs.getDiscsWithFade(fade);
		}
		
		// Any discs reamining in 'filteredDiscs' match the criteria
		Random random = new Random();
		int numDiscs = filteredDiscs.size();
		LOGGER.log(Level.INFO, "" + numDiscs + " meet the criteria. Selecting one randomly");
		return filteredDiscs.getDiscs().get(random.nextInt(numDiscs));
	}
	
	/*
	 * Recommends a disc for a specified hole based on the available discs
	 * 
	 * TODO make a "best guess" if criteria aren't met.
	 */
	public static Disc recommendDiscForHole(Bag bag, Hole hole) {
		LOGGER.log(Level.INFO, "Populating criteria for hole '" + hole.getHoleNumber() + "' to recommend a disc.");
		
		Map<String, Object> criteria = new HashMap<String, Object>();
		int distance = hole.getDistance();
		
		if (distance < PUTT_MAX_DIST) { criteria.put("discType", DiscType.PUTTAPPROACH); }
		else if (distance < MID_MAX_DIST) { criteria.put("discType", DiscType.MIDRANGE); }
		else if (distance < FAIR_MAX_DIST) { criteria.put("discType", DiscType.FAIRWAYDRIVER); }
		else { criteria.put("discType", DiscType.DISTANCEDRIVER); }
		
		switch(hole.getHoleType()) {
			case STRAIGHT:
				criteria.put("stability", Stability.STABLE);
				break;
			case SLIGHT_LEFT:
			// TODO  Use turn/fade ratings based on hard vs slight curves
			case HARD_LEFT:
				criteria.put("stability", Stability.OVERSTABLE);
				break;
			case SLIGHT_RIGHT:
			case HARD_RIGHT:
				criteria.put("stability", Stability.UNDERSTABLE);
				break;
		}

		LOGGER.log(Level.INFO, "Criteria for disc for hole '" + hole.getHoleNumber() + "': " + criteria.toString());
		return getDiscByCriteria(criteria);
	}
}
