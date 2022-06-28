package circlesintersection.presentation.drawing;

import java.awt.*;

/**
 * Draws a time taken to compute and draw one frame.
 */
public class FrameTimeDrawingHelper {

    private final FrameTimeCounter mFrameTimeCounter;
    private static FrameTimeDrawingHelper sFrameDrawingHelper;

    /**
     * Private constructor
     *
     * @param frameTimeCounter counter of time spent for one frame.
     */
    private FrameTimeDrawingHelper(FrameTimeCounter frameTimeCounter) {
        mFrameTimeCounter = frameTimeCounter;
    }

    /**
     * @param frameTimeCounter counter of time spent for one frame.
     * @return Retrieve instance of this class.
     */
    public static FrameTimeDrawingHelper getInstance(FrameTimeCounter frameTimeCounter) {
        if (sFrameDrawingHelper == null) {
            sFrameDrawingHelper = new FrameTimeDrawingHelper(frameTimeCounter);
        }
        return sFrameDrawingHelper;
    }

    /**
     * Draw time taken to repaint a canvas
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void drawFrameDrawingTime(Graphics2D g2d) {
        g2d.setPaint(Color.GRAY);
        // Time taken in nanoseconds to draw a frame.
        mFrameTimeCounter.setTimeEnd(System.nanoTime() - mFrameTimeCounter.getTimeBegin());
        g2d.drawString("Frame render time: " + mFrameTimeCounter.getTimeEnd() / 1_000_000
                        + "."
                        + Long.toString(mFrameTimeCounter.getTimeEnd() / 10_000).substring(1)
                        + " milliseconds",
                10,
                40);
    }
}
