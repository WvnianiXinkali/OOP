import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		// return 0;
		if(str.length() == 0) return 0;
		char tmp = str.charAt(0);
		int answ = 0;
		int count = 0;
		for(int i = 0; i < str.length(); i++){
			if(tmp == str.charAt(i)){
				count++;
			} else {
				answ = max(count, answ);
				count = 1;
				tmp = str.charAt(i);
			}
		}// YOUR CODE HERE
		answ = max(count, answ);
		return answ;
	}


	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		//return null;
		if(str.length() == 0) return "";
		String ans = "";
		for(int i = 0; i < str.length() - 1; i++){
			if(Character.isDigit(str.charAt(i))){
				for(int j = 0; j < str.charAt(i) - '0';j ++){
					ans += str.charAt(i + 1);
				}
			} else {
				ans += str.charAt(i);
			}
		}
		if(Character.isDigit(str.charAt(str.length() - 1))){
			return ans;
		} else {
			return ans + str.charAt(str.length() - 1);
		}
		// YOUR CODE HERE
	}

	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		//return false;
		if(len == 0) return true;
		if(a.length() < len || b.length() < len) return false;

		HashSet<String> substrings = new HashSet<String>();
		for(int i = 0; i < a.length() - len + 1; i ++){
			substrings.add(a.substring(i, i + len));
		}

		for(int i = 0; i < b.length() - len + 1; i ++){
			if(substrings.contains(b.substring(i, i + len))) return true;
		}
		return false;
		// YOUR CODE HERE
	}

}
