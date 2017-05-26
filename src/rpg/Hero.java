package rpg;

import java.util.regex.Pattern;

public class Hero extends Mobile {

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