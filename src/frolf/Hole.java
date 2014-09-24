package frolf;

import java.util.ArrayList;

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
	private String description;			// Description of the hole
	
	/* 
	 * Completely defined constructor
	 */
	public Hole(int holeNumber, HoleType holeType, int distance, int par, int timesPlayed,
			int holesInOne, int bestScore, int averageScore, int worstScore,
			String description) {
		this.holeNumber = holeNumber;
		this.holeType = holeType;
		this.distance = distance;
		this.par = par;
		this.timesPlayed = timesPlayed;
		this.holesInOne = holesInOne;
		this.bestScore = bestScore;
		this.averageScore = averageScore;
		this.worstScore = worstScore;
		this.description = description;
	}
	
	/*
	 * Returns the hole in its specified JSON format
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		
		obj.put("holeNumber", holeNumber);
		obj.put("holeType", holeType.toString());
		obj.put("distance", distance);
		obj.put("par", par);
		obj.put("timesPlayed", timesPlayed);
		obj.put("holesInOne", holesInOne);
		obj.put("bestScore", bestScore);
		obj.put("averageScore", averageScore);
		obj.put("worstScore", worstScore);
		obj.put("description", description);
		
		return obj;
	}
	
	/*
	 * Update the hole information from scorecard
	 * Updates the stats specific to this hole
	 */
	public void updateFromScorecard(Scorecard scorecard) {
		ArrayList<Integer> scores = scorecard.getScoresForHole(holeNumber);
		for (int score : scores) {
			addTimePlayed();
			if (score == 1) { addHolesInOne(1); }
			if (score < bestScore) { bestScore = score; }
			averageScore = (averageScore + score) / timesPlayed;
			if (score > worstScore) { worstScore = score; }
		}
	}
	
	/** GETTERS **/
    public int getHoleNumber() { return holeNumber; }
    public HoleType getHoleType() { return holeType; }
    public int getDistance() { return distance; }
    public int getPar() { return par; }
    public int getTimesPlayed() { return timesPlayed; }
    public int getHolesInOne() { return holesInOne; }
    public int getBestScore() { return bestScore; }
    public int getAverageScore() { return averageScore; }
    public int getWorstScore() { return worstScore; }
    public String getDescription() { return description; }
    
    /** SETTERS++ **/
    public void addHolesInOne(int amount) { holesInOne += amount; }
    public void addTimePlayed() { timesPlayed++; }
}
