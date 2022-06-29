package circlesintersection.utils;

import circlesintersection.IllegalInstantiationException;
import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;

import java.awt.geom.Arc2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Provides logging methods for debug.
 */
public final class CirclesLogger {

    private static Logger LOGGER = null;

    static {
        InputStream stream = CirclesLogger.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOGGER = Logger.getLogger(CirclesLogger.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private constructor, since no instance
     */
    private CirclesLogger() {
        throw new IllegalInstantiationException("Utils class should not be instantiated");
    }

    /**
     * Prints arcs of every circle to console.
     *
     * @param circleWithArcsList list of circles that consists list of intersections (arcs).
     */
    public static void printArcs(List<CircleWithArcs> circleWithArcsList) {
        LOGGER.log(Level.INFO, "All arcs for all circles: ");
        for (int i = 0; i < circleWithArcsList.size(); i++) {
            LOGGER.log(Level.INFO, format("%d:  ", i));
            for (Arc arc : circleWithArcsList.get(i).getArcsList()) {
                LOGGER.log(Level.INFO, format("[%.2f:%.2f] ", arc.getAngleBegin(), arc.getAngleEnd()));
            }
            LOGGER.log(Level.INFO, "");
        }
    }

    /**
     * Prints resulting list of arcs ({@link Arc2D} list) to console.
     *
     * @param circleWithArcsList list of circles that consists list of intersections (arcs).
     */
    public static void printArcs2DAngles(List<CircleWithArcs> circleWithArcsList) {
        LOGGER.log(Level.INFO, "All Arc2D for all circles: ");
        for (int i = 0; i < circleWithArcsList.size(); i++) {
            LOGGER.log(Level.INFO, format("%d:  ", i));
            for (Arc2D arc2D : circleWithArcsList.get(i).getArcs2D()) {
                LOGGER.log(Level.INFO, format("[%.2f:%.2f] ", arc2D.getAngleStart(), arc2D.getAngleExtent()));
            }
            LOGGER.log(Level.INFO, "");
        }
    }
}
