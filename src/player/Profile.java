package player;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import bags.Bag;
import discs.Disc;
import frolf.Scorecard.ScorecardSummary;

public class Profile {
	protected String username;
	protected String displayName;
	protected Date startDate;
	protected boolean isRightHanded;
	protected String favoriteDiscName;
	protected String favoriteCourseName;
	protected int gamesPlayed;
	protected int holesInOne;
	protected int albatrosses;
	protected int eagles;
	protected int birdies;
    protected int pars;
	protected Bag discs;
	protected float averageOverUnder;
	// Hidden from user
	private int lifetimeOverUnder;
	private int bogeys;
	private int doubleBogeys;
	private int tripleBogeys;
	private int worstHole;
	private int lifetimeThrows;
	
	public String getProfileSummary() {
	    String rlHanded = isRightHanded ? "Right handed" : "Left handed";
	    String summary = 
	        "*************************\n" +
	        "****  Frolf Profile  ****\n" +
	        "*************************\n" +
	        "* Username: " + username + "\n" +
	        "* Display name: " + displayName + "\n" +
	        "* Start date: " + startDate.toString() + "\n" +
	        "* R/L handed: " + rlHanded + "\n" +
	        "* Favorite disc: " + favoriteDiscName + "\n" +
	        "* Favorite course: " + favoriteCourseName + "\n" +
	        "* Average Over/under: " + averageOverUnder + "\n" +
	        "* Games played: " + gamesPlayed + "\n" +
	        "* Lifetime score: " + lifetimeThrows + "\n" +
	        "* Lifetime Over/under: " + lifetimeOverUnder + "\n" +
	        "* Holes in one: " + holesInOne + "\n" +
	        "* Albatrosses: " + albatrosses + "\n" +
	        "* Eagles: " + eagles + "\n" +
	        "* Birdies: " + birdies + "\n" +
	        "* Pars: " + pars + "\n" + 
	        "* Bogeys: " + bogeys + "\n" +
	        "* Double bogeys: " + doubleBogeys + "\n" + 
	        "* Triple bogeys: " + tripleBogeys + "\n" +
	        "* Discs: " + discs.show() + "\n" +
	        "*************************";
	    
	    return summary;
	}
	
	public Profile(String username, String displayName, Date startDate,
			boolean isRightHanded, String favoriteDiscName, String favoriteCourseName,
			int gamesPlayed, int holesInOne, int albatrosses, int eagles, int birdies,
            int pars, 
			int lifetimeScore, int bogeys, int doubleBogeys, int tripleBogeys,
			int worstHole, int lifetimeThrows){
		this.username = username;
		this.displayName = displayName;
		this.startDate = startDate;
		this.isRightHanded = isRightHanded;
		this.favoriteDiscName = favoriteDiscName;
		this.favoriteCourseName = favoriteCourseName;
		this.gamesPlayed = gamesPlayed;
		this.holesInOne = holesInOne;
		this.albatrosses = albatrosses;
		this.eagles = eagles;
		this.birdies = birdies;
        this.pars = pars;
		this.discs = new Bag();
		this.lifetimeOverUnder = lifetimeScore;
		this.bogeys = bogeys;
		this.doubleBogeys = doubleBogeys;
		this.tripleBogeys = tripleBogeys;
		this.worstHole = worstHole;
		this.lifetimeThrows = lifetimeThrows;
		this.averageOverUnder = lifetimeOverUnder / gamesPlayed;
	}
	
	public Profile(JSONObject json) {
	    this.username = (String)json.get("username");
	    this.displayName = (String)json.get("displayName");
	    this.startDate = new Date(
	            (int)((long)json.get("startYear")),
	            (int)((long)json.get("startMonth")),
	            (int)((long)json.get("startDay"))
	    );
	    this.isRightHanded = (boolean)json.get("isRightHanded");
	    this.favoriteDiscName = (String)json.get("favoriteDiscName");
	    this.favoriteCourseName = (String)json.get("favoriteCourseName");
	    this.gamesPlayed = (int)((long)json.get("gamesPlayed"));
	    this.holesInOne = (int)((long)json.get("holesInOne"));
	    this.albatrosses = (int)((long)json.get("albatrosses"));
	    this.eagles = (int)((long)json.get("eagles"));
	    this.birdies = (int)((long)json.get("birdies"));
	    this.pars = (int)((long)json.get("pars"));
	    this.bogeys = (int)((long)json.get("bogeys"));
	    this.doubleBogeys = (int)((long)json.get("doubleBogeys"));
	    this.tripleBogeys = (int)((long)json.get("tripleBogeys"));
	    this.worstHole = (int)((long)json.get("worstHole"));
	    this.lifetimeThrows = (int)((long)json.get("lifetimeThrows"));
	    this.lifetimeOverUnder = (int)((long)json.get("lifetimeOverUnder"));
	    this.discs = new Bag();
	    
	    if (gamesPlayed == 0) { averageOverUnder = 0; }
	    else { this.averageOverUnder = lifetimeOverUnder / gamesPlayed; }
	}

