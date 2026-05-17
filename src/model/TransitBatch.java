// Subsystem owned by student #2

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Transit batch entity
 */
public class TransitBatch {
    private String batchId;
    private Date creationTimestamp;
    private String originNodeId;
    private String destinationNodeId;
    private String transitStatus;
    private List<Waybill> containedWaybills;

    public TransitBatch(String batchId, String originNodeId, String destinationNodeId) {
        this.batchId = batchId;
        this.creationTimestamp = new Date();
        this.originNodeId = originNodeId;
        this.destinationNodeId = destinationNodeId;
        this.transitStatus = "CREATED";
        this.containedWaybills = new ArrayList<>();
    }

    public void addWaybill(Waybill waybill) {
        containedWaybills.add(waybill);
    }

    public void updateBatchStatus(String newStatus) {
        this.transitStatus = newStatus;
    }

    // Getter
    public String getBatchId() { return batchId; }
    public Date getCreationTimestamp() { return creationTimestamp; }
    public String getOriginNodeId() { return originNodeId; }
    public String getDestinationNodeId() { return destinationNodeId; }
    public String getTransitStatus() { return transitStatus; }
    public List<Waybill> getContainedWaybills() { return containedWaybills; }

    @Override
    public String toString() {
        return "Batch ID=" + batchId + ", Status=" + transitStatus +
                ", Contained waybills=" + containedWaybills.size();
    }
}
