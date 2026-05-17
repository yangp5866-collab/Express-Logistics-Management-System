package test;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.DataManager;
import model.LogisticsNode;
import model.RouteLeg;
import model.RoutePlan;
import model.TransitBatch;
import model.Waybill;
import service.LogisticsNodeService;
import service.RouteService;
import service.TransitBatchService;
import service.WaybillService;

import java.util.List;

public class LogisticsSubsystemTest {
	private LogisticsNodeService nodeService;
    private TransitBatchService batchService;
    private WaybillService waybillService;
    private RouteService routeService;

    // Lifecycle Annotations: Prepares the environment before each test method runs to guarantee independence [cite: 102, 104]
    @BeforeEach
    public void setUp() {
        nodeService = new LogisticsNodeService();
        batchService = new TransitBatchService();
        waybillService = new WaybillService();
        routeService = new RouteService();
        
        // Clear static memory to ensure each test case is independent of execution order [cite: 53, 55]
        DataManager.nodeList.clear();
        DataManager.waybillList.clear();
        DataManager.batchList.clear();
    }

    // Lifecycle Annotations: Performs cleanup after each test [cite: 111]
    @AfterEach
    public void tearDown() {
        DataManager.nodeList.clear();
        DataManager.waybillList.clear();
        DataManager.batchList.clear();
    }

    @Test
    public void testNodeBoundaryAndAddition() {
        // Test for boundary conditions (exactly 100) and general success [cite: 66, 67]
        boolean validResult = nodeService.addNode("N1", "Distribution Hub", "HUB", 100);
        assertTrue(validResult, "Adding a node with exactly 100 capacity should succeed."); // Boolean Assertion [cite: 121]
        
        boolean invalidResult = nodeService.addNode("N2", "Small Station", "STATION", 99);
        assertFalse(invalidResult, "Adding a node with < 100 capacity should fail."); // Boolean Assertion [cite: 121]
        
        assertEquals(1, DataManager.nodeList.size(), "Only one valid node should exist in DataManager."); // Equality Assertion [cite: 119]
    }

    @Test
    public void testNodeRetrievalAndIdentity() {
        nodeService.addNode("N1", "Melbourne Hub", "HUB", 500);
        LogisticsNode fetchedNode = nodeService.findNodeById("N1");
        LogisticsNode missingNode = nodeService.findNodeById("INVALID");
        
        assertNotNull(fetchedNode, "Fetched node should exist in memory."); // Null Reference [cite: 126]
        assertNull(missingNode, "Fetching an invalid node ID should return null."); // Null Reference [cite: 126]
        assertSame(DataManager.nodeList.get(0), fetchedNode, "References must point to the exact same object instance."); // Reference Identity [cite: 128]
    }

    @Test
    public void testNodeListArrayEquality() {
        nodeService.addNode("N1", "Sydney Hub", "HUB", 500);
        nodeService.addNode("N2", "Brisbane Station", "STATION", 200);
        
        List<LogisticsNode> nodes = nodeService.getAllNodes();
        LogisticsNode[] expectedArray = { DataManager.nodeList.get(0), DataManager.nodeList.get(1) };
        LogisticsNode[] actualArray = nodes.toArray(new LogisticsNode[0]);
        
        // Compares elements of two arrays to verify content equality [cite: 130]
        assertArrayEquals(expectedArray, actualArray, "The generated array should match the DataManager array exactly."); 
    }

    @Test
    public void testWaybillStateTransitions() {
        nodeService.addNode("N1", "Perth Transit", "TRANSIT", 500);
        
        waybillService.scanCheckIn("W1", "N1");
        Waybill waybill = DataManager.waybillList.get(0);
        String initialState = waybill.getCurrentState().getStateName();
        
        assertEquals("IN_TRANSIT", initialState, "Initial state after check-in must be IN_TRANSIT."); // Equality Assertion [cite: 119]
        
        waybillService.scanCheckOut("W1", "N1");
        String updatedState = waybill.getCurrentState().getStateName();
        
        assertNotEquals(initialState, updatedState, "State should have changed after checkout."); // Equality Assertion [cite: 149]
        assertEquals("ARRIVED_AT_NODE", updatedState, "State must transition to ARRIVED_AT_NODE."); // Equality Assertion [cite: 119]
    }

    @Test
    public void testBatchCreationAndWaybillIntegration() {
        TransitBatch batch1 = batchService.createTransitBatch("B1", "N1", "N2");
        TransitBatch batch2 = batchService.createTransitBatch("B2", "N2", "N3");
        
        // Verifies distinct batches do not share memory allocation 
        assertNotSame(batch1, batch2, "Distinct batches should have different memory identities."); // Reference Identity [cite: 128]
        
        nodeService.addNode("N1", "Hobart Station", "STATION", 150);
        waybillService.scanCheckIn("W1", "N1"); 
        
        boolean added = batchService.addWaybillToBatch("B1", "W1");
        assertTrue(added, "Waybill should successfully bind to the batch.");
    }

    @Test
    public void testExceptionHandlingOnNullInputs() {
        // Exception Testing: Verifies that specific exceptions occur, ensuring code handles errors safely [cite: 132, 133]
        assertThrows(NullPointerException.class, () -> {
            // Force an NPE by explicitly passing null where a valid String object is expected by the framework
            waybillService.scanCheckIn(null, "N1");
        }, "Passing a null ID into a list search should throw a NullPointerException when .equals() is invoked.");
    }

    @Test
    public void testDynamicallyModifyRouteInvalidNodeIdShouldFail() {
        nodeService.addNode("N1", "Melbourne Hub", "HUB", 500);
        nodeService.addNode("N2", "Brisbane Station", "STATION", 200);

        RoutePlan route = new RoutePlan("R1");
        route.addRouteLeg(new RouteLeg(1, 3, DataManager.findNodeById("N1")));
        DataManager.waybillList.add(new Waybill("W1", route));

        boolean result = routeService.dynamicallyModifyRoute("W1", "INVALID");
        assertFalse(result, "Using a non-existent node ID should fail.");
    }

    @Test
    public void testDynamicallyModifyRouteNodeNotInRouteShouldFail() {
        nodeService.addNode("N1", "Melbourne Hub", "HUB", 500);
        nodeService.addNode("N2", "Brisbane Station", "STATION", 200);

        RoutePlan route = new RoutePlan("R1");
        route.addRouteLeg(new RouteLeg(1, 3, DataManager.findNodeById("N1")));
        DataManager.waybillList.add(new Waybill("W1", route));

        boolean result = routeService.dynamicallyModifyRoute("W1", "N2");
        assertFalse(result, "Using a node ID that is not in the route should fail.");
    }
}
