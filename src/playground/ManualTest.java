package playground; 

import java.io.IOException;

import util.FrolfUtil;
import bags.Bag;
import discs.Disc;

public class ManualTest {
	public static void main(String[] args) throws IOException {
		Bag bag = new Bag();
		Bag discs = FrolfUtil.loadDiscs();
		
		bag.addDisc(discs.getDisc("Innova Polecat"));
		bag.addDisc(discs.getDisc("Innova Kite"));
		bag.addDisc(discs.getDisc("Innova TeeBird"));
		
		bag.show();
		System.out.println("Testing");
		Disc recommendedDisc = FrolfUtil.recommendDiscForBag(bag);
		
		recommendedDisc.show();
	}
}
