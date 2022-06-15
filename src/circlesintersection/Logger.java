package circlesintersection;

import circlesintersection.models.AnglePair;

import java.util.ArrayList;

/**
 * Providers logging methods for debug.
 */
public final class Logger {

    /**
     * Private constructor, since no instance
     */
    private Logger() {
        throw new RuntimeException("Utils class cannot be instantiated");
    }

    /**
     * Debug method. Prints anglePairs to console.
     * @param anglePairsListArray   2d arraylist of {@link AnglePair}. It tells what arcs
     *                              are there for every circle.
     */
    public static void printAnglePairs(ArrayList<ArrayList<AnglePair>> anglePairsListArray) {
        for (ArrayList<AnglePair> anglePairs : anglePairsListArray) {
//            System.out.println("Arc #" + i++ + " inters-s with arcs #"+ itArray.next());
            System.out.println(anglePairs);
        }
    }

    /**
     * Debug method. Prints resulting array of anglePairs, i.e. AnglePairsFinal to console.
     */
    public static void printAnglePairsFinal(ArrayList<AnglePair> anglePairsListFinal) {
        for (int i = 0; i < anglePairsListFinal.size(); i++) {
            System.out.print(anglePairsListFinal.get(i) + "\t");
            if (i % 5 == 0 && i != 0) {
                System.out.println();
            }
        }
    }
}
