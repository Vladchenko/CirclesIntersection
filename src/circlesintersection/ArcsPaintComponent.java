package circlesintersection;

import circlesintersection.listeners.UiUpdateListener;
import circlesintersection.models.AnglePair;
import circlesintersection.models.Arc;
import circlesintersection.models.Arcs;
import circlesintersection.models.DrawKind;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Component that draws the arcs (circles).
 */
public class ArcsPaintComponent extends JPanel implements UiUpdateListener {

    private Arcs mArcs;
    private final Settings mSettings;
    private final static GraphicsDevice GRAPHICS_DEVICE
            = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    /**
     * Public constructor.
     *
     * @param arcs     an arcs to be drawn on a canvas.
     * @param settings different values to tune the program.
     */
    public ArcsPaintComponent(Arcs arcs, Settings settings) {
        mArcs = arcs;
        mSettings = settings;
        arcs.computeIntersections();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.setBackground(Color.BLACK);
        // Erasing a previous drawing
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Changing appearance for a measuring lines
        float[] dash1 = {2.0f, 4.0f};
        g2.setStroke(new BasicStroke(2,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                2, dash1, 0.0f));

        g2.setColor(mSettings.getFadedArcsColor());

        if (mSettings.isAntiAliasingEnabled()) {
            // Smooths the picture, but slows down drawing a lot
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        for (int i = 0; i < mArcs.getArcsArray().length; i++) {
            drawCircle(g2, mArcs.getArcsArray()[i], mSettings.getArcsColor(), mSettings.getFadedArcsColor());
            if (DEBUG_ENABLED) {
                if (i < 10) {
                    g2.drawString(Integer.toString(i), (int) mArcs.getArcsArray()[i].getX() - 3, (int) mArcs.getArcsArray()[i].getY() - 7);
                } else {
                    g2.drawString(Integer.toString(i), (int) mArcs.getArcsArray()[i].getX() - 6, (int) mArcs.getArcsArray()[i].getY() - 7);
                }
            }
        }
        // Drawing a last circle with a different colors, since it is the one taking a mouse coordinates for its position.
        drawCircle(g2, mArcs.getArcsArray()[mArcs.getArcsArray().length - 1], mSettings.getSubjectCircleColor(), mSettings.getFadedSubjectCircleColor());

        g2.setStroke(       //new BasicStroke(2));
                new BasicStroke(4,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        2, null, 2.0f));
        g2.setColor(mSettings.getArcsColor());

        // Drawing an arcs for a not intersected areas of circles
        if (mSettings.getDrawKind().equals(DrawKind.ARCS)
                || mSettings.getDrawKind().equals(DrawKind.BOTH)) {
            for (int i = 0; i < mArcs.getAnglePairsListFinal().size(); i++) {
                if ((mArcs.getAnglePairsListFinal().get(i)).getNumber() == mArcs.getArcsArray().length - 1) {
                    g2.setColor(mSettings.getSubjectCircleColor());
                }
                g2.draw(createArc2D(mArcs.getAnglePairsListFinal().get(i)));
            }
        }

        // Time taken in nanoseconds to draw a frame.
        mSettings.setTimeTemp(System.nanoTime() - mSettings.getTimeBegin());
        g2.setPaint(Color.GRAY);
        g2.drawString("Frame render time: " + mSettings.getTimeTemp() / 1_000_000
                        + "."
                        + Long.toString(mSettings.getTimeTemp() / 10_000).substring(1)
                        + " milliseconds",
                10,
                40);
    }

    @Override
    public void updateArcsAndRepaint(Arcs arcs) {
        this.mArcs = arcs;
        repaint();
    }

    @Override
    public void toggleFullScreen() {
        mSettings.setTimeBegin(System.nanoTime());
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        if (GRAPHICS_DEVICE.getFullScreenWindow() == null) {  // Go to full screen mode
            frame.dispose();    // When this line of code is moved outside of if, full screen toggling doesn't work
            frame.setUndecorated(true);
            GRAPHICS_DEVICE.setFullScreenWindow(frame);
        } else { // back to windowed mode
            frame.dispose();    // When this line of code is moved outside of if, full screen toggling doesn't work
            frame.setUndecorated(true);
            GRAPHICS_DEVICE.setFullScreenWindow(null);
        }
        frame.setVisible(true);
    }

    private void drawCircle(Graphics2D g2, Arc arc, Color mainColor, Color fadedColor) {
        if (mSettings.isGradientEnabled()) {
            RadialGradientPaint gradientPaint = new RadialGradientPaint(
                    (float) arc.getX() - (int) mArcs.getMouseDraggedDeltaX(),
                    (float) arc.getY() - (int) mArcs.getMouseDraggedDeltaY(),
                    (float) (arc.getDiameter() / 2),
                    new float[]{(float) 0.0, (float) 1.0},
                    new Color[]{fadedColor, new Color(0, 0, 0, 0)});
            g2.setPaint(gradientPaint);
            // Drawing a circled gradient at the center of circle
            g2.fillOval((int) arc.getX() - (int) mArcs.getMouseDraggedDeltaX() - (int) arc.getDiameter() / 2,
                    (int) arc.getY() - (int) mArcs.getMouseDraggedDeltaY() - (int) arc.getDiameter() / 2,
                    (int) arc.getDiameter(), (int) arc.getDiameter());
        }
        if (mSettings.getDrawKind().equals(DrawKind.CIRCLES)
                || mSettings.getDrawKind().equals(DrawKind.BOTH)) {
            g2.setPaint(fadedColor);
            // Drawing an intersected arc of circle
            // 1) Since both the diameters (x and y ) match, lets use oval instead of circle
            // 2) CPU cost for drawing an arc is more than drawing a circle, this is why here we draw a circle.
            // Anyway, a not intersected arc will be drawn later in a code and, overdraw the needed circle area.
            g2.drawOval((int) arc.getX() - (int) mArcs.getMouseDraggedDeltaX() - (int) arc.getDiameter() / 2,
                    (int) arc.getY() - (int) mArcs.getMouseDraggedDeltaY() - (int) arc.getDiameter() / 2,
                    (int) arc.getDiameter(), (int) arc.getDiameter());
        }
        g2.setPaint(mainColor);
        // Drawing a center of a circle.
        g2.drawOval((int) arc.getX() - (int) mArcs.getMouseDraggedDeltaX(),
                (int) arc.getY() - (int) mArcs.getMouseDraggedDeltaY(), 1, 1);
    }

    private Arc2D createArc2D(AnglePair anglePair) {
        return new Arc2D.Double(
                mArcs.getArcsArray()[anglePair.getNumber()].getX()
                        - mArcs.getMouseDraggedDeltaX()
                        - mArcs.getArcsArray()[anglePair.getNumber()].getDiameter() / 2,
                mArcs.getArcsArray()[anglePair.getNumber()].getY()
                        - mArcs.getMouseDraggedDeltaY()
                        - mArcs.getArcsArray()[anglePair.getNumber()].getDiameter() / 2,
                mArcs.getArcsArray()[anglePair.getNumber()].getDiameter(),
                mArcs.getArcsArray()[anglePair.getNumber()].getDiameter(),
                anglePair.getAngleBegin(),
                (anglePair.getAngleEnd()
                        - anglePair.getAngleBegin()),
                Arc2D.OPEN);
    }
}
