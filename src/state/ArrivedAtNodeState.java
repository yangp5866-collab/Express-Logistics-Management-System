// Subsystem owned by student #2

package state;

import model.Waybill;

/**
 * Task 3: Subclass implementation - arrived-at-node state
 */
public class ArrivedAtNodeState extends BaseState {
    @Override
    public String getStateName() {
        return "ARRIVED_AT_NODE";
    }

    @Override
    public void handleScan(Waybill waybill, String nodeId, String scanType) {
        super.handleScan(waybill, nodeId, scanType);
        // After check-out, switch to in-transit state
        waybill.setState(new InTransitState());
    }
}
