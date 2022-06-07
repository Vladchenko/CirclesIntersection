package circlesintersection;

import circlesintersection.listeners.UiUpdateListener;
import circlesintersection.models.AnglePair;
import circlesintersection.models.Arc;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import static circlesintersection.GeometryUtils.*;
import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Component that draws the arcs (circles).
 */
public class PaintComponent extends JPanel implements UiUpdateListener {

    private Arcs arcs;
    private final Settings settings;
    private final static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    /**
     * Public constructor.
     *
     * @param arcs  an arcs to be drawn on a canvas.
     * @param settings different values to tune the program.
     */
    public PaintComponent(Arcs arcs, Settings settings) {
        this.arcs = arcs;
        this.settings = settings;
        arcs.computeIntersections();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.setBackground(Color.BLACK);
        // Erasing a previous drawing
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Changing appearance for a measuring lines
        float[] dash1 = {4.0f, 3.0f};
        g2.setStroke(new BasicStroke(2,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                2, dash1, 0.0f));

        g2.setColor(settings.getFadedArcsColor());

        if (settings.isAntiAliasingEnabled()) {
            // Smooths the picture, but slows down drawing a lot
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            drawCircle(g2, arcs.getArcsArray()[i], settings.getArcsColor(), settings.getFadedArcsColor());
            if (DEBUG_ENABLED) {
                if (i < 10) {
                    g2.drawString(Integer.toString(i), (int) arcs.getArcsArray()[i].getX() - 3, (int) arcs.getArcsArray()[i].getY() - 7);
                } else {
                    g2.drawString(Integer.toString(i), (int) arcs.getArcsArray()[i].getX() - 6, (int) arcs.getArcsArray()[i].getY() - 7);
                }
            }
        }
        // Drawing a last circle with a different colors, since it is the one taking a mouse coordinates for its position.
        drawCircle(g2, arcs.getArcsArray()[arcs.getArcsArray().length - 1], settings.getSubjectCircleColor(), settings.getFadedSubjectCircleColor());

        g2.setStroke(       //new BasicStroke(2));
                new BasicStroke(3,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        2, null, 0.0f));
        g2.setColor(settings.getArcsColor());

        // Drawing an arcs for a not intersected areas of circles
        if (settings.getDrawKind().equals(DrawKind.arcs)
                || settings.getDrawKind().equals(DrawKind.both)) {
            for (int i = 0; i < arcs.getAnglePairsListFinal().size(); i++) {
                if ((arcs.getAnglePairsListFinal().get(i)).getNumber() == arcs.getArcsArray().length - 1) {
                    g2.setColor(settings.getSubjectCircleColor());
                }
                g2.draw(createArc2D(arcs.getAnglePairsListFinal().get(i)));
            }
        }

        // Time taken in nanoseconds to draw a frame.
        settings.setTimeTemp(System.nanoTime() - settings.getTimeBegin());
        g2.setPaint(Color.GRAY);
        g2.drawString("Frame render time: " + settings.getTimeTemp() / 1_000_000
                        + "."
                        + Long.toString(settings.getTimeTemp() / 10_000).substring(1)
                        + " milliseconds",
                10,
                40);
    }

    private void drawCircle(Graphics2D g2, Arc arc, Color mainColor, Color fadedColor) {
        if (settings.isGradientEnabled()) {
            RadialGradientPaint gradientPaint = new RadialGradientPaint(
                    (float) arc.getX(),
                    (float) arc.getY(),
                    (float) (arc.getDiameter() / 2),
                    new float[]{(float) 0.0, (float) 1.0},
                    new Color[]{fadedColor, new Color(0, 0, 0, 0)});
            g2.setPaint(gradientPaint);
            // Drawing a circled gradient at the center за circle
            g2.fillOval((int) arc.getX() - (int) arc.getDiameter() / 2,
                    (int) arc.getY() - (int) arc.getDiameter() / 2,
                    (int) arc.getDiameter(), (int) arc.getDiameter());
        }
        if (settings.getDrawKind().equals(DrawKind.circles)
                || settings.getDrawKind().equals(DrawKind.both)) {
            g2.setPaint(fadedColor);
            // Drawing an intersected arc of circle
            // 1) Since both the diameters (x and y ) match, lets use oval instead of circle
            // 2) CPU cost for drawing an arc is more than drawing a circle, this is why here we draw a circle.
            // Anyway, a not intersected arc will be drawn later in a code and, overdraw the needed circle area.
            g2.drawOval((int) arc.getX() - (int) arc.getDiameter() / 2,
                    (int) arc.getY() - (int) arc.getDiameter() / 2,
                    (int) arc.getDiameter(), (int) arc.getDiameter());
        }
        g2.setPaint(mainColor);
        // Drawing a center of a circle.
        g2.drawOval((int) arc.getX(), (int) arc.getY(), 1, 1);
    }

