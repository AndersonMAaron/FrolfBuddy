package frolf;

import java.util.ArrayList;
import java.util.HashMap;

public class Scorecard {
	private String courseName; 	      // Name of the course played
	private HashMap<String, ArrayList<Integer>> scores; // Player scores for each hole
	private ArrayList<Integer> pars;  // Course pars for each hole

	/* 
	 * Completely defined constructor
	 */
	public Scorecard(String courseName,
			HashMap<String, ArrayList<Integer>> scores,
			ArrayList<Integer> pars) {
            this.courseName = courseName;
            this.scores = scores;
            this.pars = pars;
	}

	/*
	 * Save the scorecard in JSON format
	 * 
	 * TODO complete method
	 */
	public void save() {}

	/*
	 * Returns a HashMap where
	 *  key -> Profile username
	 *  value -> ScorecardSummary of the round 
	 */
	public HashMap<String, ScorecardSummary> getSummaries() {

		HashMap<String, ScorecardSummary> summaries = new HashMap<String, ScorecardSummary>();

		for (String profileUsername : scores.keySet()) {
			ArrayList<Integer> profileScores = scores.get(profileUsername);
			int parz = 0, eagles = 0, birdies = 0, bogeys = 0, doubleBogeys = 0;
			int tripleBogeys = 0, overUnderPar = 0, parScore = 0, score = 0;
			int holesInOne = 0, albatrosses = 0, worstHole = 0;

			for (int hole = 0; hole < profileScores.size(); hole++) {

				int thisPar = pars.get(hole);
				int thisScore = profileScores.get(hole);
				
				if (thisScore == 1) { holesInOne++; }   
                if (thisScore > worstHole) { worstHole = thisScore; }

				parScore += thisPar;
				score += thisScore;
				overUnderPar += (thisScore - thisPar);

				switch (thisScore - thisPar) {
					case 0:
						parz++;
						break;
					case 1:
						bogeys++;
						break;
					case 2:
						doubleBogeys++;
						break;
					case 3:
						tripleBogeys++;
						break;
					case -1:
						birdies++;
						break;
					case -2:
						eagles++;
						break;
					case -3:
						albatrosses++;
						break;
				}
			}
			// Create the summary and add it to the map
			summaries.put(profileUsername, new ScorecardSummary(courseName,
					holesInOne, albatrosses, eagles, birdies, parz, bogeys,
					doubleBogeys, tripleBogeys, score, overUnderPar, parScore, worstHole));
		}

		return summaries;
	}

	/** 
	 * @class ScorecardSummary
	 * @purpose Translation message from the Scorecard
	 * to the profile. Used to update profile statistics.
	 * @author Aaron Anderson
	 */
	public class ScorecardSummary {
		/* Profile relative values */
		protected String courseName;
		protected int pars, eagles, birdies, holesInOne, albatrosses;
		protected int bogeys, doubleBogeys, tripleBogeys;
		protected int overUnderPar, parScore, score, worstHole;

		/*
		 * Completely defined constructor
		 */
		public ScorecardSummary(String courseName, int holesInOne,
				int albatrosses, int eagles, int birdies, int pars, int bogeys,
				int doubleBogeys, int tripleBogeys, int score,
				int overUnderPar, int parScore, int worstHole) {

			this.courseName = courseName;
			this.pars = pars;
			this.eagles = eagles;
			this.birdies = birdies;
			this.albatrosses = albatrosses;
			this.holesInOne = holesInOne;
			this.bogeys = bogeys;
			this.doubleBogeys = doubleBogeys;
			this.tripleBogeys = tripleBogeys;
			this.overUnderPar = overUnderPar;
			this.parScore = parScore;
			this.score = score;
            this.worstHole = worstHole;
		}

		/* GETTERS */
		public String getCourseName() { return courseName; }
		public int getHolesInOne() { return holesInOne; }
		public int getPars() { return pars;  }
		public int getBirdies() { return birdies; }
		public int getEagles() { return eagles; }
		public int getAlbatrosses() { return albatrosses; }
		public int getBogeys() { return bogeys; }
		public int getDoubleBogeys() { return doubleBogeys; }
		public int getTripleBogeys() { return tripleBogeys; }
		public int getOverUnderPar() { return overUnderPar; }
		public int getParScore() { return parScore; }
		public int getScore() { return score; }
        public int getWorstHole() { return worstHole; }
	}
}