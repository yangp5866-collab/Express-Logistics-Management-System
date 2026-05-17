// Subsystem owned by student #2

package state;

import model.ScanRecord;
import model.Waybill;

/**
 * Task 3: Abstract base class - implements the interface and provides shared methods for subclasses
 */
public abstract class BaseState implements WaybillState {
    @Override
    public void handleScan(Waybill waybill, String nodeId, String scanType) {
        // Common scan log handling
        waybill.addScanRecord(new ScanRecord(
                "SCAN" + System.currentTimeMillis(),
                scanType,
                "OPERATOR"
        ));
        waybill.setCurrentLocationId(nodeId);
    }
}
