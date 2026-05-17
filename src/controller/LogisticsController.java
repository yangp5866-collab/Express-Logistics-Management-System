// Subsystem owned by student #2

package controller;

import model.TransitBatch;
import service.*;
import util.InputUtil;

/**
 * Logistics system controller - handles user interaction and coordinates business workflows
 */
public class LogisticsController {

    private final LogisticsNodeService nodeService;
    private final RouteService routeService;
    private final TransitBatchService batchService;
    private final WaybillService waybillService;

    public LogisticsController() {
        this.nodeService = new LogisticsNodeService();
        this.routeService = new RouteService();
        this.batchService = new TransitBatchService();
        this.waybillService = new WaybillService();
    }

    /**
     * Display the main menu
     */
    public void showMainMenu() {
        while (true) {
            System.out.println("\n===== Waybill Logistics & Node Management System =====");
            System.out.println("1.  Add logistics node");
            System.out.println("2.  Update node status");
            System.out.println("3.  Query node information");
            System.out.println("4.  Generate initial route plan");
            System.out.println("5.  Dynamically modify transport route");
            System.out.println("6.  Create transit batch");
            System.out.println("7.  Update transit batch status");
            System.out.println("8.  Waybill check-in scan");
            System.out.println("9.  Waybill check-out scan");
            System.out.println("10. Track waybill trajectory");
            System.out.println("0.  Exit");

            int choice = InputUtil.nextInt("Enter operation number: ");

            switch (choice) {
                case 1: addNode(); break;
                case 2: updateNodeStatus(); break;
                case 3: queryNode(); break;
                case 4: generateInitialRoute(); break;
                case 5: dynamicallyModifyRoute(); break;
                case 6: createTransitBatch(); break;
                case 7: updateBatchStatus(); break;
                case 8: scanCheckIn(); break;
                case 9: scanCheckOut(); break;
                case 10: trackWaybill(); break;
                case 0: System.out.println("Exited successfully!"); return;
                default: System.out.println("Invalid input!");
            }
        }
    }

    // ===================== UC1 Add Logistics Node =====================
    private void addNode() {
        String nodeId = InputUtil.nextLine("Enter node ID: ");
        String nodeName = InputUtil.nextLine("Enter node name: ");
        System.out.println("Allowed node types: TRANSIT, HUB, STATION");
        String type = InputUtil.nextLine("Enter node type (TRANSIT/HUB/STATION): ").toUpperCase();
        if (!type.equals("TRANSIT") && !type.equals("HUB") && !type.equals("STATION")) {
            System.out.println("Invalid type.");
            return;
        }
        int capacity = InputUtil.nextInt("Enter maximum capacity (minimum 100): ");
        nodeService.addNode(nodeId, nodeName, type, capacity);
    }

    // ===================== UC2 Update Node Status =====================
    private void updateNodeStatus() {
        String nodeId = InputUtil.nextLine("Enter node ID to update: ");
        String status = InputUtil.nextLine("Enter new status (ACTIVE/MAINTENANCE/CONGESTED): ");
        nodeService.updateNodeStatus(nodeId, status);
    }

    // ===================== UC3 Query Node Information =====================
    private void queryNode() {
        System.out.println("\n===== Logistics Node List =====");
        for (var node : nodeService.getAllNodes()) {
            System.out.println(node);
        }
    }

    // ===================== UC4 Generate Initial Route Plan =====================
    private void generateInitialRoute() {
        String waybillId = InputUtil.nextLine("Enter waybill ID: ");
        int numLegs = InputUtil.nextInt("Enter number of route legs to generate (min 2, max <= number of nodes): ");
        routeService.generateInitialRoute(waybillId, numLegs);
    }

    // ===================== UC5 Dynamically Modify Transport Route =====================
    private void dynamicallyModifyRoute() {
        String waybillId = InputUtil.nextLine("Enter waybill ID: ");
        if (!routeService.canModifyRoute(waybillId)) {
            System.out.println("Waybill does not exist or has no route plan. Please generate an initial route first.");
            return;
        }

        while (true) {
            String failedNodeId = InputUtil.nextLine("Enter failed node ID to skip: ");
            boolean ok = routeService.dynamicallyModifyRoute(waybillId, failedNodeId);
            if (ok) return;
            System.out.println("Modify failed. Please re-enter the failed node ID.");
        }
    }

    // ===================== UC6 Create Transit Batch =====================
    private void createTransitBatch() {
        String batchId = InputUtil.nextLine("Enter batch ID: ");
        String origin = InputUtil.nextLine("Enter origin node ID: ");
        String dest = InputUtil.nextLine("Enter destination node ID: ");

        TransitBatch batch = batchService.createTransitBatch(batchId, origin, dest);

        while (true) {
            String wid = InputUtil.nextLine("Enter waybill ID to add (enter # to finish): ");
            if (wid.equals("#")) break;
            batchService.addWaybillToBatch(batchId, wid);
        }

        System.out.println("Transit batch created: " + batch);
    }

    // ===================== UC7 Update Transit Batch Status =====================
    private void updateBatchStatus() {
        String batchId = InputUtil.nextLine("Enter batch ID: ");
        String status = InputUtil.nextLine("Enter new status (CREATED/DEPARTED/IN_TRANSIT/ARRIVED): ");
        batchService.updateBatchStatus(batchId, status);
    }

    // ===================== UC8 Waybill Check-In Scan =====================
    private void scanCheckIn() {
        String waybillId = InputUtil.nextLine("Enter waybill ID: ");
        String nodeId = InputUtil.nextLine("Enter current node ID: ");
        waybillService.scanCheckIn(waybillId, nodeId);
    }

    // ===================== UC9 Waybill Check-Out Scan =====================
    private void scanCheckOut() {
        String waybillId = InputUtil.nextLine("Enter waybill ID: ");
        String nodeId = InputUtil.nextLine("Enter current node ID: ");
        waybillService.scanCheckOut(waybillId, nodeId);
    }

    // ===================== UC10 Track Waybill Trajectory =====================
    private void trackWaybill() {
        String waybillId = InputUtil.nextLine("Enter waybill ID: ");
        waybillService.trackWaybill(waybillId);
    }
}
