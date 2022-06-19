package circlesintersection.utils;

import circlesintersection.models.AnglePair;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Provides logging methods for debug.
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
     * FIXME
     * Debug method. Prints resulting array of anglePairs, i.e. AnglePairsFinal to console.
     */
    public static void printAnglePairsFinal(ArrayList<Arc2D> graphicsShapes) {
        for (int i = 0; i < graphicsShapes.size(); i++) {
            System.out.print(graphicsShapes.get(i) + "\t");
            if (i % 5 == 0 && i != 0) {
                System.out.println();
            }
        }
    }
}
