package util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import bags.Bag;
import discs.DiscType;

public class TestDiscRecommendation {

	/*
	 * Tests that a putter is recommended when an empty bag is passed in.
	 * @Test
	 */
	public void emptyBag() {
		Bag bag = new Bag();
		
		DiscType expected = DiscType.PUTTAPPROACH;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A putter was not recommended for an empty bag", 
				expected == actual);
	}
	
	/*
	 * Tests that a mid-range is recommended when a
	 * bag contains only a putter.
	 * @Test
	 */
	public void onlyPutter() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		
		DiscType expected = DiscType.MIDRANGE;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A mid-range was not recommended for a bag containing only a putter", 
				expected == actual);
	}

}
