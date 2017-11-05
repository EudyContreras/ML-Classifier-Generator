package classifier;

/**
 * Utility class which contains helper methods
 * 
 * @author Eudy Contreras & Pierre Leidbring
 *
 */
public class Utilities {
	
	public static enum DiscreteTag {
		VERY_HIGH, HIGH, MEDIUM, LOW, VERY_LOW
	}

	public static DiscreteTag discretize(int upperLimit, int value) {
		
		double percentage = ((double)value / (double)upperLimit);
		
		if (percentage < 0.1)
			return DiscreteTag.VERY_LOW;
		else if (percentage <= 0.3)
			return DiscreteTag.LOW;
		else if (percentage <= 0.5)
			return DiscreteTag.MEDIUM;
		else if (percentage <= 0.7)
			return DiscreteTag.HIGH;
		else
			return DiscreteTag.VERY_HIGH;
	}
}
