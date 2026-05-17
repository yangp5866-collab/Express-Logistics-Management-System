// Subsystem owned by student #2

package state;

import model.Waybill;

/**
 * Task 3: Subclass implementation - in-transit state
 */
public class InTransitState extends BaseState {
    @Override
    public String getStateName() {
        return "IN_TRANSIT";
    }

    @Override
    public void handleScan(Waybill waybill, String nodeId, String scanType) {
        super.handleScan(waybill, nodeId, scanType);
        // After check-in, switch to arrived-at-node state
        waybill.setState(new ArrivedAtNodeState());
    }
}
