
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {

	private List<T> rules;

	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		this.rules = rules;
	}

	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		//return null;
		//HashMap<T, Set<T>> map = new HashMap<>();

		Set<T> answ = new HashSet<>();

		for(int i = 0; i < rules.size() - 1; i++){
//			T k = rules.get(i);
//			if(map.containsKey(k)){
//				Set<T> tmp = map.get(k);
//				tmp.add(rules.get(i + 1));
//				map.put(k, tmp);
//			} else {
//				Set<T> carieli = new HashSet<>();
//				map.put(k, carieli);
//			}
			if(rules.get(i) != null && rules.get(i).equals(elem)){
				if(rules.get(i+1) != null) answ.add(rules.get(i+1));
			}
		}
		if(answ.isEmpty()) return Collections.emptySet();
		return answ;
		//return map.get(elem);
		// YOUR CODE HERE
	}

	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		for (int i = 0; i < list.size() - 1; i ++){
			if(noFollow(list.get(i)).contains(list.get(i + 1))) {
				list.remove(i + 1);
				i--;
			}
		}
	}

}
