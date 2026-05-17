// Subsystem owned by student #2

package model;

import state.InTransitState;
import state.WaybillState;

import java.util.ArrayList;
import java.util.List;

/**
 * Waybill entity
 */
public class Waybill {
    private String waybillId;
    private String currentLocationId;
    private WaybillState currentState;
    private RoutePlan routePlan;
    private List<ScanRecord> trajectory;

    public Waybill(String waybillId, RoutePlan initialRoute) {
        this.waybillId = waybillId;
        this.routePlan = initialRoute;
        this.trajectory = new ArrayList<>();
        this.currentState = new InTransitState(); // Initial state: in transit
    }

    // Core business method
    public void processScan(String nodeId, String scanType) {
        currentState.handleScan(this, nodeId, scanType);
    }

    public void addScanRecord(ScanRecord scanRecord) {
        trajectory.add(scanRecord);
    }

    // Getter & Setter
    public String getWaybillId() { return waybillId; }
    public String getCurrentLocationId() { return currentLocationId; }
    public void setCurrentLocationId(String currentLocationId) { this.currentLocationId = currentLocationId; }
    public WaybillState getCurrentState() { return currentState; }
    public void setState(WaybillState currentState) { this.currentState = currentState; }
    public RoutePlan getRoutePlan() { return routePlan; }
    public void setRoutePlan(RoutePlan routePlan) {this.routePlan = routePlan; }
    public List<ScanRecord> getTrajectory() { return trajectory; }

    @Override
    public String toString() {
        return "Waybill ID=" + waybillId + ", Current location=" + currentLocationId +
                ", Current state=" + currentState.getStateName();
    }
}
