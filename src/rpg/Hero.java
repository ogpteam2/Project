package rpg;

import java.util.regex.Pattern;

public class Hero extends Mobile {

	public Hero(String name) throws IllegalArgumentException {
		super(name);
		this.validNamePattern = Pattern.compile("[A-Za-z]+");
	}

	private final Pattern validNamePattern;
	
	@Override
	public Pattern getValidNamePattern() {
		return this.validNamePattern;
	}


}