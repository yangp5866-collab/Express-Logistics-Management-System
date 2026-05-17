// Subsystem owned by student #2

package service;

import dao.DataManager;
import model.LogisticsNode;
import model.RoutePlan;
import model.ScanRecord;
import model.Waybill;

/**
 * Waybill service layer
 */
public class WaybillService {

    /**
     * Waybill check-in scan
     */
    public boolean scanCheckIn(String waybillId, String nodeId) {
        // Check whether the node exists
        LogisticsNode node = DataManager.findNodeById(nodeId);
        if (node == null) {
            System.out.println("Node does not exist. Check-in failed!");
            return false;
        }

        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) {
            waybill = new Waybill(waybillId, new RoutePlan("ROUTE" + waybillId));
            DataManager.waybillList.add(waybill);
        }
        waybill.processScan(nodeId, "CHECK_IN");
        System.out.println("Waybill checked in successfully. Current state: " + waybill.getCurrentState().getStateName());
        return true;
    }

    /**
     * Waybill check-out scan
     */
    public boolean scanCheckOut(String waybillId, String nodeId) {
        // Check whether the node exists
        LogisticsNode node = DataManager.findNodeById(nodeId);
        if (node == null) {
            System.out.println("Node does not exist. Check-out failed!");
            return false;
        }

        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) {
            System.out.println("Waybill does not exist!");
            return false;
        }

        // Check node ID consistency
        if (waybill.getCurrentLocationId() != null && !waybill.getCurrentLocationId().equals(nodeId)) {
            System.out.println("Node ID mismatch: this waybill has already checked in at node " + waybill.getCurrentLocationId()
                    + ". The check-out node must be the same node!");
            return false;
        }

        waybill.processScan(nodeId, "CHECK_OUT");
        System.out.println("Waybill checked out successfully. Current state: " + waybill.getCurrentState().getStateName());
        return true;
    }

    /**
     * Track waybill trajectory
     */
    public void trackWaybill(String waybillId) {
        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) {
            System.out.println("Waybill does not exist!");
            return;
        }
        System.out.println("\n===== Waybill Info =====");
        System.out.println(waybill);
        System.out.println("===== Scan Trajectory =====");
        for (ScanRecord record : waybill.getTrajectory()) {
            System.out.println(record);
        }
    }

    private Waybill findWaybill(String waybillId) {
        for (Waybill w : DataManager.waybillList) {
            if (w.getWaybillId().equals(waybillId)) return w;
        }
        return null;
    }
}
