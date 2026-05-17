// Subsystem owned by student #2

package service;

import dao.DataManager;
import model.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Route service layer
 */
public class RouteService {

    public boolean canModifyRoute(String waybillId) {
        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) return false;
        RoutePlan routePlan = waybill.getRoutePlan();
        return routePlan != null && routePlan.getLegs() != null && !routePlan.getLegs().isEmpty();
    }

    /**
     * Generate an initial route plan
     */
    public boolean generateInitialRoute(String waybillId, int numLegs) {
        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) {
            waybill = new Waybill(waybillId, new RoutePlan("ROUTE_" + waybillId));
            DataManager.waybillList.add(waybill);
        }

        if (numLegs < 2) {
            System.out.println("Number of route legs must be at least 2!");
            return false;
        }
        if (DataManager.nodeList.size() < numLegs) {
            System.out.println("Not enough nodes! Currently there are only " + DataManager.nodeList.size()
                    + " nodes, cannot generate " + numLegs + " route legs.");
            return false;
        }

        System.out.println("=== Auto-generating " + numLegs + " random route legs ===");
        RoutePlan route = waybill.getRoutePlan();
        route.getLegs().clear();

        ArrayList<Integer> availableIndices = new ArrayList<>();
        for (int i = 0; i < DataManager.nodeList.size(); i++) {
            availableIndices.add(i);
        }

        Random random = new Random();

        for (int i = 1; i <= numLegs; i++) {
            int randomIndex = random.nextInt(availableIndices.size());
            int nodeIndex = availableIndices.get(randomIndex);
            availableIndices.remove(randomIndex);
            int transitHours = 1 + random.nextInt(10);
            route.addRouteLeg(new RouteLeg(i, transitHours, DataManager.nodeList.get(nodeIndex)));
        }

        System.out.println("Route generated:");
        for (RouteLeg leg : route.getLegs()) {
            System.out.println("- Leg " + leg.getSequenceIndex() + ": via " + leg.getStopNode().getNodeName()
                    + ", estimated transit time " + leg.getEstimatedTransitHours() + " hours");
        }
        return true;
    }

    /**
     * Dynamically modify the transport route
     */
    public boolean dynamicallyModifyRoute(String waybillId, String failedNodeId) {
        Waybill waybill = findWaybill(waybillId);
        if (waybill == null) {
            System.out.println("Waybill does not exist!");
            return false;
        }

        RoutePlan oldRoute = waybill.getRoutePlan();
        if (oldRoute == null || oldRoute.getLegs() == null || oldRoute.getLegs().isEmpty()) {
            System.out.println("Waybill has no route plan. Please generate an initial route first.");
            return false;
        }

        if (failedNodeId == null || failedNodeId.isBlank()) {
            System.out.println("Failed node ID cannot be empty!");
            return false;
        }

        if (DataManager.findNodeById(failedNodeId) == null) {
            System.out.println("Node does not exist!");
            return false;
        }

        boolean existsInRoute = false;
        for (RouteLeg leg : oldRoute.getLegs()) {
            if (leg != null && leg.getStopNode() != null
                    && failedNodeId.equalsIgnoreCase(leg.getStopNode().getNodeId())) {
                existsInRoute = true;
                break;
            }
        }
        if (!existsInRoute) {
            System.out.println("Failed node is not in the current route. Please enter a node ID from the route.");
            return false;
        }

        RoutePlan newRoute = new RoutePlan(oldRoute.getRouteId() + "_MODIFIED");

        System.out.println("=== Dynamically replanning route ===");
        int seq = 1;
        for (RouteLeg leg : oldRoute.getLegs()) {
            if (!failedNodeId.equalsIgnoreCase(leg.getStopNode().getNodeId())) {
                newRoute.addRouteLeg(new RouteLeg(seq++, leg.getEstimatedTransitHours(), leg.getStopNode()));
            }
        }

        waybill.setRoutePlan(newRoute);
        System.out.println("Route updated. Skipped node: " + failedNodeId);
        for (RouteLeg leg : newRoute.getLegs()) {
            System.out.println("- Via: " + leg.getStopNode().getNodeName());
        }
        return true;
    }

    private Waybill findWaybill(String waybillId) {
        for (Waybill w : DataManager.waybillList) {
            if (w.getWaybillId().equals(waybillId)) return w;
        }
        return null;
    }
}
