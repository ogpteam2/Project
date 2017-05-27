package rpg;

import java.util.regex.Pattern;

import rpg.inventory.Anchorpoint;

public class Hero extends Mobile {

	private Anchorpoint body;
	private Anchorpoint leftHand;
	private Anchorpoint rightHand;
	private Anchorpoint belt;
	private Anchorpoint back;
	
	public Hero(String name) throws IllegalArgumentException {
		super(name);
		this.
	}

	private static final Pattern validNamePattern = Pattern.compile("[A-Za-z]+");
	
	@Override
	public Pattern getValidNamePattern() {
		return this.validNamePattern;
	}


}