    public void save() throws IOException {
        JSONObject json = new JSONObject();
        json.put("startYear", startDate.getYear());
        json.put("startMonth", startDate.getMonth());
        json.put("startDay", startDate.getDay());
        
        json.put("username", username);
        json.put("displayName", displayName);
        json.put("isRightHanded", isRightHanded);
        json.put("favoriteDiscName", favoriteDiscName);
        json.put("favoriteCourseName", favoriteCourseName);
        json.put("gamesPlayed", gamesPlayed);
        json.put("holesInOne", holesInOne);
        json.put("albatrosses", albatrosses);
        json.put("eagles", eagles);
        json.put("birdies", birdies);
        json.put("pars", pars);
        json.put("bogeys", bogeys);
        json.put("doubleBogeys", doubleBogeys);
        json.put("tripleBogeys", tripleBogeys);
        json.put("worstHole", worstHole);
        json.put("lifetimeThrows", lifetimeThrows);
        json.put("lifetimeOverUnder", lifetimeOverUnder);
        
        try {
            File file = new File("profiles/" + username + ".json");
            FileWriter fw = new FileWriter(file.getAbsolutePath());
            fw.write(json.toString());
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        saveDiscs();
    }
    
    public void saveDiscs() throws IOException {
    	File file = new File("bags/" + username + "/bag.txt");
        FileWriter fw = new FileWriter(file.getAbsolutePath());
        for (Disc disc: discs.getDiscs()){
        	fw.write(disc.getName() + "\n");
        }
    }
	
	public String getUsername() { return username; }
	public String getDisplayName() { return displayName; }
	public Date getStartDate() { return startDate; }
	public boolean isRightHanded() { return isRightHanded; }
	public String getFavoriteDiscName() { return favoriteDiscName; }
	public String getFavoriteCourseName() { return favoriteCourseName; }
	public int getGamesPlayed() { return gamesPlayed; }
	public int getHolesInOne() { return holesInOne; }
	public int getAlabatrosses() { return albatrosses; }
	public int getEagles() { return eagles; }
	public int getBirdies() { return birdies; }
        public int getPars() { return pars; }
	public Bag getDiscs() { return discs; }
	protected int getLifetimeScore() { return lifetimeOverUnder; }
	protected int getBogeys() { return bogeys; }
	protected int getDoubleBogeys() { return doubleBogeys; }
	protected int getTripleBogeys() { return tripleBogeys; }
	protected int getWorstHole() { return worstHole; }
	protected int getLifetimeThrows() { return lifetimeThrows; }
	protected float getAverageOverUnder() { return averageOverUnder; }
	
	public void setDisplayName(String displayName) 
		{ this.displayName = displayName; }
	public void setIsRightHanded(boolean isRightHanded) 
		{ this.isRightHanded = isRightHanded; }
	public void setFavoriteDiscName(String favoriteDiscName) 
		{ this.favoriteDiscName = favoriteDiscName; }
	public void setFavoriteCourseName(String favoriteCourseName) 
		{ this.favoriteCourseName = favoriteCourseName; }
	public void setWorstHole(int worstHole) 
		{ this.worstHole = worstHole; }

	public void addGamePlayed() { gamesPlayed++; }
	public void addHolesInOne(int amount) { holesInOne += amount; }
	public void addAlbatrosses(int amount) { albatrosses += amount; }
	public void addEagles(int amount) { eagles += amount; }
	public void addBirdies(int amount) { birdies += amount; }
        public void addPars(int amount) { pars += amount; }
	public void addBogeys(int amount) { bogeys += amount; }
	public void addDoubleBogeys(int amount) { doubleBogeys += amount; }
	public void addTripleBogeys(int amount) { tripleBogeys += amount; }

	public void adjustLifetimeOverUnder(int amount) {
		this.lifetimeOverUnder += amount; 
	}
	
	public void updateAverageOverUnder() {
		averageOverUnder = lifetimeOverUnder / gamesPlayed;
	}
	
	public void addToLifetimeThrows(int amount) { lifetimeThrows += amount; }
	
	public void updateFromScorecard(ScorecardSummary summary) {
		addAlbatrosses(summary.getAlbatrosses());
        addPars(summary.getPars());
		addBirdies(summary.getBirdies());
		addBogeys(summary.getBogeys());
		addDoubleBogeys(summary.getDoubleBogeys());
		addTripleBogeys(summary.getTripleBogeys());
		addEagles(summary.getEagles());
		addHolesInOne(summary.getHolesInOne());
		adjustLifetimeOverUnder(summary.getOverUnderPar());
		addToLifetimeThrows(summary.getScore());
	    addPars(summary.getPars());
	    addGamePlayed();
	    if (summary.getWorstHole() > worstHole){
	        setWorstHole(summary.getWorstHole());
	    }
	    updateAverageOverUnder();
	}
	
	public void addDiscToBag(Disc disc) { discs.addDisc(disc); }
	public void setDiscs(Bag bag) { discs = bag; }
}