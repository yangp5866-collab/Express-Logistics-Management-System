// Subsystem owned by student #2

package model;

/**
 * Logistics node entity
 */
public class LogisticsNode {
    private String nodeId;
    private String nodeName;
    private String locationType;
    private String operationalStatus;
    private int maxCapacity;
    private int currentWorkload;

    public LogisticsNode(String nodeId, String nodeName, String locationType, int maxCapacity) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.locationType = locationType;
        this.maxCapacity = maxCapacity;
        this.operationalStatus = "ACTIVE";
        this.currentWorkload = 0;
    }

    // Getter & Setter
    public String getNodeId() { return nodeId; }
    public String getNodeName() { return nodeName; }
    public String getLocationType() { return locationType; }
    public String getOperationalStatus() { return operationalStatus; }
    public void setOperationalStatus(String operationalStatus) { this.operationalStatus = operationalStatus; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getCurrentWorkload() { return currentWorkload; }
    public void setCurrentWorkload(int currentWorkload) { this.currentWorkload = currentWorkload; }

    @Override
    public String toString() {
        return "Node ID=" + nodeId + ", Name=" + nodeName + ", Status=" + operationalStatus +
                ", Capacity=" + maxCapacity + ", Current workload=" + currentWorkload;
    }
}
