
import java.util.Comparator;

public class StringLengthSort implements Comparator<String> {
	/* Compare two string based on their length */
	public int compare(String s1, String s2) {
		
		if (s1.length() < s2.length())
			return 1;
		else if (s1.length() > s2.length())
			return -1;
		else 
			return 0;
	}
}