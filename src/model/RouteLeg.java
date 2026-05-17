// Subsystem owned by student #2

package model;

/**
 * Route leg entity
 */
public class RouteLeg {
    private int sequenceIndex;
    private int estimatedTransitHours;
    private LogisticsNode stopNode;

    public RouteLeg(int sequenceIndex, int estimatedTransitHours, LogisticsNode stopNode) {
        this.sequenceIndex = sequenceIndex;
        this.estimatedTransitHours = estimatedTransitHours;
        this.stopNode = stopNode;
    }

    // Getter
    public int getSequenceIndex() { return sequenceIndex; }
    public int getEstimatedTransitHours() { return estimatedTransitHours; }
    public LogisticsNode getStopNode() { return stopNode; }
}
