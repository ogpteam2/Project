package rpg;

import java.util.regex.Pattern;

public class Hero extends Mobile {

	private final Pattern validNamePattern = Pattern.compile("[A-Z][A-Za-z '{0,2}(: )]");
	
	@Override
	protected Pattern getValidNamePattern() {
		return this.validNamePattern;
	}


}