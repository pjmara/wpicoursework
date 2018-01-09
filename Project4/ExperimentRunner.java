import java.util.*;
import com.cs210x.*;

/**
 * Class to deduce the identity of mystery data structures.
 */
public class ExperimentRunner {
	private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;
	// Number of elements we want to test
	private static final int[] testingNumbers = {1, 2, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 
			600, 700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
	// The number of times we want to test for one single case
	private static final int TESTING_TIME = 100;
	// An Object Random shared by all methods
	private static final Random RANDOM = new Random();

	public static void main (String[] args) {
		final String cs210XTeamIDForProject4 = "klei"; // TODO CHANGE THIS TO THE TEAM ID YOU USE TO SUBMIT YOUR PROJECT3 ON INSTRUCT-ASSIST.

		// Fetch the collections whose type you must deduce.
		// Note -- you are free to change the type parameter from Integer to whatever you want. In this
		// case, make sure to replace (over the next 4 lines of code) Integer with whatever class you prefer.
		// In addition, you'll need to pass the method getMysteryDataStructure a "sample" (an instance) of 
		// the class you want the collection to store.
		@SuppressWarnings("unchecked")
		final Collection210X<Integer>[] mysteryDataStructures = (Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
		for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
			mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject4.hashCode(), i, new Integer(0));
		}

		for (int i = 0; i < mysteryDataStructures.length; i++) {
			Collection210X<Integer> structure = mysteryDataStructures[i];
			structure.clear();
			for (int j = 0; j < testingNumbers.length; j ++) {
				final int N = testingNumbers[j];
				for (int k = 0; k < N; k++) {
					structure.add(new Integer(k));
				}
				//Run one at a time so that the console does not look messy
//				testAdd(structure, i, N);
				testRemove(structure, i, N);
//				testContains(structure, i, N);
			}

		}
	}

	public static void testAdd(Collection210X<Integer> structure,int structIndex, int amount) {
//		System.out.println("Structure" + structIndex);
//		System.out.println("N\tT (add(o))");
		long totalElapsed = 0;
		for (int i = 0; i < TESTING_TIME; i++) {
			final int num = RANDOM.nextInt();
			final long start = CPUClock.getNumTicks();
			structure.add(num);
			final long end = CPUClock.getNumTicks();
			structure.remove(num);
			totalElapsed += (end - start);
		}
		final long averageElapsed = totalElapsed / TESTING_TIME;
		System.out.println(amount + "\t" + averageElapsed);
	}

	public static void testRemove(Collection210X<Integer> structure, int structIndex, int amount) {
		//Testing worst cases
		long totalElapsed = 0;
		long start = CPUClock.getNumTicks();
		structure.remove(amount - 1);
		long end = CPUClock.getNumTicks();
		structure.add(amount - 1);
		long timeElapsed = end - start;
//		System.out.println("Structure" + structIndex);
//		System.out.println("N\tT (remove(o))(worst)");
		System.out.println(amount + "\t" + timeElapsed);

		//Testing best cases
		start = CPUClock.getNumTicks();
		structure.remove(0);
		end = CPUClock.getNumTicks();
		structure.add(0);
		timeElapsed = end - start;
//		System.out.println("N\tT (remove(o))(best)");
		System.out.println(amount + "\t" + timeElapsed);

		//Testing average cases
		for (int i = 0; i < TESTING_TIME; i++) {
			final int num = RANDOM.nextInt(amount);
			start = CPUClock.getNumTicks();
			structure.remove(num);
			end = CPUClock.getNumTicks();
			structure.add(num);
			totalElapsed += (end - start);
		}
		final long averageElapsed = totalElapsed / TESTING_TIME;
//		System.out.println("N\tT (remove(o))");
		System.out.println(amount + "\t" + averageElapsed);
	}

	public static void testContains(Collection210X<Integer> structure, int structIndex, int amount) {
		long totalElapsed = 0;
		//Testing worst cases
		long start = CPUClock.getNumTicks();
		structure.contains(amount);
		long end = CPUClock.getNumTicks();
		long timeElapsed = end - start;
//		System.out.println("Structure" + structIndex);
//		System.out.println("N\tT (contains(o))(worst)");
		System.out.println(amount + "\t" + timeElapsed);

		//Testing best cases
		start = CPUClock.getNumTicks();
		structure.contains(0);
		end = CPUClock.getNumTicks();
		timeElapsed = end - start;
//		System.out.println("N\tT (contains(o))(best)");
		System.out.println(amount + "\t" + timeElapsed);

		//Testing if it is a heap
		start = CPUClock.getNumTicks();
		structure.contains(amount - 1);
		end = CPUClock.getNumTicks();
		timeElapsed = end - start;
//		System.out.println("N\tT (contains(o))(heap?)");
		System.out.println(amount + "\t" + timeElapsed);

		//Testing average cases
		for (int i = 0; i < TESTING_TIME; i++) {
			final int num = RANDOM.nextInt(amount);
			start = CPUClock.getNumTicks();
			structure.contains(num);
			end = CPUClock.getNumTicks();
			totalElapsed += (end - start);
		}
		final long averageElapsed = totalElapsed / TESTING_TIME;
//		System.out.println("N\tT (contains(o))");
		System.out.println(amount + "\t" + averageElapsed);
	}
}
