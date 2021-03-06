package discs;

public class Disc {
	protected String name;				 // Disc name
	protected Manufacturer manufacturer; // Manufacturer of disc
	protected DiscType discType;		 // Disc type (putt, midrange, driver...)
	protected Stability stability;		 // Stability of disc (under, over, stable)
	protected int speed;				 // Speed rating of disc (1-15)
	protected int glide;   				 // Glide rating of disc (0-7)
	protected int turn;					 // Turn rating of disc (-4, 4)
	protected int fade;					 // Fade rating of disc (-4, 4)
	//TODO add PlasticType. Will be multiple. Differences between manufacturer?

	/*
	 * Completely defined constructor
	 */
	public Disc(String name, Manufacturer manufacturer, DiscType discType, Stability stability,
			int speed, int glide, int turn, int fade) {
		this.name = name;
		this.manufacturer = manufacturer;
		this.discType = discType;
		this.stability = stability;
		this.speed = speed;
		this.glide = glide;
		this.turn = turn;
		this.fade = fade;
	}
	
	/*
	 * Outputs to STDOUT the summary
	 */
	public void show() { 
		System.out.println(getSummary());
	}
	
	/*
	 * Prettified toString()
	 */
	public String getSummary() {
		return "Name: " + name + "\n" +
			"Disc Type: " + discType.toString() + "\n" + 
			"Stability: " + stability.toString() + "\n" +
			"Manufacturer: " + manufacturer.toString() + "\n" + 
			"Speed: " + speed + "\n" + 
			"Glide: " + glide + "\n" +
			"Turn: " + turn + "\n" +
			"Fade: " + fade + "\n";
	}
	
	/** GETTERS *(*/
	public String getName() { return name; }
	public DiscType getDiscType() { return discType; }
	public Stability getStability() { return stability; }
	public Manufacturer getManufacturer() { return manufacturer; }
	public int getSpeed() { return speed; }
	public int getGlide() { return glide; }
	public int getTurn() { return turn; }
	public int getFade() { return fade; }
	
	/** SETTERS **/
	public void setName(String name) { this.name = name; }
	public void setDiscType(DiscType discType) { this.discType = discType; }
	public void setStability(Stability stability) { this.stability = stability; }
	public void setManufacturer(Manufacturer manufacturer) { this.manufacturer = manufacturer; }
	public void setSpeed(int speed) { this.speed = speed; }
	public void setGlide(int glide) { this.glide = glide; }
	public void setTurn(int turn) { this.turn = turn; }
	public void setFade(int fade) { this.fade = fade; }
}