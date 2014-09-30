package bags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import discs.Disc;
import discs.DiscType;
import discs.Manufacturer;
import discs.Stability;

public class Bag {								     // Desired percent of bag to be:
	public final static int IDEAL_PUTTER_PCT = 10;   //  -Putters
	public final static int IDEAL_MIDRANGE_PCT = 35; //  -Midrange discs
	public final static int IDEAL_FDRIVER_PCT = 35;  //  -Fairway drivers
	public final static int IDEAL_DDRIVER_PCT = 20;  //  -Distance drivers
	public final static int MIN_STABILITY_PCT = 25;  // Minimum % a stability ratio can be
													 // before a disc will be suggested
    protected ArrayList<Disc> discs; 				 // Discs in this bag
	private Logger LOGGER;							 // Class logger

    /* 
     * Default constrcutor - empty bag
     */
    public Bag() {
        discs = new ArrayList<Disc>();
    	LOGGER = Logger.getLogger( Bag.class.getName() );
    }
    
    /*
     * Fully defined constructor - pre-populated bag
     */
    public Bag(ArrayList<Disc> discs) {
    	this.discs = discs; 
    	LOGGER = Logger.getLogger( Bag.class.getName() );
    }

    /*
     * Add disc to the bag. Accepts duplicates.
     */
    public void addDisc(Disc disc) {
    	LOGGER.log(Level.INFO, "Adding disc to bag " + this.hashCode() + ". Now there are " + size() + " discs.");
        discs.add(disc);
    }

    /*
     * Returns the # of discs in the bag
     */
    public int size() {
        return discs.size();
    }
    
    /*
     * Returns true if this bag contains the specified disc,
     * false if it does not.
     */
    public boolean has(Disc disc) { 
		HashSet<Disc> discSet = new HashSet<Disc>(discs); 
		return discSet.contains(disc);
	}
    
    /*
     * Returns a HashMap with
     * 	key -> String discName
     *  value -> Disc disc
     */
    public HashMap<String, Disc> asHash() {
    	LOGGER.log(Level.INFO, "Generating hash representation for bag " + this.hashCode());
    	HashMap<String, Disc> hash = new HashMap<String, Disc>();

        Iterator<Disc> i = discs.iterator();
        while (i.hasNext()) {
            Disc disc = i.next();
            hash.put(disc.getName(), disc);
        }

        return hash;
    }
    
    /****************************************************
     *  GETTERS
     ***************************************************/
    
    /*
     * Prettified toString()
     */
    public String getSummary() {
    	LOGGER.log(Level.INFO, "Generating string summary for bag " + this.hashCode());
    	String output = "";
    	
    	for (Disc disc : discs) {
    		output += "\n* -- " + disc.getName();
    	}
    	
    	return output;
    }

    /*
     * Returns the bag's raw data
     */
    public ArrayList<Disc> getDiscs() {
        return discs;
    }

    /*
     * Returns the Disc with name 'discName' if it exists,
     * returns null if it doesn't.
     * 
     * TODO asHash overwrites discs with identical names. 
     * 		Determine if needs fixed.
     */
    public Disc getDisc(String discName) {
    	LOGGER.log(Level.INFO, "Looking for disc " + discName + " in bag " + this.hashCode());
        return asHash().get(discName);
    }

