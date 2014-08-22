package frolf;

import java.util.ArrayList;

public class Course {
	private String name;               // Name of the course
	private String address;			   // Address (# Street, City, State, Zip)
	private int bestScore;			   // Best full-round score at this course
	private int averageScore;		   // Average full-round score at this course
	private int worstScore;			   // Worst full-round score at this course
	private int timesPlayed;		   // Number of times this course has been played
	private ArrayList<Hole> holes;     // The holes at this course
	private String userWithBestScore;  // Username of the person with 'bestScore'
	private String description;        // Course description
	
	/*
	 * Completely defined constructor
	 */
	public Course(String name, String address, int bestScore,
			int averageScore, int worstScore, int timesPlayed,
			ArrayList<Hole> holes, String userWithBestScore, 
			String description){
		this.name = name;
		this.address = address;
		this.bestScore = bestScore;
		this.averageScore = averageScore;
		this.worstScore = worstScore;
		this.timesPlayed = timesPlayed;
		this.holes = holes;  
		this.userWithBestScore = userWithBestScore;
		this.description = description;
	}
	
	/*
	 * Default constructor
	 */
	public Course() {
		holes = new ArrayList<Hole>();
		name = "Undefined course"; address = "N/A";
		bestScore = 3; averageScore = 3; worstScore = 3; timesPlayed = 1;
		userWithBestScore = "N/A"; description = "Undefined course.";
	}
	
	/* GETTERS */
	public String getName() { return name; }
	public String getAddress() { return address; }
	public int getBestScore() { return bestScore; }
	public int getAverageScore() { return averageScore; }
	public int getWorstScore() { return worstScore; }
	public int getTimesPlayed() { return timesPlayed; }
	public String getUserWithBestScore() { return userWithBestScore; }
	public String getDescription() { return description; }
	/* GETTERS++ */
	public int getNumberOfHoles() { return holes.size(); }
	
	/*
	 * Returns Hole with holeNumber == 'holeNumber' if exists,
	 * returns null if it doesn't
	 */
	public Hole getHole(int holeNumber) {
		for (Hole hole : holes) {
		    if (hole.getHoleNumber() == holeNumber) { return hole; }
		}
	        
        return null;
    }
	
	/*
	 * Returns a list of the pars for this course
	 */
	public ArrayList<Integer> getPars() {
		ArrayList<Integer> pars = new ArrayList<Integer>();
		for (int i = 0; i < getNumberOfHoles(); i++) {
			pars.add(holes.get(i).getPar());
		}
		
		return pars;
	}
	  
	/* SETTERS++ */
	public void addHole(Hole hole) {
		holes.add(hole);
	}
}
