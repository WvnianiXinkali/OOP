import java.util.*;

public class Appearances {

	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		//return 0;
		HashMap<T, Integer> freq = new HashMap<>();

		Iterator<T> iterator = a.iterator();

		while (iterator.hasNext()) {
			T tmp = iterator.next();
			if(!freq.containsKey(tmp)){
				freq.put(tmp, 1);
			} else {
				int m = freq.get(tmp);
				freq.put(tmp, m + 1);
			}
		}

		HashMap<T, Integer> freq2 = new HashMap<>();

		Iterator<T> iteratorb = b.iterator();

		int answ = 0;

		while (iteratorb.hasNext()) {
			T tmpb = iteratorb.next();

			if(freq.containsKey(tmpb)){
				int m = freq.get(tmpb);
				if(m > 1){
					freq.put(tmpb, m - 1);
				} else if (m == 1) {
					answ++;
					freq.put(tmpb, m - 1);
				} else if (m == 0) {
					answ--;
					freq.put(tmpb, m - 1);
				}
			}
		}
		return  answ;
		// YOUR CODE HERE
	}
}
