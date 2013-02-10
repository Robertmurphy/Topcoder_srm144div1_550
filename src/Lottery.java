
import java.util.*;
import java.util.Map.Entry;

public class Lottery {
	
	public String[] sortByOdds(String[] rules) {
		
		String[] returnResults = new String[rules.length];
		int returnResultsTracker = 0;
		Map<String, Double> results = new TreeMap<String, Double>();
		
		for (String s : rules) {
			String name = s.split(":")[0]; name = name.trim();
			String data = s.split(":")[1]; data = data.trim();
			int choices = Integer.parseInt(data.split(" ")[0]);
			int blanks = Integer.parseInt(data.split(" ")[1]);
			char sorted = data.split(" ")[2].charAt(0);
			char unique = data.split(" ")[3].charAt(0);
			boolean sortedBoolean = false;
			boolean uniqueBoolean = false;
			if (sorted == 'T') sortedBoolean = true;
			if (unique == 'T') uniqueBoolean = true;

			double weight = 0;
			if (sortedBoolean && uniqueBoolean) weight = (nCr(choices, blanks));
			else if (sortedBoolean) weight = (nCrWithReplacement(choices, blanks)); 
			else if (uniqueBoolean) weight = (nPr(choices, blanks));
			else weight = nPrWithReplacement(choices, blanks);

			results.put(name, weight);
		}
		
		for (Entry<String, Double> entry  : entriesSortedByValues(results)) {
			System.out.println(entry.getKey() + "///" + entry.getValue());
			returnResults[returnResultsTracker++] = entry.getKey(); 
		}
		
		return returnResults;
	}
	
	private static double nCr(int n, int r) {
		double op1 = 1;
		double op2 = 1;
		for (int i=0; i<r; i++) op1 *= (n-i);
		for (int i=0; i<r; i++) op2 *= (r-i);
		return op1/op2;
	}
	
	private static double nPr(int n, int r) {
		double op1 = 1;
		for (int i=0; i<r; i++) op1 *= (n-i);
		return op1;
	}
	
	private static double nCrWithReplacement(int n, int r) {
		n = n+r-1;
		double op1 = 1;
		double op2 = 1;
		for (int i=0; i<r; i++) op1 *= (n-i);
		for (int i=0; i<r; i++) op2 *= (r-i);
		return op1/op2;
	}
	
	private static double nPrWithReplacement(int n, int r) {
		double op1 = 1;
		for (int i=0; i<r; i++) op1 *= n;
		return op1;
	}
	
	private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>() {
                public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1; // Special fix to preserve items with equal values
                }
            }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

	public static void main(String... args) {
		Lottery lottery = new Lottery();
		for (String s : lottery.sortByOdds(new String[] {"INDIGO: 93 8 T F",
				 "ORANGE: 29 8 F T",
				 "VIOLET: 76 6 F F",
				 "BLUE: 100 8 T T",
				 "RED: 99 8 T T",
				 "GREEN: 78 6 F T",
				 "YELLOW: 75 6 F F"}))
			System.out.println(s);
	}
	
}
