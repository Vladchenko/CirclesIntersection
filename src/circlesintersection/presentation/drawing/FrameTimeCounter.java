package circlesintersection.presentation.drawing;

/**
 * TODO
 */
public class FrameTimeCounter {

    // Time stamp of the moment of the beginning of a rendering
    private long mTimeBegin;
    // Time stamp of the moment of the ending of a rendering
    private long mTimeEnd;

    public long getTimeBegin() {
        return mTimeBegin;
    }

    public void setTimeBegin(long timeBegin) {
        this.mTimeBegin = timeBegin;
    }

    public long getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.mTimeEnd = timeEnd;
    }
}
