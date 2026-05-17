// Subsystem owned by student #2

package service;

import dao.DataManager;
import model.LogisticsNode;

import java.util.List;

/**
 * Logistics node service layer
 */
public class LogisticsNodeService {

    /**
     * Add a logistics node
     */
    public boolean addNode(String nodeId, String nodeName, String locationType, int capacity) {
        if (nodeId == null || nodeId.isEmpty()) {
            System.out.println("Node ID cannot be empty. Add failed!");
            return false;
        }
        if (findNodeById(nodeId) != null) {
            System.out.println("Node ID already exists. Add failed!");
            return false;
        }
        if (capacity < 100) {
            System.out.println("Capacity must be at least 100. Add failed!");
            return false;
        }
        DataManager.nodeList.add(new LogisticsNode(nodeId, nodeName, locationType, capacity));
        System.out.println("Node added successfully!");
        return true;
    }

    /**
     * Update node status
     */
    public boolean updateNodeStatus(String nodeId, String status) {
        for (LogisticsNode node : DataManager.nodeList) {
            if (node.getNodeId().equals(nodeId)) {
                node.setOperationalStatus(status);
                System.out.println("Node status updated successfully!");
                return true;
            }
        }
        System.out.println("Node does not exist!");
        return false;
    }

    /**
     * Get all nodes
     */
    public List<LogisticsNode> getAllNodes() {
        return DataManager.nodeList;
    }

    /**
     * Find node by ID
     */
    public LogisticsNode findNodeById(String nodeId) {
        return DataManager.findNodeById(nodeId);
    }
}
