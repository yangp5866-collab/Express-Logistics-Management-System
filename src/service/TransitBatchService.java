// Subsystem owned by student #2

package service;

import dao.DataManager;
import model.TransitBatch;
import model.Waybill;

/**
 * Transit batch service layer
 */
public class TransitBatchService {

    /**
     * Create a transit batch
     */
    public TransitBatch createTransitBatch(String batchId, String origin, String dest) {
        TransitBatch batch = new TransitBatch(batchId, origin, dest);
        DataManager.batchList.add(batch);
        return batch;
    }

    /**
     * Add a waybill to a batch
     */
    public boolean addWaybillToBatch(String batchId, String waybillId) {
        TransitBatch batch = findBatchById(batchId);
        if (batch == null) {
            System.out.println("Batch does not exist.");
            return false;
        }

        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) {
            System.out.println("Waybill does not exist.");
            return false;
        }

        batch.addWaybill(waybill);
        System.out.println("Waybill " + waybillId + " has been added to the batch.");
        return true;
    }

    /**
     * Update transit batch status
     */
    public boolean updateBatchStatus(String batchId, String status) {
        for (TransitBatch b : DataManager.batchList) {
            if (b.getBatchId().equals(batchId)) {
                b.updateBatchStatus(status);
                System.out.println("Batch status updated: " + b.getTransitStatus());
                return true;
            }
        }
        System.out.println("Batch does not exist!");
        return false;
    }

    private TransitBatch findBatchById(String batchId) {
        for (TransitBatch b : DataManager.batchList) {
            if (b.getBatchId().equals(batchId)) return b;
        }
        return null;
    }

    private Waybill findWaybill(String waybillId) {
        for (Waybill w : DataManager.waybillList) {
            if (w.getWaybillId().equals(waybillId)) return w;
        }
        return null;
    }
}