    @Override
    public void createNewArcsAndRepaint() {
        settings.setTimeBegin(System.nanoTime());
        arcs = new Arcs(settings);
        arcs.computeIntersections();
        repaint();
    }

    @Override
    public void updateArcsAndRepaint() {
        settings.setTimeBegin(System.nanoTime());
        repaint();
    }

    @Override
    public void updateArcsAndRepaint(Point point) {
        settings.setTimeBegin(System.nanoTime());
        arcs.setAnglePairsListArray(new ArrayList<>(settings.getCirclesQuantity()));
        arcs.setAnglePairsListFinal(new ArrayList<>());
        arcs.setAnglePairsList(new ArrayList<>());
        SwingUtilities.convertPointToScreen(point, this);
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setX(point.getX());
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setY(point.getY());
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
        }
        arcs.computeIntersections();
        repaint();
    }

    @Override
    public void updateArcsAndRepaint(Point point, int wheelRotationValue) {
        settings.setTimeBegin(System.nanoTime());
        arcs.setAnglePairsListArray(new ArrayList<>(settings.getCirclesQuantity()));
        arcs.setAnglePairsListFinal(new ArrayList<>());
        arcs.setAnglePairsList(new ArrayList<>());
        SwingUtilities.convertPointToScreen(point, this);
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setX(point.getX());
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setY(point.getY());
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
        }
        if (wheelRotationValue < 0
                && arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() > 20) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setDiameter(
                    arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() - 10);
        }
        if (wheelRotationValue > 0) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setDiameter(
                    arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() + 10);
        }
        arcs.computeIntersections();
        repaint();
    }

    @Override
    public void rotateArcsAndRepaint(int wheelRotation) {
        settings.setTimeBegin(System.nanoTime());
        double newAngle;
        double extraAngle;
        double distance;
        arcs.prepareArcsWhenCirclesRotated();
        if (wheelRotation > 0) {
            extraAngle = 0.04;
        } else {
            extraAngle = -0.04;
        }
        for (int i = 0; i < arcs.getArcsArray().length - 1; i++) {
            newAngle = computeAngle(arcs.getArcsArray()[arcs.getArcsArray().length - 1], arcs.getArcsArray()[i], AngleKind.RAD) + extraAngle;
            distance = computeDistance(arcs.getArcsArray()[i], arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    arcs.getArcsArray()[i],
                    arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
        }
        arcs.computeIntersections();
        repaint();
    }

    @Override
    public void scaleArcsAndRepaint(int wheelRotation) {
        settings.setTimeBegin(System.nanoTime());
        double newAngle;
        double distance;
        arcs.prepareArcsWhenCirclesRotated();
        for (int i = 0; i < arcs.getArcsArray().length - 1; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
            newAngle = computeAngle(arcs.getArcsArray()[arcs.getArcsArray().length - 1], arcs.getArcsArray()[i], AngleKind.RAD);
            distance = computeDistance(arcs.getArcsArray()[i], arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
            if (wheelRotation > 0) {
                distance = distance + distance/15;
            } else {
                distance = distance - distance/15;
            }
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    arcs.getArcsArray()[i],
                    arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
        }
        arcs.computeIntersections();
        repaint();
    }

    @Override
    public void toggleFullScreen() {
        settings.setTimeBegin(System.nanoTime());
        JFrame frame = (JFrame)SwingUtilities.getRoot(this);
        if (device.getFullScreenWindow() == null) {  // Go to full screen mode
            frame.dispose();
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
            frame.setVisible(true);
        } else { // back to windowed mode
            frame.dispose();
            frame.setUndecorated(true);
            device.setFullScreenWindow(null);
            frame.setVisible(true);
        }
    }

    private Arc2D createArc2D(AnglePair anglePair) {
        return new Arc2D.Double(
                arcs.getArcsArray()[anglePair.getNumber()].getX()
                        - arcs.getArcsArray()[anglePair.getNumber()].getDiameter() / 2,
                arcs.getArcsArray()[anglePair.getNumber()].getY()
                        - arcs.getArcsArray()[anglePair.getNumber()].getDiameter() / 2,
                arcs.getArcsArray()[anglePair.getNumber()].getDiameter(),
                arcs.getArcsArray()[anglePair.getNumber()].getDiameter(),
                anglePair.getAngleBegin(),
                (anglePair.getAngleEnd()
                        - anglePair.getAngleBegin()),
                Arc2D.OPEN);
    }
}
