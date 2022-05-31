package circlesintersection.listeners;

/**
 * TODO Presumed to be a listener to notify JFrame to update its JLabels, but the realization seems bad - classes have
 * interdependence. Architecture has to be refined.
 */
public interface DataChangeListener {
    /**
     * TODO
     *
     * @param fps
     * @param timeTaken
     */
    void updateLabelsData(String fps, String timeTaken);
}
