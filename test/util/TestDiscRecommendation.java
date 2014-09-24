package util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import bags.Bag;
import discs.DiscType;

public class TestDiscRecommendation {

	/*
	 * Tests that a putter is recommended when an empty bag is passed in.
	 */
	@Test
	public void testEmptyBag() {
		Bag bag = new Bag();
		
		DiscType expected = DiscType.PUTTAPPROACH;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		System.out.println("" + bag.getLackingDiscType());
		assertTrue("A putter was not recommended for an empty bag. Got: " + actual, 
				expected == actual);
	}
	
	/*
	 * Tests that a mid-range is recommended when a
	 * bag contains only a putter.
	 */
	@Test
	public void testOnlyPutter() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		
		DiscType expected = DiscType.MIDRANGE;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A mid-range was not recommended for a bag containing only a putter. Got: " + actual, 
				expected == actual);
	}
	
	/*
	 * Tests that a fairway driver is recommended when a
	 * bag contains a putter and a midranger.
	 */
	@Test
	public void testPutterAndMidRanger() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		bag.addDisc(allDiscs.getDisc("Innova Roc"));
		
		DiscType expected = DiscType.FAIRWAYDRIVER;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A fairway driver was not recommended for a bag containing a putter and a mid ranger. Got: " + actual, 
				expected == actual);
	}
	
	/*
	 * Tests that a distance driver is recommended when a
	 * bag contains a putter, a midranger, and a fairway driver.
	 */
	@Test
	public void testPutterMidAndFair() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		bag.addDisc(allDiscs.getDisc("Innova Roc"));
		bag.addDisc(allDiscs.getDisc("Innova Leopard"));
		
		DiscType expected = DiscType.DISTANCEDRIVER;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A distance driver was not recommended for a bag containing a putter, midranger and fairway driver. Got: " + actual, 
				expected == actual);
	}
	
	/*
	 * Tests that a distance driver is recommended for a
	 * bag whose ratio of distance driver ratio is farthest from ideal.
	 */
	@Test
	public void testDistanceRatioFarthestFromIdeal() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		bag.addDisc(allDiscs.getDisc("Innova Roc"));
		bag.addDisc(allDiscs.getDisc("Innova Leopard"));
		bag.addDisc(allDiscs.getDisc("Innova Boss"));
		
		DiscType expected = DiscType.DISTANCEDRIVER;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A distance driver was not recommended for a bag whose ratio of distance drivers is farthest from ideal. Got: " + actual, 
				expected == actual);
	}
	
	/*
	 * Tests that a distance driver is recommended for a bag
	 * whose fairway ratio is farthest from ideal.
	 */
	@Test
	public void testFairwayRatioFarthestFromIdeal() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		bag.addDisc(allDiscs.getDisc("Innova Roc"));
		bag.addDisc(allDiscs.getDisc("Innova Roc"));
		bag.addDisc(allDiscs.getDisc("Innova Leopard"));
		bag.addDisc(allDiscs.getDisc("Innova Boss"));

		
		DiscType expected = DiscType.FAIRWAYDRIVER;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A fairway driver was not recommended for a bag whose ratio of fairway drivers is farthest from ideal. Got:" + actual, 
				expected == actual);
	}

	/*
	 * Tests that a midranger is recommended for a
	 * bag whose midrange ratio is the farthest from ideal.
	 */
	@Test
	public void testMidrangeRatioFarthestFromIdeal() throws IOException {
		Bag allDiscs = FrolfUtil.loadDiscs();
		Bag bag = new Bag();
		bag.addDisc(allDiscs.getDisc("Innova Polecat"));
		bag.addDisc(allDiscs.getDisc("Innova Roc"));
		bag.addDisc(allDiscs.getDisc("Innova Leopard"));
		bag.addDisc(allDiscs.getDisc("Innova Leopard"));
		bag.addDisc(allDiscs.getDisc("Innova Boss"));

		
		DiscType expected = DiscType.MIDRANGE;
		DiscType actual = FrolfUtil.recommendDiscForBag(bag).getDiscType();
		
		assertTrue("A midranger was not recommended for a bag whose ratio of midrangers is farthest from ideal. Got:" + actual, 
				expected == actual);
	}
}