    /*
     * Returns a Bag containing a subset of this Bag 
     * where discType equals the argument.
     */
    public Bag getDiscsByType(DiscType discType) {
    	LOGGER.log(Level.INFO, "Getting all discs of type " + discType);

        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getDiscType() == discType) {
                discBag.addDisc(discs.get(i));
            }
        }
        
        LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs found of type " + discType);
        return discBag;
    }
    
    /*
     * Returns a Bag containing a subset of this Bag 
     * where stability equals the argument.
     */
    public Bag getDiscsWithStability(Stability stability) {
    	LOGGER.log(Level.INFO, "Getting discs with stability " + stability);
    	
        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getStability() == stability) {
                discBag.addDisc(discs.get(i));
            }
        }
        
        LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs with stability " + stability);
        return discBag;
    }

    /*
     * Returns a Bag containing a subset of this Bag 
     * where manufacturer equals the argument.
     */
    public Bag getDiscsFromManufacturer(Manufacturer manufacturer) {
    	LOGGER.log(Level.INFO, "Getting discs by manufacturer " + manufacturer);
    	
        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getManufacturer() == manufacturer) {
                discBag.addDisc(discs.get(i));
            }
        }
        
        LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs from manufacturer" + manufacturer);
        return discBag;
    }
    
    /*
     * Returns a Bag containing a subset of this Bag 
     * where speed equals the argument.
     */
    public Bag getDiscsWithSpeed(int speed) {
    	LOGGER.log(Level.INFO, "Getting discs with speed " + speed);
    	
        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getSpeed() == speed) {
                discBag.addDisc(discs.get(i));
            }
        }
        
        LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs with speed " + speed);
        return discBag;
    }

    /*
     * Returns a Bag containing a subset of this Bag 
     * where glide equals the argument.
     */
    public Bag getDiscsWithGlide(int glide) {
    	LOGGER.log(Level.INFO, "Getting discs with glide " + glide);
    	
        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getGlide() == glide) {
                discBag.addDisc(discs.get(i));
            }
        }
        
    	LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs with glide " + glide);
        return discBag;
    }

    /*
     * Returns a Bag containing a subset of this Bag 
     * where fade equals the argument.
     */
    public Bag getDiscsWithFade(int fade) {
    	LOGGER.log(Level.INFO, "Getting discs with fade " + fade);
    	
        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getFade() == fade) {
                discBag.addDisc(discs.get(i));
            }
        }
        
        LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs with fade " + fade);
        return discBag;
    }

    /*
     * Returns a Bag containing a subset of this Bag 
     * where turn equals the argument.
     */
    public Bag getDiscsWithTurn(int turn) {
    	LOGGER.log(Level.INFO, "Getting discs with turn " + turn);
    	
        Bag discBag = new Bag();
        for (int i = 0; i < discs.size(); i++) {
            if (discs.get(i).getTurn() == turn) {
                discBag.addDisc(discs.get(i));
            }
        }
        
        LOGGER.log(Level.INFO, "Found " + discBag.size() + " discs with turn " + turn);
        return discBag;
    }
	
	/*
	 * Returns UNKNOWN if there is an acceptable distribution of DiscType's,
	 * returns DiscType.X if X's ratio is sub-optimal. 
	 */
	public DiscType getLackingDiscType() {
		LOGGER.log(Level.INFO, "Calculating the disc type whose ratio is farthest from ideal."); 
		
		int numPutters = getDiscsByType(DiscType.PUTTAPPROACH).size();
		LOGGER.log(Level.INFO, numPutters + " putters in bag.");
		int numMidRangers = getDiscsByType(DiscType.MIDRANGE).size();
		LOGGER.log(Level.INFO, numMidRangers + " midrangers in bag.");
		int numFDrivers = getDiscsByType(DiscType.FAIRWAYDRIVER).size();
		LOGGER.log(Level.INFO, numFDrivers + " fairway drivers in bag.");
		int numDDrivers = getDiscsByType(DiscType.DISTANCEDRIVER).size();
		LOGGER.log(Level.INFO, numDDrivers + " distance drivers in bag.");
		
		float puttDiff = IDEAL_PUTTER_PCT - ((float)(numPutters * 100) / size());
		float midDiff = IDEAL_MIDRANGE_PCT - ((float)(numMidRangers * 100) / size());
		float fairDiff = IDEAL_FDRIVER_PCT - ((float)(numFDrivers * 100) / size());
		float distDiff = IDEAL_DDRIVER_PCT - ((float)(numDDrivers * 100) / size());
		
		if (numPutters == 0 || (puttDiff > midDiff && puttDiff > fairDiff && puttDiff > distDiff)) {
			LOGGER.log(Level.INFO, "Putter ratio is farthest from ideal.");
			return DiscType.PUTTAPPROACH;
		} else if (numMidRangers == 0 || (midDiff > puttDiff && midDiff > fairDiff && midDiff > distDiff)) {
			LOGGER.log(Level.INFO, "Midrage ratio is farthest from ideal.");
			return DiscType.MIDRANGE;
		} else if (numFDrivers == 0 || (fairDiff > puttDiff && fairDiff > midDiff && fairDiff > distDiff)) {
			LOGGER.log(Level.INFO, "Fairway driver ratio is farthest from ideal.");
			return DiscType.FAIRWAYDRIVER;
		} else {
			LOGGER.log(Level.INFO, "Distance Driver ratio is farthest from ideal.");
			return DiscType.DISTANCEDRIVER;
		}		
	}
	
	/*
	 * Checks the stability ratio for the disc type 'discType'.
	 * Returns Stability.UNKNOWN if the ratios are acceptable,
	 * Returns Stability.X if X has a lacking stability
	 * 
	 * Prioritizes STABLE > UNDERSTABLE > OVERSTABLE
	 */
	public Stability getLackingStabilityForType(DiscType discType){
		LOGGER.log(Level.INFO, "Determining lacking stability rating for disc type " + discType);
		
		Bag tDiscs = getDiscsByType(discType);
		if (tDiscs.size() == 0) { return Stability.STABLE; }
		
		float stablePct = (float)(tDiscs.getDiscsWithStability(Stability.STABLE).size() * 100) / tDiscs.size();
		float usPct = (float)(tDiscs.getDiscsWithStability(Stability.UNDERSTABLE).size() * 100) / tDiscs.size();
		float osPct = (float)(tDiscs.getDiscsWithStability(Stability.OVERSTABLE).size() * 100) / tDiscs.size();

		if (stablePct < MIN_STABILITY_PCT) {
			LOGGER.log(Level.INFO, "Lacking stability rating is STABLE.");
			return Stability.STABLE;
		} else if (usPct < MIN_STABILITY_PCT) {
			LOGGER.log(Level.INFO, "Lacking stability rating is UNDERSTABLE");
			return Stability.UNDERSTABLE;
		} else if (osPct < MIN_STABILITY_PCT) {
			LOGGER.log(Level.INFO, "Lacking stability rating is OVERSTABLE");
			return Stability.OVERSTABLE;
		}
		
		return Stability.UNKNOWN;
	}
	
    /*
     * Returns a new Bag containing the Set difference
     * Set<this> - Set<argument>
     */
	public Bag minus(Bag excluded) {
		LOGGER.log(Level.INFO, "Getting the 'Set.minus' for this bag");
		Bag returnBag = new Bag(discs);
		returnBag.getDiscs().removeAll(excluded.getDiscs());
		return returnBag;
	}
}