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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

public class FrolfUtil {
	final static String COURSE_ROOT = "courses/"; 
	public final static int SECOND_PUTTER_THRESHOLD = 15; // In number of discs
	
	public final static int STABILITY_MAX_PCT = 45;
	public final static int ACCEPT_DIST_DELTA = 10;
	
	public static Disc readDiscFromFile(String filename) throws org.json.simple.parser.ParseException {
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
		
		return disc;
	}
        
    public static HashMap<String, Profile> loadProfiles() {
        HashMap<String, Profile> profiles = new HashMap<String, Profile>();
        File[] proFiles = new File("profiles").listFiles();
        
        for (File file : proFiles) {
            try {
            	if (!file.getCanonicalPath().endsWith(".json")) { continue; }
                Profile profile = new Profile((JSONObject) new JSONParser().parse(new FileReader(file)));
                profiles.put(profile.getUsername(), profile);
            } catch (IOException ex) {
                Logger.getLogger(FrolfUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
        }
        return profiles;
    }
	
	public static Bag loadDiscs() throws IOException {
		Bag allDisks = new Bag();
		
		File[] discFiles = new File("discs/innova").listFiles();
		for (File file : discFiles) {
			Disc disc = null;
			try {
				disc = readDiscFromFile("discs/innova/" + file.getName());
			} catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
			if (disc != null) { allDisks.addDisc(disc); }
		}
		
		return allDisks;
	}
	
	public static final HashMap<String, Course> loadCourseCatalog() throws IOException {
		HashMap<String, Course> courses = new HashMap<String, Course>();
		
		JSONParser parser = new JSONParser();

		File[] files = new File(COURSE_ROOT).listFiles();
		for (File file : files) {
	        if (file.isDirectory()) {
        		Course course = readCourse(new File(file + "/course.json"));

	        	for (File definitionFile : file.listFiles()){
	        		
	        		try { // Course definition
						if (!definitionFile.getCanonicalPath().contains("course.json")) {
							course.addHole(readHole(definitionFile));
						} else { // Hole definition
													}
					} catch (IOException e) {e.printStackTrace();}
	        	}

    			courses.put(course.getName(), course);
	        } else {/* Do nothing */}
	    }
		
		return courses;
	}
	
	public static final Course readCourse(File definitionFile){
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(definitionFile));
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
		
		return new Course(
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
	}
	
	public static final Hole readHole(File definitionFile) {
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
		
		return new Hole(
			(int)((long)jsonObject.get("holeNumber")),
			holeType,
			(int)((long)jsonObject.get("distance")),
			(int)((long)jsonObject.get("par")),
			(int)((long)jsonObject.get("timesPlayed")),
			(int)((long)jsonObject.get("holesInOne")),
			(int)((long)jsonObject.get("bestScore")),
			(int)((long)jsonObject.get("averageScore")),
			(int)((long)jsonObject.get("worstScore")),
			(String)jsonObject.get("userWithBestScore"),
			(String)jsonObject.get("description")
		);
	}
	
	public static ArrayList<String> readDiscsForUser(String username) {
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
		
		return discNames;
	}
	
	public static Disc recommendDiscForBag(Bag bag) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		DiscType suggestDiscType = bag.getLackingDiscType();

		if (suggestDiscType != DiscType.UNKNOWN) {
			criteria.put("discType", suggestDiscType);
			criteria.put("stability", bag.getLackingStabilityForType(suggestDiscType));
		} else { // They aren't lacking a disc type 
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
		
		System.out.println("REC discType: " + suggestDiscType.toString());
		return getDiscByCriteria(criteria);
	}

	public static Disc getDiscByCriteria(Map<String, Object> criteria) {
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
			filteredDiscs = filteredDiscs.getDiscsFromManufacturer(manufacturer);
		}
		
		if (discType != null) { 
			filteredDiscs = filteredDiscs.getDiscsByType(discType); 
		}
		
		if (stability != null) {
			filteredDiscs = filteredDiscs.getDiscsWithStability(stability);
		}
		
		if (speed != INT_NOT_DEFINED) {
			filteredDiscs = filteredDiscs.getDiscsWithSpeed(speed);
		}
		
		if (glide != INT_NOT_DEFINED) {
			filteredDiscs = filteredDiscs.getDiscsWithGlide(glide);
		}
		
		if (turn != INT_NOT_DEFINED) {
			filteredDiscs = filteredDiscs.getDiscsWithTurn(turn);
		}
		
		if (fade != INT_NOT_DEFINED) {
			filteredDiscs = filteredDiscs.getDiscsWithFade(fade);
		}
		
		// Any discs reamining in 'filteredDiscs' match the criteria
		Random random = new Random();
		int numDiscs = filteredDiscs.size();
		System.out.println("numDiscs: " + numDiscs);
		return filteredDiscs.getDiscs().get(random.nextInt(numDiscs));
	}
}
