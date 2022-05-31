package circlesintersection;

import circlesintersection.listeners.UIUpdateCallbacks;
import circlesintersection.models.AnglePair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * TODO
 */
public class PaintComponent extends JPanel implements UIUpdateCallbacks {

    private Arcs arcs;
    private final Settings settings;

    /**
     * TODO
     *
     * @param arcs
     * @param settings different values to tune the program.
     */
    public PaintComponent(Arcs arcs, Settings settings) {
        this.arcs = arcs;
        this.settings = settings;
        arcs.runRendering();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.setBackground(Color.BLACK);
//        super.paintComponent(g);  // Seems not needed
        Graphics2D g2 = (Graphics2D) g;

        //** Changing appearance for a measuring lines
        float[] dash1 = {4.0f, 3.0f};
        g2.setStroke(new BasicStroke(2,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                2, dash1, 0.0f));

        g2.setColor(settings.getFadedArcsColor());

        // Smooths the picture, but slows down drawing a lot
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (settings.getDrawKind().equals(DrawKind.circles)
                || settings.getDrawKind().equals(DrawKind.both)) {
            for (int i = 0; i < arcs.getArcsArray().length; i++) {
                //** If an item is the last in an array ...
                if (i == arcs.getArcsArray().length - 1) {
                    //** ... make its color to be
                    g2.setColor(settings.getFadedSubjectCircleColor());
                }
                // 1) Since both the diameters (x and y ) match, oval stands for circle
                // 2) CPU cost for drawing an arc is more than drawing a circle, this is why here we draw a circle.
                // Anyway, a not intersected arc will be drawn later in a code and, overdraw the needed circle area.
                g2.drawOval((int) arcs.getArcsArray()[i].getX() - (int) arcs.getArcsArray()[i].getDiameter() / 2,
                        (int) arcs.getArcsArray()[i].getY() - (int) arcs.getArcsArray()[i].getDiameter() / 2,
                        (int) arcs.getArcsArray()[i].getDiameter(), (int) arcs.getArcsArray()[i].getDiameter());
                // Drawing a center of a circle.
                g2.drawOval((int) arcs.getArcsArray()[i].getX(), (int) arcs.getArcsArray()[i].getY(),
                        1,1);
                if (Settings.getDEBUG()) {
                    if (i < 10) {
                        g2.drawString(Integer.toString(i), (int) arcs.getArcsArray()[i].getX() - 3, (int) arcs.getArcsArray()[i].getY() - 7);
                    } else {
                        g2.drawString(Integer.toString(i), (int) arcs.getArcsArray()[i].getX() - 6, (int) arcs.getArcsArray()[i].getY() - 7);
                    }
                }
            }
        }

        g2.setStroke(       //new BasicStroke(2));
        new BasicStroke(3,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                2, null, 0.0f));
        g2.setColor(settings.getArcsColor());

        //** Drawing an arcs for a not intersected areas of circles */
        if (settings.getDrawKind().equals(DrawKind.arcs)
                || settings.getDrawKind().equals(DrawKind.both)) {
            for (int i = 0; i < arcs.getAnglePairsListFinal().size(); i++) {
                if ((arcs.getAnglePairsListFinal().get(i)).getNumber() == arcs.getArcsArray().length - 1) {
                    g2.setColor(settings.getSubjectCircleColor());
                }
                g2.draw(createArc2D(arcs.getAnglePairsListFinal().get(i)));
            }
        }
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
        arcs.runRendering();

        //* Time taken in nanoseconds to run a rendering.
        settings.setTimeTemp(System.nanoTime() - settings.getTimeBegin());
        String timeSpent = settings.getTimeTemp() / 1_000_000
                + "."
                + Long.toString(settings.getTimeTemp() / 1_000).substring(1);
        repaint();
//        timeSpentLabel.setText(timeSpent);    //TODO Get an access to these labels somehow
//        fpsLabel.setText(fps);
    }

    @Override
    public void updateArcsAndRepaint(MouseWheelEvent event) {
        arcs.setAnglePairsListArray(new ArrayList<>(settings.getCirclesQuantity()));
        arcs.setAnglePairsListFinal(new ArrayList<>());
        arcs.setAnglePairsList(new ArrayList<>());
        SwingUtilities.convertPointToScreen(event.getPoint(), this);
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setX(event.getX());
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setY(event.getY());
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
        }
        if (event.getWheelRotation() > 0
                && arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() > 20) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setDiameter(
                    arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() - 10);
        }
        if (event.getWheelRotation() < 0) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setDiameter(
                    arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() + 10);
        }
        arcs.runRendering();
        repaint();
    }

    @Override
    public void createNewArcsAndRepaint() {
        arcs = new Arcs(settings);
        arcs.runRendering();
        repaint();
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
