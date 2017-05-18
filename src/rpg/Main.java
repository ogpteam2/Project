package rpg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {	
	public static void main(String[] args) {
		Pattern validNamePattern = Pattern.compile("[A-Za-z_]+");
		print(validNamePattern);
		Matcher matcher = validNamePattern.matcher("ab");
		print(matcher);
		print(matcher.matches());
		print("lol".matches("[A-Za-z_]+"));
	}
	
	public static void print(Object object){
		System.out.println(object.toString());
	}

}
