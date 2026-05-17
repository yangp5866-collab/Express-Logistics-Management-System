// Subsystem owned by student #2

package model;

import java.util.Date;

/**
 * Scan record entity
 */
public class ScanRecord {
    private String scanId;
    private Date timestamp;
    private String scanType;
    private String operatorId;

    public ScanRecord(String scanId, String scanType, String operatorId) {
        this.scanId = scanId;
        this.timestamp = new Date();
        this.scanType = scanType;
        this.operatorId = operatorId;
    }
    
    public String getOperatorId() { return operatorId; }

    @Override
    public String toString() {
        return "Scan ID=" + scanId + ", Time=" + timestamp + ", Type=" + scanType;
    }
}
