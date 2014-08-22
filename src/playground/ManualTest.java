package playground; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import util.FrolfUtil;
import bags.Bag;
import discs.Disc;
import frolf.Scorecard;
import frolf.Scorecard.ScorecardSummary;

public class ManualTest {
	public static void main(String[] args) throws IOException {
		testScorecardSummary();
	}
	
	public static void testScorecardSummary() {
		ArrayList<Integer> score = new ArrayList<Integer>();
		for (int i = 0; i < 16; i++) { score.add(4); }
		score.add(3);
		score.add(2);
		
		ArrayList<Integer> pars = new ArrayList<Integer>();
		for (int i = 0; i < 18; i++) {  pars.add(3); }
		
		HashMap<String, ArrayList<Integer>> scores = new HashMap<String, ArrayList<Integer>>();
		scores.put("Me", score);
		
		Scorecard scorecard = new Scorecard(
			"Test Course",
			scores,
			pars
		);
		
		ScorecardSummary summary = scorecard.getSummaries().get("Me");
		
		System.out.println(summary.getBogeys());
		System.out.println(summary.getPars());
		System.out.println(summary.getBirdies());
	}
	
	public static void recommendDisc() throws IOException{
		Bag bag = new Bag();
		Bag discs = FrolfUtil.loadDiscs();
		
		bag.addDisc(discs.getDisc("Innova Polecat"));
		bag.addDisc(discs.getDisc("Innova Kite"));
		bag.addDisc(discs.getDisc("Innova TeeBird"));
		
		bag.getSummary();
		System.out.println("Testing");
		Disc recommendedDisc = FrolfUtil.recommendDiscForBag(bag);
		
		recommendedDisc.show();
	}
}
