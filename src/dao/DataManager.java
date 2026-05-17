// Subsystem owned by student #2

package dao;

import model.LogisticsNode;
import model.TransitBatch;
import model.Waybill;

import java.util.ArrayList;
import java.util.List;

/**
 * Task 2: New class - in-memory data manager (replaces the database)
 * Purpose: centrally stores all entity data for global access
 */
public class DataManager {
    public static List<LogisticsNode> nodeList = new ArrayList<>();
    public static List<Waybill> waybillList = new ArrayList<>();
    public static List<TransitBatch> batchList = new ArrayList<>();

    public static LogisticsNode findNodeById(String nodeId) {
        if (nodeId == null) return null;
        for (LogisticsNode node : nodeList) {
            if (node != null && nodeId.equalsIgnoreCase(node.getNodeId())) {
                return node;
            }
        }
        return null;
    }

    // Seed test data
    static {
        nodeList.add(new LogisticsNode("NODE001", "Beijing Distribution Center", "TRANSIT", 1000));
        nodeList.add(new LogisticsNode("NODE002", "Shanghai Distribution Center", "TRANSIT", 800));
    }
}
