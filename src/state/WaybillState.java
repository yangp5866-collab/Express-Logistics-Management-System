// Subsystem owned by student #2

package state;

import model.Waybill;

/**
 * Task 3: Interface definition - contract for waybill state behaviors
 */
public interface WaybillState {
    void handleScan(Waybill waybill, String nodeId, String scanType);
    String getStateName();
}
