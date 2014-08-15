package frolf;

import org.json.simple.JSONObject;

public class Hole {
	private int holeNumber;				// Number of the hole at respective course
	private HoleType holeType;			// General layout of the hole
	private int distance;				// Distance (in feet) between the tee and the cage
	private int par;					// Number of throws considered the hole par
	private int timesPlayed;			// Number of times this hole has been played
	private int holesInOne;				// Number of times this hole has been aced
	private int bestScore;				// Lowest # of throws ever used to complete hole
	private int averageScore;			// Average # of throws used to complete hole
	private int worstScore;				// Most # of throws ever used to complete hole
	private String userWithBestScore;	// User with the 'bestScore'
	private String description;			// Description of the hole
	
	/* 
	 * Completely defined constructor
	 */
	public Hole(int holeNumber, HoleType holeType, int distance, int par, int timesPlayed,
			int holesInOne, int bestScore, int averageScore, int worstScore,
			String userWithBestScore, String description) {
		this.holeNumber = holeNumber;
		this.holeType = holeType;
		this.distance = distance;
		this.par = par;
		this.timesPlayed = timesPlayed;
		this.holesInOne = holesInOne;
		this.bestScore = bestScore;
		this.averageScore = averageScore;
		this.worstScore = worstScore;
		this.userWithBestScore = userWithBestScore;
		this.description = description;
	}
	
	/*
	 * Returns the hole in its specified JSON format
	 * TODO complete method
	 */
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		return obj;
	}
	
	/* GETTERS */
    public int getHoleNumber() { return holeNumber; }
    public HoleType getHoleType() { return holeType; }
    public int getDistance() { return distance; }
    public int getPar() { return par; }
    public int getTimesPlayed() { return timesPlayed; }
    public int getHolesInOne() { return holesInOne; }
    public int getBestScore() { return bestScore; }
    public int getAverageScore() { return averageScore; }
    public int getWorstScore() { return worstScore; }
    public String getUserWithBestScore() { return userWithBestScore; }
    public String getDescription() { return description; }
}
