// Subsystem owned by student #2

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Route plan entity
 */
public class RoutePlan {
    private String routeId;
    private boolean isActive;
    private List<RouteLeg> legs;

    public RoutePlan(String routeId) {
        this.routeId = routeId;
        this.isActive = true;
        this.legs = new ArrayList<>();
    }

    public void addRouteLeg(RouteLeg leg) {
        legs.add(leg);
    }

    // Getter
    public String getRouteId() { return routeId; }
    public boolean getIsActive() { return isActive; }
    public List<RouteLeg> getLegs() { return legs; }
}
